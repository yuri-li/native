pluginManagement {
    val kotlinVersion: String by settings
    val springbootVersion: String by settings
    val foojayVersion:String by settings
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.springframework.boot") version springbootVersion
        kotlin("plugin.spring") version kotlinVersion
        kotlin("plugin.serialization") version kotlinVersion
        id("org.gradle.toolchains.foojay-resolver-convention") version foojayVersion
    }
}
rootProject.name = "springboot-rsocket-koltin-graalvm"