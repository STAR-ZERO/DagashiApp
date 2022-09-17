plugins {
    id("com.star_zero.dagashi.build.library.compose")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.star_zero.dagashi.features.milestone"
}

dependencies {
    implementation(projects.androidApp.core)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    testImplementation(projects.androidApp.testutils)
    testImplementation(libs.junit)
    testImplementation(libs.truth)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

kapt {
    correctErrorTypes = true
}
