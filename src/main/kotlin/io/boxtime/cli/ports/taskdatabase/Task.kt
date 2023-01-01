package io.boxtime.cli.ports.taskdatabase

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_ALPHABET
import io.boxtime.cli.application.parseDuration
import io.boxtime.cli.ports.tasklogger.Count
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern

data class Task(
    val id: String,
    val title: String,
    val created: LocalDateTime,
    val unit: Unit,
    val tags: Set<Tag>
) {

    companion object {
        val RANDOM = Random()
        private const val TAG_REGEX = "(#[^# ]+)"
        private val TAG_PATTERN = Pattern.compile(TAG_REGEX)

        private fun stripTags(string: String): String {
            val matcher = TAG_PATTERN.matcher(string)

            var stripped = string
            while (matcher.find()) {
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

    constructor(
        title: String,
        unit: Unit = Unit.SECONDS,
        extractTags: Boolean = false) : this(
        NanoIdUtils.randomNanoId(RANDOM, DEFAULT_ALPHABET, 8),
        if (extractTags) stripTags(title) else title,
        LocalDateTime.now(),
        unit,
        if (extractTags) extractTags(title) else setOf<Tag>()
    )

    fun withTags(tags: Set<Tag>): Task {
        return Task(this.id, this.title, this.created, this.unit, tags)
    }

    fun tagsString(): String {
        val builder = StringBuilder()
        for (tag in tags) {
            builder.append(tag.nameWithHashtag())
            builder.append(" ")
        }
        return builder.toString().trim()
    }

    fun toCount(completion: String): Count {
        return if (this.unit == Unit.SECONDS) {
            Count(this.unit, parseDuration(completion).toSeconds().toFloat())
        } else {
            Count(this.unit, completion.toFloat())
        }
    }

    fun toCount(count: Float): Count {
        return Count(this.unit, count)
    }

}
