package com.tpov.mornhouse.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tpov.mornhouse.R
import com.tpov.mornhouse.data.database.models.NumberDetailEntity

class MainHistoryAdapter(private var data: List<NumberDetailEntity>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<MainHistoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.textView.text = "${item.id.toString()}. ${item.name}"

        holder.itemView.setOnClickListener {
            listener.onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setData(newData: List<NumberDetailEntity>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(data, newData))
        data = newData
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClick(item: NumberDetailEntity)
    }

    class MyDiffCallback(
        private val oldList: List<NumberDetailEntity>,
        private val newList: List<NumberDetailEntity>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textViewListItem)
    }
}
