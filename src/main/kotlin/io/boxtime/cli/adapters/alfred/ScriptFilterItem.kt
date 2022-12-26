package io.boxtime.cli.adapters.alfred

import kotlinx.serialization.Serializable

@Serializable
data class ScriptFilterItems(
    val items: List<ScriptFilterItem>
)

@Serializable
data class ScriptFilterItem(
    val arg: String,
    val title: String,
    val subtitle: String,
    val icon: Icon? = null,
    val type: String = "default",
    val uid: String = arg,
    val autocomplete: String = title
)

@Serializable
data class Icon(
    val path: String
)
