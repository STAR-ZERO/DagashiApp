plugins {
    id("io.gitlab.arturbosch.detekt").version("1.22.0-RC1")
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

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        toolVersion = "1.22.0-RC1"
        config = files("$rootDir/config/detekt/detekt.yml")
        buildUponDefaultConfig = true
        parallel = true
        ignoreFailures = true
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
        exclude {
            it.file.absolutePath.contains("build/generated")
        }
        reports {
            sarif.required.set(true)
        }

        finalizedBy(reportMerge)
        reportMerge.configure {
            input.from(sarifReportFile)
        }
    }

    dependencies {
        detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.22.0-RC1")
    }

//    // ./gradlew ktlintCheck
//    // ./gradlew ktlintFormat
//    apply(plugin = "org.jlleitschuh.gradle.ktlint")
//
//    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
//        version.set("0.45.2")
//        android.set(true)
//        verbose.set(true)
//        ignoreFailures.set(isCI)
//        reporters {
//            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.SARIF)
//        }
//        filter {
//            exclude { element -> element.file.path.contains("generated/") }
//        }
//    }

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

tasks.register<Exec>("mergeKtlint") {
    doFirst {
        mkdir("./build")
    }

    val inputFiles = project.subprojects.map { subproject ->
        File(subproject.buildDir, "reports/ktlint")
            .walk()
            .filter {
                it.isFile && it.extension == "sarif"
            }
            .map { file ->
                file.absolutePath
            }.toList()
    }.flatten()

    executable("npx")

    args(
        listOf(
            "@microsoft/sarif-multitool",
            "merge",
            "--recurse",
            "--force",
            "--output-file=build/mergedKtlintReport.sarif"
        ) + inputFiles
    )
}
