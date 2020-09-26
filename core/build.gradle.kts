import com.google.protobuf.gradle.builtins
import com.google.protobuf.gradle.generateProtoTasks
import com.google.protobuf.gradle.id
import com.google.protobuf.gradle.protobuf
import com.google.protobuf.gradle.protoc

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    id("com.google.protobuf") version "0.8.13"
}

apply<DagashiPlugin>()

dependencies {
    api(Deps.KOTLIN_STDLIB)

    api(Deps.ANDROIDX_CORE)
    api(Deps.APPCOMPAT)
    api(Deps.MATERIAL)

    api(Deps.COROUTINE)

    implementation(Deps.DAGGER_HILT_ANDROID)
    kapt(Deps.DAGGER_HILT_COMPILER)

    implementation(Deps.RETROFIT)
    implementation(Deps.RETROFIT_MOSHI)
    implementation(Deps.OKHTTP)
    implementation(Deps.MOSHI)
    kapt(Deps.MOSHI_CODEGEN)

    api(Deps.DATASTORE)
    api(Deps.PROTOBUF_JAVA)

    testImplementation(Deps.JUNIT)
    androidTestImplementation(Deps.ANDROIDX_JUNIT)
    androidTestImplementation(Deps.ESPRESSO)
}

protobuf {
    protoc {
        artifact = Deps.PROTOC
    }

    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                id("java") {
                    option("lite")
                }
            }
        }
    }
}
