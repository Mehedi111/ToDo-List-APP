package com.ms.to_dolistapp.ui.tasks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ms.to_dolistapp.data.TaskDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

/**
 * @AUTHOR: Mehedi Hasan
 * @DATE: 2/3/2021, Wed
 */
class TaskViewModel @ViewModelInject constructor(
    val taskDao: TaskDao
) : ViewModel() {

    val searchQuery = MutableStateFlow("")
    val sortOrder = MutableStateFlow(SortOrder.BY_NAME)
    val hideCompleted = MutableStateFlow(false)

    //combine multiple flow in a single flow
    private val taskFlow = combine(
        searchQuery,
        sortOrder,
        hideCompleted
    ){ query, sortOrder, hideCompleted ->
        Triple(query, sortOrder, hideCompleted)

    }.flatMapLatest {(query, sortOrder, hideCompleted) ->
        taskDao.getTasks(query, sortOrder, hideCompleted)
    }

    val tasks = taskFlow.asLiveData()


}

enum class SortOrder{BY_NAME, BY_DATE}