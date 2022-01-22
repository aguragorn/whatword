package com.aguragorn

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper

open class AndroidConfig : Plugin<Project> {
    override fun apply(target: Project) {
        target.extensions.getByType<BaseExtension>().run {
            compileSdkVersion(31)

            if (target.isMultiplatform()) {
                sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
            }

            defaultConfig {
                minSdkVersion(24)
                targetSdkVersion(31)
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }
    }
}

internal fun Project.isMultiplatform(): Boolean {
    return plugins.any { it is KotlinMultiplatformPluginWrapper }
}