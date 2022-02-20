pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}
rootProject.name = "WhatWord"

//enableFeaturePreview("GRADLE_METADATA")

include("ui:android")
include("ui:desktop")
include("ui:common-jetpack")
include("ui:web")
include("game")
include("statistics")
include("config")
