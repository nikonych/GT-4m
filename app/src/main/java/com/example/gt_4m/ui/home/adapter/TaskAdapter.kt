package com.example.gt_4m.ui.home.adapter

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gt_4m.App
import com.example.gt_4m.R
import com.example.gt_4m.databinding.ItemTaskBinding
import com.example.gt_4m.model.Task

class TaskAdapter : Adapter<TaskAdapter.TaskViewHolder>() {
    private val data = arrayListOf<Task>()
    var onItemLongClick: ((Task) -> Unit)?= null
    private var changeColor = true

    fun addTask(tasks: List<Task>){
        data.clear()
        data.addAll(tasks)
        notifyDataSetChanged()
    }

    fun reloadData(){
        data.clear()
        data.addAll(App.db.taskDao().getAll())
        notifyDataSetChanged()
    }

    fun deleteItem(i:Int){
        data.removeAt(i)
        notifyDataSetChanged()
    }

    fun UndoItem(i: Int, task: Task){
        data.add(i, task)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.count()

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position])
    }


    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root){
        fun bind(task: Task) {
            with(binding) {
                tvTitle.text = task.title
                tvDesc.text = task.description
            }
            if (changeColor) {
                binding.itemTask.setBackgroundColor(Color.BLACK)
                binding.tvDesc.setTextColor(Color.WHITE)
                binding.tvTitle.setTextColor(Color.WHITE)
                changeColor = false
            } else {
                binding.itemTask.setBackgroundColor(Color.WHITE)
                binding.tvDesc.setTextColor(Color.BLACK)
                binding.tvTitle.setTextColor(Color.BLACK)
                changeColor = true
            }

        }

        init {
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(data[adapterPosition])
                return@setOnLongClickListener true
            }
        }

    }
}