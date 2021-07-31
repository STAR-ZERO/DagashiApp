package com.star_zero.dagashi.build.dependecies

object Dependencies {
    val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"

    val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.0"
    const val COROUTINE_NATIVE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.0-native-mt"

    // Compose
    val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
    val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE}"

    // Compose Desktop
    val COMPOSE_DESKTOP_PLUGIN = "org.jetbrains.compose:compose-gradle-plugin:0.2.0-build132"

    // AndroidX
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.6.0"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.3.0"

    const val MATERIAL = "com.google.android.material:material:1.2.1"

    private const val LIFECYCLE_VERSION = "2.4.0-alpha02"
    const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"

    const val DATASTORE = "androidx.datastore:datastore:1.0.0-rc01"

    const val NAVIGATION_COMPOSE = "androidx.navigation:navigation-compose:1.0.0-alpha10"
    const val HILT_NAVIGATION_COMPOSE = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"

    const val BROWSER = "androidx.browser:browser:1.2.0"

    // Dagger
    private const val DAGGER_HILT_VERSION = "2.37"
    const val DAGGER_HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$DAGGER_HILT_VERSION"
    const val DAGGER_HILT_ANDROID = "com.google.dagger:hilt-android:$DAGGER_HILT_VERSION"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-compiler:$DAGGER_HILT_VERSION"

    // API
    private const val KTOR_VERSION = "1.5.4"
    const val KTOR_CORE = "io.ktor:ktor-client-core:$KTOR_VERSION"
    const val KTOR_ANDROID = "io.ktor:ktor-client-android:$KTOR_VERSION"
    const val KTOR_DESKTOP = "io.ktor:ktor-client-cio:$KTOR_VERSION"
    const val KTOR_IOS = "io.ktor:ktor-client-ios:$KTOR_VERSION"
    const val KTOR_SERIALIZATION = "io.ktor:ktor-client-serialization:$KTOR_VERSION"

    val SERIALIZATION_PLUGIN = "org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}"
    const val SERIALIZATION_JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2"

    // DB
    private const val SQLDELIGHT_VERSION = "1.5.0"
    const val SQLDELIGHT_PLUGIN = "com.squareup.sqldelight:gradle-plugin:$SQLDELIGHT_VERSION"
    const val SQLDELIGHT_RUNTIME = "com.squareup.sqldelight:runtime:$SQLDELIGHT_VERSION"
    const val SQLDELIGHT_ANDROID = "com.squareup.sqldelight:android-driver:$SQLDELIGHT_VERSION"
    const val SQLDELIGHT_SQLITE = "com.squareup.sqldelight:sqlite-driver:$SQLDELIGHT_VERSION"
    const val SQLDELIGHT_NATIVE = "com.squareup.sqldelight:native-driver:$SQLDELIGHT_VERSION"

    // etc
    const val PROTOBUF_PLUGIN = "com.google.protobuf:protobuf-gradle-plugin:0.8.14"
    const val PROTOBUF_JAVA = "com.google.protobuf:protobuf-javalite:3.10.0"
    const val PROTOC = "com.google.protobuf:protoc:3.10.0"

    private const val ACCOMPANIST_VERSION = "0.15.0"
    const val ACCOMPANIST_COIL = "com.google.accompanist:accompanist-coil:$ACCOMPANIST_VERSION"
    const val ACCOMPANIST_FLOW = "com.google.accompanist:accompanist-flowlayout:$ACCOMPANIST_VERSION"

    // Test
    const val JUNIT = "junit:junit:4.13"
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:1.1.2"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:3.3.0"
}
