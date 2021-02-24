package com.ms.to_dolistapp.ui.deleteallcompleted

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

/**
 * @AUTHOR: Mehedi Hasan
 * @DATE: 2/24/2021, Wed
 */
@AndroidEntryPoint
class DeleteAllCompletedDialogFragment : DialogFragment() {

    private val viewModel: DeleteAllCompletedViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Do you really want to delete all completed task ?")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Yes") { _, _ ->
                // call viewmodel to delete
                viewModel.onConfirmClick()
            }.create()

}