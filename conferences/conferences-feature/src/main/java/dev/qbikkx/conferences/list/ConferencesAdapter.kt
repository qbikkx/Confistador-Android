package dev.qbikkx.conferences.list

import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.toRectF
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.qbikkx.conferences.R
import dev.qbikkx.conferences.core.Conference
import dev.qbikkx.coreui.toPx

internal class ConferencesAdapter : ListAdapter<Conference, ConferencesAdapter.ViewHolder>(DIFF_UTILL) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_conference, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val title = view.findViewById<TextView>(R.id.title)
        private val subtitle = view.findViewById<TextView>(R.id.subtitle)
        private val category = view.findViewById<TextView>(R.id.category)
        private val twitter = view.findViewById<TextView>(R.id.twitter_link)

        fun bind(conference: Conference) {
            itemView.setOnClickListener { }
            title.text = "${conference.name} ${conference.city.substringBefore(',')}"
            subtitle.text = "${conference.country} | ${conference.startDate.substringBefore(',')}"
            twitter.text = conference.twitter ?: ""
            val builder = SpannableStringBuilder()
            conference.categories.forEach { category ->
                val string = SpannableString("  ${category.name}")
                val color = ShittyRoundedDrawable(
                    ContextCompat.getColor(
                        itemView.context,
                        category.getColorResId()
                    )
                )
                color.setBounds(0, 0, 10.toPx().toInt(), 10.toPx().toInt())
                string.setSpan(
                    ImageSpan(color, DynamicDrawableSpan.ALIGN_BASELINE),
                    0,
                    1,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                builder.append(string)
                builder.append(" ")
            }
            category.text = builder
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

class ShittyRoundedDrawable(
    private val color: Int
) : Drawable() {

    private var roundedBounds: RectF = RectF()

    private var radius = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = this@ShittyRoundedDrawable.color
    }

    override fun setBounds(left: Int, top: Int, right: Int, bottom: Int) {
        super.setBounds(left, top, right, bottom)
        roundedBounds = bounds.toRectF()
        radius = roundedBounds.width() / 2
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRoundRect(roundedBounds, radius, radius, paint)
    }

    override fun setAlpha(alpha: Int) {
    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
    }
}

fun Conference.Category.getColorResId(): Int {
    return when (this) {
        Conference.Category.Android -> R.color.android_green
        Conference.Category.Clojure -> R.color.clojure
        Conference.Category.CSS -> R.color.css
        Conference.Category.Data -> R.color.data
        Conference.Category.DevOps -> R.color.dev_ops
        Conference.Category.dotNet -> R.color.dot_net
        Conference.Category.Elixir -> R.color.elixir
        Conference.Category.General -> R.color.general
        Conference.Category.GoLang -> R.color.go_lang
        Conference.Category.GraphQL -> R.color.graph_ql
        Conference.Category.iOS -> R.color.ios
        Conference.Category.Java -> R.color.java
        Conference.Category.JavaScript -> R.color.java_script
        Conference.Category.Networking -> R.color.networking
        Conference.Category.PHP -> R.color.php
        Conference.Category.Python -> R.color.python
        Conference.Category.Ruby -> R.color.ruby
        Conference.Category.Rust -> R.color.rust
        Conference.Category.Scala -> R.color.scala
        Conference.Category.Security -> R.color.security
        Conference.Category.TechComm -> R.color.tech_comm
        Conference.Category.UX -> R.color.ux
    }
}