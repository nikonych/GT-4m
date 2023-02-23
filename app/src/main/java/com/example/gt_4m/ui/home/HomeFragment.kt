package com.example.gt_4m.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gt_4m.App
import com.example.gt_4m.R
import com.example.gt_4m.databinding.FragmentHomeBinding
import com.example.gt_4m.model.Task
import com.example.gt_4m.ui.home.adapter.TaskAdapter
import com.example.gt_4m.ui.home.dialog.DeleteDialog
import com.example.gt_4m.ui.home.swipe.SwipeTask
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var adapter: TaskAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = TaskAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {

            findNavController().navigate(R.id.taskFragment)
        }
        val tasks = App.db.taskDao().getAll()
        adapter.addTask(tasks)

        val swipeTask = object : SwipeTask(this.context){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when(direction){
                    ItemTouchHelper.LEFT -> {
                        adapter.deleteItem(viewHolder.absoluteAdapterPosition)
                        adapter.notifyItemRemoved(viewHolder.absoluteAdapterPosition)

                        Snackbar.make(view, "Deleted", Snackbar.LENGTH_LONG)
                            .setAction(
                                "Undo",
                                View.OnClickListener {
                                    adapter.reloadData()
                                })
                            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>(){
                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    when (event) {
                                        Snackbar.Callback.DISMISS_EVENT_ACTION ->
                                            adapter.reloadData()
                                        else -> App.db.taskDao().delete(tasks[viewHolder.absoluteAdapterPosition])
                                    }

                                }
                            })
                            .show()
                    }
                }



            }
        }




        val touchHelper = ItemTouchHelper(swipeTask)
        touchHelper.attachToRecyclerView(binding.rvTasks)

        binding.rvTasks.adapter = adapter

        adapter.onItemLongClick={
            val dialog = DeleteDialog(it, adapter)
            dialog.show(parentFragmentManager, "gg")


        }


        binding.fabRandom.setOnClickListener {
            val taskRandom = Task(title = getRandomString(10), description = getRandomString(100))
            App.db.taskDao().insert(taskRandom)
            adapter.reloadData()
        }

    }


    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }

    companion object {
        const val RESULT_REQUEST_KEY = "request_key"
        const val TASK_KEY = "task_key"

    }

}