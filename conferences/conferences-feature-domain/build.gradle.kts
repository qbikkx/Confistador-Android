plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Setup.compileSdkVersion)
    defaultConfig {
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
        }
    }
    compileOptions {
        sourceCompatibility = Setup.sourceCompat
        targetCompatibility = Setup.targetCompat
    }
}

dependencies {
    implementation(project(":conferences-feature-repository"))
    implementation(project(":conferences-feature-core"))
    implementation(project(":core-utils"))
    implementation(project(":core-domain"))

    implementation(Dependencies.kotlin)

    implementation(Dependencies.rxJava)

    implementation(Dependencies.Dagger.runtime)
    kapt(Dependencies.Dagger.kapt)

    testImplementation(Dependencies.Test.junit)
}