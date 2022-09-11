plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.gradle)
    implementation(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        create("application") {
            id = "com.star_zero.dagashi.build.application"
            implementationClass = "com.star_zero.dagashi.build.ApplicationPlugin"
        }
        create("library") {
            id = "com.star_zero.dagashi.build.library"
            implementationClass = "com.star_zero.dagashi.build.LibraryPlugin"
        }
        create("libraryCompose") {
            id = "com.star_zero.dagashi.build.library.compose"
            implementationClass = "com.star_zero.dagashi.build.LibraryComposePlugin"
        }
        create("shared") {
            id = "com.star_zero.dagashi.build.shared"
            implementationClass = "com.star_zero.dagashi.build.SharedPlugin"
        }
        create("test") {
            id = "com.star_zero.dagashi.build.test"
            implementationClass = "com.star_zero.dagashi.build.TestPlugin"
        }
    }
}
