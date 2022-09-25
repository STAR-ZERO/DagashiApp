package com.star_zero.dagashi.build

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle Plugin for application module
 */
class ApplicationPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        val libs = project.extensions.getByType<VersionCatalogsExtension>().named("libs")

        project.pluginManager.run {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
        }

        project.extensions.configure<BaseAppModuleExtension> {
            configureAndroid(project, libs)
            configureCompose(libs)

            defaultConfig {
                targetSdk = SdkVersions.TARGET
            }

            lint {
                @Suppress("UnstableApiUsage")
                checkDependencies = true
            }
        }
    }
}
