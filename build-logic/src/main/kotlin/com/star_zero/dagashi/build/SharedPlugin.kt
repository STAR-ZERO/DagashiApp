package com.star_zero.dagashi.build

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Gradle Plugin for shared module which used Kotlin multiplatform
 */
class SharedPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.pluginManager.run {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.library")
        }

        project.extensions.configure<LibraryExtension> {
            configureSdkAndroidVersions()
            sourceSets.getByName("main").manifest.srcFile("src/androidMain/AndroidManifest.xml")
            project.tasks.withType<KotlinCompile>().configureEach {
                kotlinOptions {
                    jvmTarget = JavaVersion.VERSION_17.toString()
                }
            }
            // TODO
            // configureKotlinExplicitApi()
        }

        project.extensions.configure<KotlinMultiplatformExtension> {
            jvmToolchain(17)
        }
    }
}
