package dev.qbikkx.conferences.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.qbikkx.conferences.R
import dev.qbikkx.conferences.core.Conference

internal class ConferencesAdapter : ListAdapter<Conference, ConferencesAdapter.ViewHolder>(DIFF_UTILL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.list_item_conference, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        private val title = view.findViewById<TextView>(R.id.title)
        private val subtitle = view.findViewById<TextView>(R.id.subtitle)

        fun bind(conference: Conference) {
            itemView.setOnClickListener {  }
            title.text = "${conference.name} ${conference.city.substringBefore(',')}"
            subtitle.text = "${conference.country} | ${conference.startDate.substringBefore(',')}"
        }
    }

    companion object {

        private val DIFF_UTILL = object : DiffUtil.ItemCallback<Conference>() {
            override fun areItemsTheSame(oldItem: Conference, newItem: Conference): Boolean {
                return oldItem.url == newItem.url && oldItem.startDate == newItem.startDate
            }

            override fun areContentsTheSame(oldItem: Conference, newItem: Conference): Boolean {
                return oldItem == newItem
            }
        }
    }
}