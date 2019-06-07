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
            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFile("proguard-rules.pro")
            file("./configs/proguard/libs").list().forEach { proguardFile(it) }
        }
    }
    compileOptions {
        sourceCompatibility = Setup.sourceCompat
        targetCompatibility = Setup.targetCompat
    }
}

dependencies {
    implementation(project(":conferences-feature"))
    implementation(project(":conferences-feature-core"))
    implementation(project(":core-utils"))
    implementation(project(":core-ui"))

    implementation(Dependencies.Dagger.runtime)
    kapt(Dependencies.Dagger.kapt)

    implementation(Dependencies.kotlin)

    implementation(Dependencies.appCompat)
    implementation(Dependencies.design)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.multidex)

    implementation(Dependencies.stetho)
    implementation(Dependencies.cicerone)
}
