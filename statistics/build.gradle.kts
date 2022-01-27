import com.aguragorn.Versions

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.android.library")
    id("com.aguragorn.androidconfig")
    id("com.aguragorn.optins")
}

group = "com.aguragorn.whatword"
version = "1.0"

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted.
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}")
                api("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlin_date_time}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlin_serialization_json}")

                api("org.kodein.di:kodein-di:${Versions.kodein}")
                implementation("com.benasher44:uuid:${Versions.uuid}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin_coroutines}")
            }
        }

        val androidMain by getting

        val desktopMain by getting

        val jsMain by getting
    }
}