plugins {
    alias(libs.plugins.detekt)
}
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

val reportMerge by tasks.registering(io.gitlab.arturbosch.detekt.report.ReportMergeTask::class) {
    output.set(rootProject.buildDir.resolve("reports/detekt/merge.sarif"))
}

val detektPluginId = libs.plugins.detekt.get().pluginId
val detektPluginVersion = libs.versions.detekt.get()
val detektFormatting = libs.detekt.formatting

subprojects {
    apply(plugin = detektPluginId)

    detekt {
        basePath = rootDir.absolutePath
        toolVersion = detektPluginVersion
        config = files("${rootDir.absolutePath}/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        parallel = true
        ignoreFailures = isCI
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        exclude {
            it.file.absolutePath.contains("build/generated")
        }
        reports {
            sarif.required.set(true)
        }

        // Merge sarif report file
        finalizedBy(reportMerge)
        reportMerge.configure {
            input.from(sarifReportFile)
        }
    }

    dependencies {
        detektPlugins(detektFormatting)
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
