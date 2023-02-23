package com.example.gt_4m.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gt_4m.App
import com.example.gt_4m.databinding.ItemTaskBinding
import com.example.gt_4m.model.Task

class TaskAdapter : Adapter<TaskAdapter.TaskViewHolder>() {
    private val data = arrayListOf<Task>()
    var onItemLongClick: ((Task) -> Unit)?= null

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

        }

        init {
            itemView.setOnLongClickListener {
                onItemLongClick?.invoke(data[adapterPosition])
                return@setOnLongClickListener true
            }
        }

    }
}