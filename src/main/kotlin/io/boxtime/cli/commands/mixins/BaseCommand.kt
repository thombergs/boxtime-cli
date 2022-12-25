package io.boxtime.cli.commands.mixins

import io.boxtime.cli.application.Application
import io.boxtime.cli.config.ApplicationConfig
import io.boxtime.cli.config.ApplicationFactory
import picocli.CommandLine.Mixin
import java.util.concurrent.Callable

abstract class BaseCommand(
    private val applicationFactory: ApplicationFactory
) : Callable<Int> {

    @Mixin
    lateinit var output: AlfredOutputMixin

    @Mixin
    lateinit var base: BaseMixin

    protected fun getApplication(): Application {
        return applicationFactory.createApplication(ApplicationConfig(output.alfred))
    }

}