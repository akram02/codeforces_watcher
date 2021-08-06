package com.bogdan.codeforceswatcher.features.filters

import com.bogdan.codeforceswatcher.epoxy.BaseEpoxyController
import com.bogdan.codeforceswatcher.features.filters.models.FilterItem

class FiltersEpoxyController : BaseEpoxyController<FilterItem>() {

    override fun buildModels() {
        data?.forEach {
            FilterEpoxyModel(it).addTo(this)
        }
    }
}
