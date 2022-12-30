package io.boxtime.cli.ports.taskdatabase

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_ALPHABET
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern

data class Task(
    val id: String,
    val title: String,
    val created: LocalDateTime,
    val tags: Set<Tag>
) {

    companion object {
        val RANDOM = Random()
        private const val TAG_REGEX = "(#[^# ]+)"
        private val TAG_PATTERN = Pattern.compile(TAG_REGEX)

        private fun stripTags(string: String): String {
            val matcher = TAG_PATTERN.matcher(string)

            var stripped = string
            while(matcher.find()){
                stripped = stripped.replace(matcher.group(), "")
            }
            return stripped
                .trim()
                .replace(Regex(" +"), " ")
        }

        private fun extractTags(string: String): Set<Tag> {
            val matcher = TAG_PATTERN.matcher(string)

            val tags = mutableSetOf<Tag>()
            while (matcher.find()) {
                tags.add(Tag(matcher.group().replace("#", "")))
            }

            return tags;
        }
    }

    constructor(title: String, tags: Set<Tag>) : this(
        NanoIdUtils.randomNanoId(RANDOM, DEFAULT_ALPHABET, 8),
        title,
        LocalDateTime.now(),
        tags
    )

    constructor(title: String, extractTags: Boolean = false) : this(
        if(extractTags) stripTags(title) else title,
        if (extractTags) extractTags(title) else setOf<Tag>()
    )


    fun withTags(tags: Set<Tag>): Task {
        return Task(this.id, this.title, this.created, tags)
    }

    fun tagsString(): String {
        val builder = StringBuilder()
        for(tag in tags){
            builder.append(tag.nameWithHashtag())
            builder.append(" ")
        }
        return builder.toString().trim()
    }

}
