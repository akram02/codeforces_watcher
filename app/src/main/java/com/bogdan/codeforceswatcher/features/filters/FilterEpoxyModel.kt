package com.bogdan.codeforceswatcher.features.filters

import android.view.View
import com.bogdan.codeforceswatcher.R
import com.bogdan.codeforceswatcher.epoxy.BaseEpoxyModel
import com.bogdan.codeforceswatcher.features.filters.models.FilterItem
import kotlinx.android.synthetic.main.view_filter_item.view.*

data class FilterEpoxyModel(private val filterItem: FilterItem) : BaseEpoxyModel(R.layout.view_filter_item) {

    init {
        id("FilterEpoxyModel", filterItem.toString())
    }

    override fun bind(view: View) = with(view) {
        super.bind(view)
        tvTitle.text = filterItem.title
        filterItem.imageId?.let {
            ivIcon.setImageResource(it)
            ivIcon.visibility = View.VISIBLE
        } ?: run {
            ivIcon.visibility = View.GONE
        }
        checkbox.isChecked = filterItem.isChecked
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            filterItem.onSwitchTap(isChecked)
        }
    }

    override fun unbind(view: View) = with(view) {
        super.unbind(view)
        checkbox.setOnCheckedChangeListener(null)
    }
}