plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.google.gms.google.services) apply false
}
buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.0.0") // Adjust this if needed
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0") // Ensure compatible Kotlin version
    }
}
