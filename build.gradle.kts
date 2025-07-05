// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        maven { url  = uri("https://jitpack.io")}
    }
    dependencies {
        val nav_version = "2.9.0"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath(kotlin("gradle-plugin", version = "2.1.21"))
    }
}
plugins {
    id("com.android.application") version "8.10.1" apply false
    id("org.jetbrains.kotlin.android") version "2.1.21" apply false
    id("com.google.devtools.ksp") version "2.1.21-2.0.1" apply false
    kotlin("jvm") version "2.1.21" apply false
    kotlin("kapt") version "2.1.21"
}