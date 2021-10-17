package com.smartpay.application.result

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.smartpay.application.R
import com.smartpay.application.data.Tlv

class ResultAdapter internal constructor(context: Activity) :
    ListAdapter<Tlv, ResultViewHolder>(ResultDiffCallBack()) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val item = getItem(position)
        holder.tvResult.text = item.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val itemView = inflater.inflate(R.layout.item_result, parent, false)
        return ResultViewHolder(itemView)
    }
}

class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvResult: TextView = itemView.findViewById(R.id.tv_Result)
}

private class ResultDiffCallBack : DiffUtil.ItemCallback<Tlv>() {
    override fun areItemsTheSame(
        oldItem: Tlv,
        newItem: Tlv
    ): Boolean {
        return (oldItem.tag == newItem.tag)
    }

    override fun areContentsTheSame(
        oldItem: Tlv,
        newItem: Tlv
    ): Boolean {
        return (oldItem.length == newItem.length) && (oldItem.value == newItem.value)
    }
}