@file:Suppress("MemberVisibilityCanBePrivate", "MissingPackageDeclaration")

import org.gradle.api.JavaVersion

object Config {

    // Don't forget to change the scripts that parse these lines
    const val applicationId = "com.nek12.composedisaster"
    const val versionCode = 1

    const val majorRelease = 1
    const val minorRelease = 0
    const val patch = 0
    const val versionName = "$majorRelease.$minorRelease.$patch ($versionCode)"


    val javaVersion = JavaVersion.VERSION_11
    val jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    const val compileSdk = 33
    const val targetSdk = compileSdk
    const val minSdk = 26
    val kotlinLanguageVersion = org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_1_9

    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val isMinifyEnabledRelease = false
    const val isMinifyEnabledDebug = false
    const val defaultProguardFile = "proguard-android-optimize.txt"
    const val proguardFile = "proguard-rules.pro"
    const val consumerProguardFile = "consumer-rules.pro"

    val supportedLocales = listOf("en")
    val kotlinCompilerArgs = listOf(
        "-Xjvm-default=all", // enable all jvm optimizations
        "-Xcontext-receivers",
        "-Xbackend-threads=0", // parallel IR compilation
        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
        "-opt-in=kotlinx.coroutines.FlowPreview",
        "-opt-in=kotlin.RequiresOptIn",
        "-opt-in=kotlin.ExperimentalStdlibApi",
        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
        "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
//        "-XXLanguage:+ExplicitBackingFields"
    )
}
