buildscript {
    apply(from = "buildSrc/build_dependencies.gradle.kts")
    apply<DagashiRootPlugin>()

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Deps.ANDROID_GRADLE_PLUGIN)
        classpath(Deps.KOTLIN_PLUGIN)
        classpath(Deps.DAGGER_HILT_PLUGIN)
        classpath(Deps.NAVIGATION_SAFE_ARGS_PLUGIN)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
