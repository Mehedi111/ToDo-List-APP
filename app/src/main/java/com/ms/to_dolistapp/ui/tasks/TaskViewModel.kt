package com.ms.to_dolistapp.ui.tasks

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ms.to_dolistapp.data.PreferencesManager
import com.ms.to_dolistapp.data.SortOrder
import com.ms.to_dolistapp.data.Task
import com.ms.to_dolistapp.data.TaskDao
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * @AUTHOR: Mehedi Hasan
 * @DATE: 2/3/2021, Wed
 */
class TaskViewModel @ViewModelInject constructor(
    val taskDao: TaskDao,
    val preferencesManager: PreferencesManager,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    val searchQuery = state.getLiveData("searchQuery", "")
    val preferencesFlow = preferencesManager.preferenceFlow

    //combine multiple flow in a single flow
    private val taskFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreference ->
        Pair(query, filterPreference)

    }.flatMapLatest { (query, filterPreference) ->
        taskDao.getTasks(query, filterPreference.sortOrder, filterPreference.hideCompleted)
    }

    fun sortOrderSelected(sortOrder: SortOrder) = viewModelScope.launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun hideCompletedClick(hideCompleted: Boolean) = viewModelScope.launch {
        preferencesManager.updateHideCompleted(hideCompleted)
    }

    fun onTaskSelected(task: Task) = viewModelScope.launch {
        taskEventChannel.send(TaskEvent.NavigateToEditTaskScreen(task))
    }

    fun onTaskCheckedChanged(task: Task, checked: Boolean) = viewModelScope.launch {
        taskDao.update(task.copy(completed = true))
    }

    private val taskEventChannel = Channel<TaskEvent>()
    val taskEvent = taskEventChannel.receiveAsFlow()

    fun onTaskSwiped(task: Task) {
        viewModelScope.launch {
            taskDao.delete(task)
            taskEventChannel.send(TaskEvent.showUndoDeleteTaskMessage(task))
        }
    }

    fun onUndoDeleteClick(task: Task) {
        viewModelScope.launch {
            taskDao.insert(task)
        }
    }

    fun onAddNewTaskClick() {
        viewModelScope.launch {
            taskEventChannel.send(TaskEvent.NavigateToAddTaskScreen)
        }
    }

    val tasks = taskFlow.asLiveData()


    sealed class TaskEvent() {
        object NavigateToAddTaskScreen : TaskEvent()
        data class NavigateToEditTaskScreen(val task: Task) : TaskEvent()
        data class showUndoDeleteTaskMessage(val task: Task) : TaskEvent()
    }
}

