plugins {
    id("com.star_zero.dagashi.build.test")
}

android {
    namespace = "com.star_zero.dagashi.macrobenchmark"

    buildTypes {
        create("benchmark") {
            isDebuggable = true
            signingConfig = signingConfigs.getByName("debug")
            matchingFallbacks.add("release")
        }
    }

    targetProjectPath = ":androidApp:app"
    experimentalProperties["android.experimental.self-instrumenting"] = true
}

dependencies {
    implementation(libs.andoridx.test.core)
    implementation(libs.androidx.junit)
    implementation(libs.espresso)
    implementation(libs.uiautomator)
    implementation(libs.benchmark.macro.junit4)
    implementation(libs.profileinstaller)
}

androidComponents {
    beforeVariants {
        it.enable = it.buildType == "benchmark"
    }
}
