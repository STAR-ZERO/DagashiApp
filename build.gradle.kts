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
        classpath(Deps.PROTOBUF_PLUGIN)
        classpath(Deps.SQLDELIGHT_PLUGIN)
        classpath(Deps.COMPOSE_DESKTOP_PLUGIN)
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
