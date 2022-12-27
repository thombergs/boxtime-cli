package io.boxtime.cli.adapters.h2taskdatabase

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("TASK_TAG")
data class TagRef(
    @Column("TAG_ID")
    val tagId: String,
)