plugins {

}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(libs.android.gradle)
        classpath(libs.kotlin.gradle)
    }
}

allprojects {
    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
            compilerOptions {
                jvmTarget.set(Config.jvmTarget)
                languageVersion.set(Config.kotlinLanguageVersion)
                freeCompilerArgs.addAll(Config.kotlinCompilerArgs)
            }
        }
    }
}
