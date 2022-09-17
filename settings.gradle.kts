pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id.startsWith("com.google.protobuf")) {
                useModule("com.google.protobuf:protobuf-gradle-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    // TODO: https://youtrack.jetbrains.com/issue/KT-51379/Build-fails-when-using-RepositoriesMode-FAIL-ON-PROJECT-REPOS-wi
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

includeBuild("build-logic")

rootProject.name = "DagashiApp"
include(":shared")
include(":androidApp:app")
include(":androidApp:core")
include(":androidApp:features:issue")
include(":androidApp:features:setting")
include(":androidApp:features:milestone")
include(":androidApp:features:favorite")
include(":androidApp:testutils")
include(":androidApp:macrobenchmark")
