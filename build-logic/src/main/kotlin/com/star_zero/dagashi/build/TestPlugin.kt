package com.star_zero.dagashi.build

import com.android.build.gradle.TestExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle Plugin for test/benchmark module
 */
class TestPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        project.pluginManager.run {
            apply("com.android.test")
            apply("org.jetbrains.kotlin.android")
        }

        project.extensions.configure<TestExtension> {
            configureAndroid(project, libs)
        }
    }
}
