package io.boxtime.cli

import io.boxtime.cli.adapters.h2taskdatabase.TaskEntity
import io.boxtime.cli.adapters.h2tasklogger.LogEntity
import io.boxtime.cli.adapters.h2tasklogger.LogRepository
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar

class RuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        hints.reflection()
            .registerType(TaskEntity::class.java, MemberCategory.INVOKE_DECLARED_METHODS)
        hints.reflection()
            .registerType(LogEntity::class.java, MemberCategory.INVOKE_DECLARED_METHODS)
        hints.reflection()
            .registerType(LogRepository::class.java, MemberCategory.INVOKE_DECLARED_METHODS)
    }
}