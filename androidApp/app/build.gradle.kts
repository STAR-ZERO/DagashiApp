plugins {
    id("com.star_zero.dagashi.build.application")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.star_zero.dagashi"

    defaultConfig {
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        val benchmark by creating {
            signingConfig = signingConfigs.getByName("debug")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "benchmark-rules.pro"
            )
            matchingFallbacks.add("release")
            isDebuggable = false
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {
    implementation(projects.androidApp.core)
    implementation(projects.androidApp.features.milestone)
    implementation(projects.androidApp.features.issue)
    implementation(projects.androidApp.features.favorite)
    implementation(projects.androidApp.features.setting)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.profileinstaller)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

kapt {
    correctErrorTypes = true
}
