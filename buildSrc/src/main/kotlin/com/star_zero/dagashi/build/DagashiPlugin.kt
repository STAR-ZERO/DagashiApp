package com.star_zero.dagashi.build

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

@Suppress("UnstableApiUsage")
class DagashiPlugin : Plugin<Project> {
    override fun apply(project: Project) {

        // common setting
        val commonExtension = project.extensions.getByType(CommonExtension::class)
        commonExtension.apply {
            compileSdk = AndroidConfigurations.COMPILE_SDK_VERSION
            defaultConfig {
                minSdk = AndroidConfigurations.MIN_SDK_VERSION
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions.apply {
                sourceCompatibility = JavaVersion.VERSION_1_8
                targetCompatibility = JavaVersion.VERSION_1_8
            }
        }

        // for application module
        if (commonExtension is ApplicationExtension) {
            commonExtension.apply {
                defaultConfig {
                    targetSdk = AndroidConfigurations.TARGET_SDK_VERSION
                }
            }
        }

        // for library module
        if (commonExtension is LibraryExtension) {
            commonExtension.apply {
                defaultConfig {
                    targetSdk = AndroidConfigurations.TARGET_SDK_VERSION
                    consumerProguardFiles("consumer-rules.pro")
                }
                // Don't create BuildConfig in library module
                buildFeatures.buildConfig = false
            }
        }

        // for Kotlin
        project.tasks.withType<KotlinCompile>().configureEach {
            kotlinOptions.jvmTarget = "1.8"
            val args = listOf(
                "-Xskip-prerelease-check",
                "-Xopt-in=kotlin.RequiresOptIn"
            )
            kotlinOptions.freeCompilerArgs = kotlinOptions.freeCompilerArgs + args
        }
    }
}
