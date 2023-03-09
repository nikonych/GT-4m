package com.example.gt_4m.ui.notifications.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gt_4m.App
import com.example.gt_4m.databinding.ItemTaskBinding
import com.example.gt_4m.model.Quote

class QuoteAdapter: RecyclerView.Adapter<QuoteAdapter.QuoteViewHolder>() {
    private val data = arrayListOf<Quote>()


    fun addQuote(quotes: List<Quote>){
        data.clear()
        data.addAll(quotes)
        notifyDataSetChanged()
    }

//    fun reloadData(){
//        data.clear()
////        data.addAll(App.db.taskDao().getAll())
//        notifyDataSetChanged()
//    }
//
//    fun deleteItem(i:Int){
//        data.removeAt(i)
//        notifyDataSetChanged()
//    }
//
//    fun UndoItem(i: Int, quote: Quote){
//        data.add(i, quote)
//        notifyDataSetChanged()
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        return QuoteViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = data.count()

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        holder.bind(data[position])
    }


    inner class QuoteViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(quote: Quote) {
            with(binding) {
                tvTitle.text = quote.author
                tvDesc.text = quote.text
            }

        }



    }
}