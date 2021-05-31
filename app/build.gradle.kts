plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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

    implementation(Deps.NAVIGATION_COMPOSE)

    implementation(Deps.DAGGER_HILT_ANDROID)
    kapt(Deps.DAGGER_HILT_COMPILER)

    implementation(Deps.BROWSER)

    implementation(Deps.ACCOMPANIST_COIL)

    testImplementation(Deps.JUNIT)
    androidTestImplementation(Deps.ANDROIDX_JUNIT)
    androidTestImplementation(Deps.ESPRESSO)
}
