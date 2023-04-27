plugins {
    id("com.android.application")
    kotlin("android")
    alias(libs.plugins.ksp)
}

android {
    configureAndroid(this)

    namespace = Config.applicationId

    defaultConfig {
        applicationId = Config.applicationId
        targetSdk = Config.targetSdk
        minSdk = Config.minSdk
        compileSdk = Config.compileSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
    }

    applicationVariants.all {
        outputs.all {
            (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl).outputFileName =
                "$applicationId-$versionName.apk"
        }

        sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    buildTypes {
        debug {
            versionNameSuffix = "-debug"
            isShrinkResources = Config.isMinifyEnabledDebug
        }
        release {
            isShrinkResources = Config.isMinifyEnabledRelease
        }
    }

    // removes kotlinx.coroutines debug bins
    packaging {
        resources {
            excludes += setOf(
                "DebugProbesKt.bin",
                "/META-INF/{AL2.0,LGPL2.1}",
                "/META-INF/versions/9/previous-compilation-data.bin"
            )
        }
    }
}

dependencies {
    implementation(libs.bundles.compose)
    implementation(libs.splashscreen)
    implementation(libs.koin.android)
    implementation(libs.compose.destinations)
    ksp(libs.compose.destinations.ksp)
    implementation(libs.koin.compose)
    implementation(libs.androidutils.compose)
    debugImplementation(libs.compose.tooling)
}
