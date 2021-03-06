plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.0.1"
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
    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":config"))
                implementation(project(":game"))
                implementation(project(":statistics"))

                implementation(kotlin("stdlib-common"))

                implementation("org.kodein.di:kodein-di:${com.aguragorn.Versions.kodein}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
                implementation(npm("copy-webpack-plugin", "9.1.0"))
            }
        }
    }
}

rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.13.1"
}