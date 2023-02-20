package com.example.gt_4m.ui.onBoard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.gt_4m.databinding.ItemOnBoardingBinding
import com.example.gt_4m.model.OnBoard
import com.example.gt_4m.utils.loadImage

class OnBoardingAdapter(private val onStartClick:()-> Unit): Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private val data = arrayListOf<OnBoard>(
        OnBoard("Create Your Task", "Create your task to make sure\n" +
                "every task you have can\n" +
                "completed on time.\n",
            "https://img.freepik.com/free-vector/hand-drawn-business-planning-with-task-list_23-2149164275.jpg?w=2000"),
        OnBoard("Share With Others",
            "You can share your task with\n" +
                    "other if that tasks for 2\n" +
                    "people or more.\n",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRJBmy4SHGOT3NPKe66CUds3YZuVo_F8CiiUYKYZfXUQC9BsbxM_v0cW2_w8mYZh6FmiEo&usqp=CAU"),
        OnBoard("Checklist Finished Task",
            "if you completed your task.\n" +
                    "so you can view the result\n" +
                    "you work for each day.\n",
            "https://www.orangescrum.com/blog/wp-content/uploads/2019/09/Google-Tasks-%E2%80%93-Your-Personal-Task-Manager.png")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(ItemOnBoardingBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size


    inner class OnBoardingViewHolder(private val binding:ItemOnBoardingBinding) : ViewHolder(binding.root){
        fun bind(onBoard: OnBoard) {
            binding.apply {
                btnStart.isVisible = adapterPosition == 2
                btnSkip.isVisible = adapterPosition != 2

                btnStart.setOnClickListener {
                    onStartClick()
                }
                btnSkip.setOnClickListener {
                    onStartClick()
                }
                tvTitle.text = onBoard.title
                tvDesc.text = onBoard.desc
                ivBoard.loadImage(onBoard.image.toString())
            }
        }
    }

}