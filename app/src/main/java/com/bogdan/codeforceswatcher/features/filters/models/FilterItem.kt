package com.bogdan.codeforceswatcher.features.filters.models

data class FilterItem(
    val imageId: Int?,
    val title: String,
    val isChecked: Boolean,
    val onSwitchTap: (Boolean) -> Unit
)