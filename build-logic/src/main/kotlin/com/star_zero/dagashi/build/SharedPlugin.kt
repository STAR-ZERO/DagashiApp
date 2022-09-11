package com.star_zero.dagashi.build

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

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
            // TODO
            // configureKotlinExplicitApi()
        }
    }
}
