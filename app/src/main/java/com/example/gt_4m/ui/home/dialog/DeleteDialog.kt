package com.example.gt_4m.ui.home.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.gt_4m.App
import com.example.gt_4m.R
import com.example.gt_4m.model.Task
import com.example.gt_4m.ui.home.adapter.TaskAdapter

class DeleteDialog(val task: Task, val adapter: TaskAdapter) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setMessage(R.string.confirm_delete)
                .setPositiveButton(R.string.yes,
                    DialogInterface.OnClickListener { dialog, id ->
                        App.db.taskDao().delete(task)
                        adapter.reloadData()
                    })
                .setNegativeButton(R.string.no,
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            // Create the AlertDialog object and return it
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}