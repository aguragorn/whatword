pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}
rootProject.name = "WhatWord"


include("ui:android")
include("ui:desktop")
include("ui:common-ui")
include("ui:web")
include("keyboard")
include("core")
include("grid")
include("validator")
