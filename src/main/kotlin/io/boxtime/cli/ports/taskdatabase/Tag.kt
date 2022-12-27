package io.boxtime.cli.ports.taskdatabase

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import java.util.*

data class Tag(
    val id: String,
    val name: String,
) {

    companion object{
        val RANDOM = Random()
    }

    constructor(name: String) : this(
        NanoIdUtils.randomNanoId(RANDOM, NanoIdUtils.DEFAULT_ALPHABET, 8),
        name,
    )

    fun nameWithHashtag(): String{
        return "#${this.name}"
    }

}
