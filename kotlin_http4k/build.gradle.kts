import org.gradle.api.JavaVersion.VERSION_22
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.*
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version "2.0.21"
    application
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(22))
    }
}

application {
    mainClass = "com.example.HelloWorldKt"
}

repositories {
    mavenCentral()
}

tasks {
    withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            allWarningsAsErrors = false
            jvmTarget.set(JVM_22)
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = VERSION_22
        targetCompatibility = VERSION_22
    }
}

dependencies {
    implementation(platform("org.http4k:http4k-bom:5.37.1.0"))
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-jetty:4.44.0.0")
    implementation("org.http4k:http4k-client-okhttp:4.44.0.0")


    implementation("org.http4k:http4k-format-jackson:4.44.0.0")
    implementation("com.zaxxer:HikariCP:5.0.1") // Connection pool
    implementation("org.jetbrains.exposed:exposed-core:0.43.0") // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-dao:0.43.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.43.0")
    implementation("com.mysql:mysql-connector-j:9.1.0") // MySQL driver

    implementation("com.h2database:h2:2.2.220") // H2 in-memory database

    implementation("org.slf4j:slf4j-api:2.0.9") // SLF4J API
    implementation("ch.qos.logback:logback-classic:1.4.11") // Logback backend

    testImplementation("org.http4k:http4k-testing-approval")
    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.3")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.3")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.9.1") // for kotest framework
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.9.1") // for kotest core jvm assertions
    testImplementation("io.mockk:mockk:1.9.3")
}

