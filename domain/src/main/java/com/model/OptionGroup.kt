package com.model

enum class SelectionType {SINGLE, MULTIPLE}

data class Option(val id: String, val name: String, val extraPriceCents: Int= 0)

data class OptionGroup(
    val id: String,
    val title: String,
    val type: SelectionType,
    val options: List<Option>,
    val required: Boolean = false
)
