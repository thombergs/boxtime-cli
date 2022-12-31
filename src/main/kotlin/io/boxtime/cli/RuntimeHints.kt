package io.boxtime.cli

import io.boxtime.cli.adapters.alfred.Icon
import io.boxtime.cli.adapters.alfred.ScriptFilterItem
import io.boxtime.cli.adapters.alfred.ScriptFilterItems
import io.boxtime.cli.adapters.h2taskdatabase.TagEntity
import io.boxtime.cli.adapters.h2taskdatabase.TaskEntity
import io.boxtime.cli.adapters.h2taskdatabase.TaskResultSetExtractor
import io.boxtime.cli.adapters.h2tasklogger.LogEntity
import org.springframework.aot.hint.ExecutableMode
import org.springframework.aot.hint.MemberCategory
import org.springframework.aot.hint.RuntimeHints
import org.springframework.aot.hint.RuntimeHintsRegistrar
import org.springframework.core.io.ClassPathResource

class RuntimeHints : RuntimeHintsRegistrar {
    override fun registerHints(hints: RuntimeHints, classLoader: ClassLoader?) {
        hints.reflection()
            .registerType(TaskEntity::class.java, MemberCategory.INVOKE_DECLARED_METHODS)
        hints.reflection()
            .registerType(LogEntity::class.java, MemberCategory.INVOKE_DECLARED_METHODS)
        hints.reflection()
            .registerType(TagEntity::class.java, MemberCategory.INVOKE_DECLARED_METHODS)
        hints.reflection()
            .registerType(TaskResultSetExtractor::class.java,
                MemberCategory.INVOKE_DECLARED_CONSTRUCTORS,
                MemberCategory.INVOKE_DECLARED_METHODS)

        hints.resources()
            .registerResource(ClassPathResource("/version.txt"))

        registerKotlinSerializables(hints)
    }

    /**
     * For each Kotlin class annotated with @Serializable, we need to register
     * the companion object and its serializer() method for reflection, otherwise
     * Json.encodeToString(content) doesn't work without manually specifying
     * the serializer.
     */
    private fun registerKotlinSerializables(hints: RuntimeHints) {
        hints.reflection()
            .registerField(ScriptFilterItems::class.java.getField("Companion"))
        hints.reflection()
            .registerMethod(ScriptFilterItems.Companion::class.java.getMethod("serializer"), ExecutableMode.INVOKE)

        hints.reflection()
            .registerField(ScriptFilterItem::class.java.getField("Companion"))
        hints.reflection()
            .registerMethod(ScriptFilterItem.Companion::class.java.getMethod("serializer"), ExecutableMode.INVOKE)

        hints.reflection()
            .registerField(Icon::class.java.getField("Companion"))
        hints.reflection()
            .registerMethod(Icon.Companion::class.java.getMethod("serializer"), ExecutableMode.INVOKE)
    }
}