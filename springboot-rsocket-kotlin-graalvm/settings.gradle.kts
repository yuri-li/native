@Suppress("DSL_SCOPE_VIOLATION")
pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
    }
    plugins{
        id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
    }
}
rootProject.name = "springboot-rsocket-kotlin-graalvm"
