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

class StatsAdapter(private val statsList: List<Stats>) : RecyclerView.Adapter<StatsAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_stats, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val stats = statsList[position]

        // sets the text to the textview from our itemHolder class
        holder.dateTime.text = stats.startDateTime
        holder.sessionLength.text = String.format ("%02d:%02d:%02d", stats.sessionLengthHours, stats.sessionLengthMinutes, stats.sessionLengthSeconds)

        if(stats.completedSession){
            holder.status.text = "COMPLETATO"
            holder.status.setTextColor((Color.parseColor("#008000")))
        }
        else {
            holder.status.text = "FALLITO"
            holder.status.setTextColor((Color.parseColor("#ff0000")))
        }

        holder.itemView.setOnClickListener{ view ->
            view.findNavController().navigate(R.id.action_stats_to_statsDetail)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return statsList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dateTime: TextView = itemView.findViewById(R.id.dateTime)
        val status: TextView = itemView.findViewById(R.id.sessionState)
        val sessionLength: TextView = itemView.findViewById(R.id.sessionLength)
    }
}