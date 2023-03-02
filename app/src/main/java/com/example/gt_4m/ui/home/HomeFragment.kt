package com.example.gt_4m.ui.home

import android.os.Bundle
import android.util.Log
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
        var tasks = App.db.taskDao().getAll()
        adapter.addTask(tasks)

        val swipeTask = object : SwipeTask(this.context) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        binding.fab.isClickable = false
                        binding.fabRandom.isClickable = false
                        var item_id = viewHolder.absoluteAdapterPosition
                        var task = tasks[item_id]
                        tasks.removeAt(item_id)
                        adapter.deleteItem(item_id)
                        adapter.notifyItemRemoved(item_id)
                        var isUndo = false
                        Log.d("gg", tasks.toString() + item_id.toString())
                        Snackbar.make(view, "Deleted", Snackbar.LENGTH_LONG)
                            .setAction(
                                "Undo",
                                View.OnClickListener {
                                    adapter.reloadData()
                                    isUndo = true
                                    tasks = App.db.taskDao().getAll()
                                })
                            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                override fun onDismissed(
                                    transientBottomBar: Snackbar?,
                                    event: Int
                                ) {
                                    if (!isUndo)
                                        App.db.taskDao().delete(task)

                                    binding.fab.isClickable = true
                                    binding.fabRandom.isClickable = true
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

        adapter.onItemLongClick = {
            val dialog = DeleteDialog(it, adapter)
            dialog.show(parentFragmentManager, "gg")


        }


        binding.fabRandom.setOnClickListener {
            val taskRandom = Task(
                title = getRandomString(1),
                description = getRandomString(1)
            )
            App.db.taskDao().insert(taskRandom)
            adapter.reloadData()
            tasks = App.db.taskDao().getAll()
        }

    }


    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }


    fun SwipeToDelete() {

    }


}