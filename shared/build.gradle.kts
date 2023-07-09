plugins {
    id("com.star_zero.dagashi.build.shared")
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqldelight)
}

kotlin {
    android()

    listOf(
        iosX64(),
        iosArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.coroutines.core)
                implementation(libs.ktor.core)
                implementation(libs.ktor.content.negotiation)
                implementation(libs.ktor.serialization)
                implementation(libs.ktor.logging)
                implementation(libs.kotlinx.serialization)
                implementation(libs.sqldelight.coroutines)
                implementation(libs.sqldelight.primitive.adapters)
                implementation(libs.napier)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test.common)
                implementation(libs.kotlin.test.annotations)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.android)
                implementation(libs.sqldelight.android)
                implementation(libs.androidx.compose.runtime)
            }
        }
        val androidUnitTest by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.kotlin.test.junit)
                implementation(libs.junit)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)

            dependencies {
                implementation(libs.ktor.ios)
                implementation(libs.sqldelight.native)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
        }
    }

    val ignoreSourceSets = listOf(
        "androidAndroidTestRelease",
        "androidTestFixtures",
        "androidTestFixturesDebug",
        "androidTestFixturesRelease"
    )

    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")

            // ignore unused source sets
            if (ignoreSourceSets.contains(name)) {
                remove(this)
            }
        }
    }
}

android {
    namespace = "com.star_zero.dagashi.shared"
}

sqldelight {
    databases {
        create("DagashiDatabase") {
            packageName.set("com.star_zero.dagashi.shared.db")
        }
    }
}
