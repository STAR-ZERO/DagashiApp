plugins {
    id("com.star_zero.dagashi.build.library")
}

android {
    namespace = "com.star_zero.dagashi.testutils"
}

dependencies {
    implementation(projects.shared)

    implementation(libs.androidx.compose.runtime)

    implementation(libs.junit)
    implementation(libs.coroutines.test)
}
