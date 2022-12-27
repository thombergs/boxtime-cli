package io.boxtime.cli.ports.taskdatabase

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_ALPHABET
import org.springframework.data.annotation.Id
import java.util.*

data class Task(
    @Id
    val id: String,
    val title: String
) {

    companion object{
        val RANDOM = Random()
    }

    constructor(title: String) : this(
        NanoIdUtils.randomNanoId(RANDOM, DEFAULT_ALPHABET, 8),
        title
    )

}
