
import com.google.protobuf.gradle.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    id("dagger.hilt.android.plugin")
    id("com.google.protobuf")
}

apply<DagashiPlugin>()

dependencies {
    api(project(":shared"))

    api(Deps.KOTLIN_STDLIB)

    api(Deps.ANDROIDX_CORE)
    api(Deps.APPCOMPAT)
    api(Deps.MATERIAL)

    api(Deps.COROUTINE)

    implementation(Deps.DAGGER_HILT_ANDROID)
    kapt(Deps.DAGGER_HILT_COMPILER)

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
