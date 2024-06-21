package com.dicoding.skivent.ui.dashboard.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.skivent.R
import com.dicoding.skivent.dataclass.HistoryItemItem

class HistoryAdapter(private val historyList: List<HistoryItemItem>):
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvPredictedDisease: TextView = itemView.findViewById(R.id.tvPredictedDisease)
//        val tvCreatedAt: TextView = itemView.findViewById(R.id.tvCreatedAt)
//        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
//        holder.tvPredictedDisease.text = historyItem.predictedDisease
//        holder.tvCreatedAt.text = historyItem.createdAt
//        holder.tvDescription.text = historyItem.description
    }

    override fun getItemCount(): Int = historyList.size
}