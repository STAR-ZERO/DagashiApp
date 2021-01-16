buildscript {
    apply(from = "buildSrc/build_dependencies.gradle.kts")
    apply<DagashiRootPlugin>()

    repositories {
        google()
        jcenter()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    dependencies {
        classpath(Deps.ANDROID_GRADLE_PLUGIN)
        classpath(Deps.KOTLIN_PLUGIN)
        classpath(Deps.DAGGER_HILT_PLUGIN)
        classpath(Deps.SERIALIZATION_PLUGIN)
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.14")
        classpath("com.squareup.sqldelight:gradle-plugin:1.4.3")
        classpath("org.jetbrains.compose:compose-gradle-plugin:0.2.0-build132")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
