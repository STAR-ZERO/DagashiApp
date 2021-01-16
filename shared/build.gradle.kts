import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.squareup.sqldelight")
}

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

kotlin {
    android()
    jvm("desktop")
    ios {
        binaries {
            framework {
                baseName = "shared"
            }
        }
    }

    // https://github.com/cashapp/sqldelight/issues/2044#issuecomment-721319037
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.COROUTINE_NATIVE)
                implementation(Deps.KTOR_CORE)
                implementation(Deps.KTOR_SERIALIZATION)
                implementation(Deps.SERIALIZATION_JSON)
                implementation("com.squareup.sqldelight:runtime:1.4.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Deps.KTOR_ANDROID)
                implementation("com.squareup.sqldelight:android-driver:1.4.3")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation(Deps.KTOR_DESKTOP)
                implementation("com.squareup.sqldelight:sqlite-driver:1.4.3")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Deps.KTOR_IOS)
                implementation("com.squareup.sqldelight:native-driver:1.4.3")
            }
        }
        val iosTest by getting
    }
}

apply<DagashiPlugin>()

android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

val packForXcode by tasks.creating(Sync::class) {
    group = "build"
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
    val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
    val framework =
        kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)
    val targetDir = File(buildDir, "xcode-frameworks")
    from({ framework.outputDirectory })
    into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)

sqldelight {
    database("DagashiDatabase") {
        packageName = "com.star_zero.dagashi.shared.db"
    }
}
