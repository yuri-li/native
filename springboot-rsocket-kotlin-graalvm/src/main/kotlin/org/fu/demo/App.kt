package org.fu.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.*

@SpringBootApplication(
    exclude = [
        org.springframework.boot.autoconfigure.context.ConfigurationPropertiesAutoConfiguration::class,
        org.springframework.boot.autoconfigure.ssl.SslAutoConfiguration::class,
        org.springframework.boot.autoconfigure.context.LifecycleAutoConfiguration::class,
        org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration::class,
        org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration::class,
        org.springframework.boot.autoconfigure.info.ProjectInfoAutoConfiguration::class,
    ]
)
class App

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    runApplication<App>(*args)
}