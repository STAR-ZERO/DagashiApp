buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.kotlinx.serialization.gradle)
        classpath(libs.hilt.gradle)
        classpath(libs.ktlint.gradle)
        classpath(libs.protobuf.gradle)
        classpath(libs.sqldelight.gradle)
    }
}

val isCI = System.getenv("CI") != null

subprojects {
    // ./gradlew ktlintCheck
    // ./gradlew ktlintFormat
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        version.set("0.45.2")
        android.set(true)
        verbose.set(true)
        ignoreFailures.set(isCI)
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
        }
        filter {
            exclude("**/build/generated/**")
        }
    }

    // test task for CI
    // TODO: Currently, kmm test is not working
    // TODO: Find more smart way
    if (project.name != "shared" && project.name != "macrobenchmark") {
        afterEvaluate {
            if (project.extensions.findByName("android") != null) {
                project.tasks.register("testCI") {
                    dependsOn("testDebugUnitTest")
                }
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
