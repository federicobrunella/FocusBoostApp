package com.example.focusboostapp.stats

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.focusboostapp.R
import com.google.firebase.Timestamp
import java.time.LocalDateTime

class StatsDetailAdapter(private val statsDetailList: ArrayList<Timestamp>) : RecyclerView.Adapter<StatsDetailAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_detail_stats, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: StatsDetailAdapter.ViewHolder, position: Int) {
        val detailItem = statsDetailList[position]

        holder.event.text = "User Distraction!"
        holder.timestamp.text = detailItem.toDate().toString()
    }



    // return the number of the items in the list
    override fun getItemCount(): Int {
        return statsDetailList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val event: TextView = itemView.findViewById(R.id.eventTextView)
        val timestamp: TextView = itemView.findViewById(R.id.eventTimestampTextView)
    }
}