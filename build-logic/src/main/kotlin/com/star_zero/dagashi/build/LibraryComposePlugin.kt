package com.star_zero.dagashi.build

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle Plugin for library module which used Jetpack Compose
 */
class LibraryComposePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        project.pluginManager.run {
            apply("com.android.library")
            apply("org.jetbrains.kotlin.android")
        }

        project.extensions.configure<LibraryExtension> {
            configureAndroid(project, libs)
            configureCompose(libs)
            // TODO
            // configureKotlinExplicitApi()
        }
    }
}
