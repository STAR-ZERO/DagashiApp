package com.star_zero.dagashi.build.dependecies

object Dependencies {
    val ANDROID_GRADLE_PLUGIN = "com.android.tools.build:gradle:${Versions.ANDROID_GRADLE_PLUGIN}"
    val KOTLIN_PLUGIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"

    val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val COROUTINE = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9"
    const val COROUTINE_CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9"

    const val COMPOSE_UI = "androidx.compose.ui:ui:${Versions.COMPOSE}"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:${Versions.COMPOSE}"
    const val COMPOSE_UI_TOOLING = "androidx.ui:ui-tooling:${Versions.COMPOSE}"


    // AndroidX
    const val ANDROIDX_CORE = "androidx.core:core-ktx:1.3.1"
    const val APPCOMPAT = "androidx.appcompat:appcompat:1.2.0"

    const val CONSTRAINT = "androidx.constraintlayout:constraintlayout:2.0.1"
    const val MATERIAL = "com.google.android.material:material:1.2.1"

    const val FRAGMENT = "androidx.fragment:fragment-ktx:1.3.0-alpha08"

    private const val LIFECYCLE_VERSION = "2.3.0-alpha07"
    const val VIEWMODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:$LIFECYCLE_VERSION"
    const val LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:$LIFECYCLE_VERSION"

    const val DATASTORE = "androidx.datastore:datastore-core:1.0.0-alpha01"

    private const val ANDROIDX_HILT_VERSION = "1.0.0-alpha02"
    const val ANDROIDX_HILT_VIEWMODEL = "androidx.hilt:hilt-lifecycle-viewmodel:$ANDROIDX_HILT_VERSION"
    const val ANDROIDX_HILT_COMPILER = "androidx.hilt:hilt-compiler:$ANDROIDX_HILT_VERSION"

    private const val NAVIGATION_VERSION = "2.3.0"
    const val NAVIGATION_FRAGMENT = "androidx.navigation:navigation-fragment-ktx:$NAVIGATION_VERSION"
    const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:$NAVIGATION_VERSION"
    const val NAVIGATION_SAFE_ARGS_PLUGIN = "androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_VERSION"

    const val BROWSER = "androidx.browser:browser:1.2.0"

    // Dagger
    private const val DAGGER_HILT_VERSION = "2.28.3-alpha"
    const val DAGGER_HILT_PLUGIN = "com.google.dagger:hilt-android-gradle-plugin:$DAGGER_HILT_VERSION"
    const val DAGGER_HILT_ANDROID = "com.google.dagger:hilt-android:$DAGGER_HILT_VERSION"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:$DAGGER_HILT_VERSION"

    // API
    private const val KTOR_VERSION = "1.4.1"
    const val KTOR_CORE = "io.ktor:ktor-client-core:$KTOR_VERSION"
    const val KTOR_ANDROID = "io.ktor:ktor-client-android:$KTOR_VERSION"
    const val KTOR_IOS = "io.ktor:ktor-client-ios:$KTOR_VERSION"
    const val KTOR_SERIALIZATION = "io.ktor:ktor-client-serialization:$KTOR_VERSION"

    val SERIALIZATION_PLUGIN = "org.jetbrains.kotlin:kotlin-serialization:${Versions.KOTLIN}"
    const val SERIALIZATION_JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.0"

    // etc
    const val PROTOBUF_JAVA = "com.google.protobuf:protobuf-javalite:3.10.0"
    const val PROTOC = "com.google.protobuf:protoc:3.10.0"

    const val ACCOMPANIST_COIL = "dev.chrisbanes.accompanist:accompanist-coil:0.3.0"

    // Test
    const val JUNIT = "junit:junit:4.13"
    const val ANDROIDX_JUNIT = "androidx.test.ext:junit:1.1.2"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:3.3.0"
}
