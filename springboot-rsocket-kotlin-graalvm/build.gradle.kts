@file:Suppress("VulnerableLibrariesLocal")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    id("java")

    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.serialization)

    alias(libs.plugins.spring.boot)
    alias(libs.plugins.tools.graalvm)
}
group = "org.fu.demo"
version = "0.0.4"

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = true
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(19))
    }
    sourceCompatibility = JavaVersion.toVersion(19)
}
repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.rsocket) {
        exclude(group = "com.fasterxml.jackson")
        exclude(group = "com.fasterxml.jackson.core")
        exclude(group = "com.fasterxml.jackson.datatype")
        exclude(group = "com.fasterxml.jackson.module")
        exclude(group = "com.fasterxml.jackson.dataformat")
    }
    implementation(libs.bundles.validator)
    implementation(libs.bundles.kotlin.reactor)

    testImplementation(libs.spring.boot.starter.test){
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation(libs.bundles.kotest)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "19"
        }
    }
    withType<JavaCompile> { options.compilerArgs = listOf("--enable-preview") }
    withType<Test> { jvmArgs = listOf("--enable-preview") }
    withType<JavaExec> { jvmArgs = listOf("--enable-preview") }
    withType<Test> {
        useJUnitPlatform()
    }
    withType<Wrapper> {
        distributionType = Wrapper.DistributionType.ALL
        gradleVersion = "8.3"
    }
}

kotlin {
    jvmToolchain(19)
}
