package com.star_zero.dagashi.build

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Configure SDK android versions
 */
fun CommonExtension<*, *, *, *, *>.configureSdkAndroidVersions() {
    compileSdk = SdkVersions.COMPILE
    defaultConfig {
        minSdk = SdkVersions.MIN
    }
}

/**
 * Configure common android settings
 */
@Suppress("UnstableApiUsage")
fun CommonExtension<*, *, *, *, *>.configureAndroid(project: Project, libs: VersionCatalog) {
    configureSdkAndroidVersions()

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        isCoreLibraryDesugaringEnabled = true
    }

    project.tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_17.toString()
        }
    }

    project.dependencies {
        add("coreLibraryDesugaring", libs.findLibrary("desugar-jdk").get())
    }

    lint {
        sarifReport = true
    }
}

/**
 * Configure for Jetpack Compose
 */
@Suppress("UnstableApiUsage")
fun CommonExtension<*, *, *, *, *>.configureCompose(libs: VersionCatalog) {
    buildFeatures {
        compose = true
    }

    // Compose compiler version
    composeOptions {
        kotlinCompilerExtensionVersion = libs.findVersion("androidx-compose-compiler").get().toString()
    }
}

/**
 * Apply [Explicit API mode](https://github.com/Kotlin/KEEP/blob/master/proposals/explicit-api-mode.md)
 */
fun CommonExtension<*, *, *, *, *>.configureKotlinExplicitApi() {
    (this as ExtensionAware).extensions.configure<KotlinJvmOptions>("kotlinOptions") {
        freeCompilerArgs = freeCompilerArgs + "-Xexplicit-api=strict"
    }
}
