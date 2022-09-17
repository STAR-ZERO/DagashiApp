import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.star_zero.dagashi.build.library.compose")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.protobuf")
}

android {
    namespace = "com.star_zero.dagashi.core"
}

dependencies {
    api(project(":shared"))

    api(libs.bundles.androidx.compose)

    api(libs.androidx.core)
    api(libs.androidx.compat)

    api(libs.material)
    api(libs.material3)
    api(libs.material3.window.size)

    implementation(libs.androidx.browser)

    api(libs.coroutines.android)

    api(libs.androidx.lifecycle.viewmodel)
    api(libs.androidx.lifecycle.livedata)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    api(libs.androidx.datastore)
    api(libs.protobuf.java)

    api(libs.napier)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.espresso)

    debugImplementation(libs.androidx.compose.ui.tooling)
}

kapt {
    correctErrorTypes = true
}

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                val java by registering {
                    option("lite")
                }
            }
        }
    }
}