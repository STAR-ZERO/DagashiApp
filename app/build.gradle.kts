plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
}

apply<DagashiPlugin>()

android {
    defaultConfig {
        applicationId = "com.star_zero.dagashi"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE
        kotlinCompilerVersion = Versions.KOTLIN
    }
}

dependencies {
    implementation(project(":core"))

    implementation(Deps.COMPOSE_UI)
    implementation(Deps.COMPOSE_MATERIAL)
    implementation(Deps.COMPOSE_UI_TOOLING)

    implementation(Deps.CONSTRAINT)
    implementation(Deps.FRAGMENT)

    implementation(Deps.VIEWMODEL)
    implementation(Deps.LIVEDATA)

    implementation(Deps.NAVIGATION_FRAGMENT)
    implementation(Deps.NAVIGATION_UI)

    implementation(Deps.DAGGER_HILT_ANDROID)
    kapt(Deps.DAGGER_HILT_COMPILER)

    implementation(Deps.ANDROIDX_HILT_VIEWMODEL)
    kapt(Deps.ANDROIDX_HILT_COMPILER)

    implementation(Deps.BROWSER)

    testImplementation(Deps.JUNIT)
    androidTestImplementation(Deps.ANDROIDX_JUNIT)
    androidTestImplementation(Deps.ESPRESSO)
}
