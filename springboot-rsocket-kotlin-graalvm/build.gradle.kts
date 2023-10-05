import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    idea
    id("org.springframework.boot")
    id("io.spring.dependency-management") version "1.1.3"

    kotlin("jvm")
    kotlin("plugin.spring")
    kotlin("plugin.serialization")

    id("org.graalvm.buildtools.native") version "0.9.27"
    id("java")
}
group = "org.fu.demo"
version = "0.0.3"

idea {
    module {
        isDownloadJavadoc = false
        isDownloadSources = true
    }
}

java{
    sourceCompatibility = JavaVersion.VERSION_19
}
repositories {
    mavenCentral()
}

dependencies {
    val kotlinxJackson: String by project
    val logback: String by project
    val kotest: String by project
    val kotlinSerialization: String by project
    val datetimeVersion: String by project
    val kotestSpringVersion: String by project

    implementation("org.springframework.boot:spring-boot-starter-rsocket") {
        exclude(group = "com.fasterxml.jackson")
        exclude(group = "com.fasterxml.jackson.core")
        exclude(group = "com.fasterxml.jackson.datatype")
        exclude(group = "com.fasterxml.jackson.module")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerialization")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime-jvm:$datetimeVersion")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        exclude(module = "mockito-core")
    }
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotest")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotest")
    testImplementation("io.kotest:kotest-property:$kotest")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:$kotestSpringVersion")
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
        distributionType = Wrapper.DistributionType.BIN
        gradleVersion = "8.3"
    }
}

kotlin {
    jvmToolchain(19)
}
