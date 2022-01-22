plugins {
    kotlin("android")
    id("org.jetbrains.compose") version "1.0.1"
    id("com.android.application")
    id("com.aguragorn.androidconfig")
    id("com.aguragorn.optins")
//    id("com.aguragorn.optins")
}

group = "com.aguragorn.whatword"
version = "1.0"

dependencies {
    implementation(project(":ui:common-ui"))
    implementation("androidx.activity:activity-compose:1.4.0")
}

android {
    defaultConfig {
        applicationId = "com.aguragorn.whatword.android"
        versionCode = 1
        versionName = "1.0"
    }
}