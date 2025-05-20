// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url  = uri("https://jitpack.io")}
    }
    dependencies {
        val nav_version = "2.7.5"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath(kotlin("gradle-plugin", version = "1.9.20"))
    }
}
plugins {
    id("com.android.application") version "8.10.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.devtools.ksp") version "1.9.20-1.0.14" apply false
    kotlin("jvm") version "1.9.20" apply false
    kotlin("kapt") version "1.9.20"
}