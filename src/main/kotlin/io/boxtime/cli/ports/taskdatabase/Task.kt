package io.boxtime.cli.ports.taskdatabase

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.aventrix.jnanoid.jnanoid.NanoIdUtils.DEFAULT_ALPHABET
import io.boxtime.cli.application.parseDuration
import io.boxtime.cli.application.relativeDate
import io.boxtime.cli.ports.tasklogger.Count
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.regex.Pattern

data class Task(
    val id: String,
    val title: String,
    val created: LocalDateTime,
    val unit: Unit,
    val planned: LocalDate?,
    val tags: Set<Tag>
) {

    companion object {
        val RANDOM = Random()
        private val TAG_PATTERN = Pattern.compile("(#[^# ]+)")
        private val DATE_PATTERN = Pattern.compile("(@[^@ ]+)")

        private fun stripTags(string: String): String {
            return stripStandardTags(stripDateTags(string))
                .trim()
                .replace(Regex(" +"), " ")
        }

        private fun stripStandardTags(string: String): String {
            val matcher = TAG_PATTERN.matcher(string)

            var stripped = string
            while (matcher.find()) {
                stripped = stripped.replace(matcher.group(), "")
            }
            return stripped
        }

        private fun stripDateTags(string: String): String {
            val matcher = DATE_PATTERN.matcher(string)

            var stripped = string
            while (matcher.find()) {
                stripped = stripped.replace(matcher.group(), "")
            }
            return stripped
        }

        private fun extractTags(string: String): Set<Tag> {
            val matcher = TAG_PATTERN.matcher(string)

            val tags = mutableSetOf<Tag>()
            while (matcher.find()) {
                tags.add(Tag(matcher.group().replace("#", "")))
            }

            return tags;
        }

        private fun extractPlannedDate(string: String): LocalDate? {
            val matcher = DATE_PATTERN.matcher(string)

            val tags = mutableSetOf<String>()
            while (matcher.find()) {
                tags.add(matcher.group().replace("@", ""))
            }

            if (tags.size == 1) {
                return relativeDate(tags.iterator().next())
            }

            return null
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
        if (extractTags) extractPlannedDate(title) else null,
        if (extractTags) extractTags(title) else setOf<Tag>()
    )

    fun withTags(tags: Set<Tag>): Task {
        return Task(this.id, this.title, this.created, this.unit, this.planned, tags)
    }

    fun plan(date: LocalDate): Task {
        return Task(this.id, this.title, this.created, this.unit, date, this.tags)
    }

    fun unplan(): Task {
        return Task(this.id, this.title, this.created, this.unit, null, this.tags)
    }

    fun plannedString(): String {
        return if (this.planned == null) {
            "unplanned"
        } else {
            this.planned.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
    }

    fun tagsString(): String {
        if (tags.isEmpty()) {
            return ""
        }

        val builder = StringBuilder("Tags: ")
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
