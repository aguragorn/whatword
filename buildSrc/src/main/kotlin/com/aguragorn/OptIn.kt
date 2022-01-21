package com.aguragorn

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

open class OptIn : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.getByType<KotlinMultiplatformExtension>().run {
            sourceSets {
                all {
                    languageSettings.optIn("kotlin.RequiresOptIn")
                }
            }
        }
    }

}
