plugins {
    id("com.star_zero.dagashi.build.library.compose")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.star_zero.dagashi.features.favorite"
}

dependencies {
    implementation(projects.androidApp.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

kapt {
    correctErrorTypes = true
}
