pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}
rootProject.name = "WhatWord"


include(":android")
include(":desktop")
include(":common-ui")
include("web")
include("keyboard")
include("core")
include("grid")
include("validator")
