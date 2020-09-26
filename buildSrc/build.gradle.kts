plugins {
    `kotlin-dsl`
}

apply(from = "build_dependencies.gradle.kts")

repositories {
    google()
    jcenter()
}

dependencies {
    implementation("com.android.tools.build:gradle:${project.extra["android_gradle_plugin_version"]}")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlin_version"]}")
}
