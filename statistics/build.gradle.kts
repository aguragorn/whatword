import com.aguragorn.Versions

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.android.library")
    id("com.aguragorn.androidconfig")
    id("com.aguragorn.optins")
    id("com.squareup.sqldelight")
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
                implementation(project(":config"))

                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}")
                api("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlin_date_time}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlin_serialization_json}")

                implementation("org.kodein.di:kodein-di:${Versions.kodein}")
                implementation("com.benasher44:uuid:${Versions.uuid}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin_coroutines}")
                implementation("io.mockk:mockk-common:${Versions.mockk}")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:android-driver:1.5.3")
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
            }
        }
        val desktopTest by getting {
            dependencies {
                implementation("io.mockk:mockk:${Versions.mockk}")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:sqljs-driver:1.5.3")
                implementation("com.juul.indexeddb:core:0.2.1")
            }
        }
    }
}

sqldelight {
    database("StatisticsDB") {
        packageName = "com.aguragorn.whatword.statistics.storage.sqldelight"
        schemaOutputDirectory = file("build/dbs")
    }
}