import Setup
import org.gradle.kotlin.dsl.*

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(Setup.compileSdkVersion)
    defaultConfig {
        applicationId = Setup.applicationId
        minSdkVersion(Setup.minSdkVersion)
        targetSdkVersion(Setup.targetSdkVersion)
        versionCode = Setup.versionCode
        versionName = Setup.versionName
        testInstrumentationRunner = Setup.testInstrumentationRunner
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            //proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFile("proguard-rules.pro")
            //proguardFiles(File("./configs/proguard/libs").listFiles())
        }
    }
}

dependencies {
    //implementation(project(":conferences-feature"))
    implementation(Dependencies.kotlin)
}
