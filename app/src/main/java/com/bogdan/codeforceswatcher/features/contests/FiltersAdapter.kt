package com.bogdan.codeforceswatcher.features.contests

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bogdan.codeforceswatcher.R
import kotlinx.android.synthetic.main.view_contest_item.view.ivIcon
import kotlinx.android.synthetic.main.view_filter_item.view.*

data class FilterItem(
        val imageId: Int?,
        val title: String,
        val isChecked: Boolean,
        val onSwitchTap: (Boolean) -> Unit
)

class FiltersAdapter(
        private val context: Context
) : RecyclerView.Adapter<FiltersAdapter.ViewHolder>() {

    private var items: List<FilterItem> = listOf()

    fun setItems(filters: List<FilterItem>) {
        items = filters
        notifyDataSetChanged() // Crash
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_filter_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val filterItem = items[position]
        with(holder) {
            title.text = filterItem.title
            filterItem.imageId?.let {
                ivIcon.setImageResource(it)
            } ?: run {
                ivIcon.visibility = View.GONE
            }
            checkBox.isChecked = filterItem.isChecked
            onCheckedChangeListener = { isChecked ->
                filterItem.onSwitchTap(isChecked)
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.tvTitle
        val ivIcon: ImageView = view.ivIcon
        val checkBox: CheckBox = view.checkbox

        var onCheckedChangeListener: ((Boolean) -> Unit)? = null

        init {
            checkBox.setOnCheckedChangeListener { _, isChecked -> onCheckedChangeListener?.invoke(isChecked) }
        }
    }
}
