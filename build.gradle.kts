plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.protobuf) apply false
    alias(libs.plugins.sqldelight) apply false
    alias(libs.plugins.detekt)
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
