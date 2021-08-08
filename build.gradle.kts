buildscript {
    apply(from = "buildSrc/build_dependencies.gradle.kts")
    apply<DagashiRootPlugin>()

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        gradlePluginPortal()
    }
    dependencies {
        classpath(Deps.ANDROID_GRADLE_PLUGIN)
        classpath(Deps.KOTLIN_PLUGIN)
        classpath(Deps.DAGGER_HILT_PLUGIN)
        classpath(Deps.SERIALIZATION_PLUGIN)
        classpath(Deps.PROTOBUF_PLUGIN)
        classpath(Deps.SQLDELIGHT_PLUGIN)
        classpath(Deps.COMPOSE_DESKTOP_PLUGIN)
        classpath(Deps.GRADLE_VERSIONS_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        gradlePluginPortal()
    }
    // ./gradlew dependencyUpdates -DoutputFormatter=html
    apply(plugin = "com.github.ben-manes.versions")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
