plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
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
    implementation(Dependencies.kotlin)

    implementation(Dependencies.ktx)
    implementation(Dependencies.rxAndroid)

    implementation(Dependencies.appCompat)
    implementation(Dependencies.design)

    implementation(Dependencies.Dagger.runtime)

    implementation(Dependencies.cicerone)

    implementation(Dependencies.picasso)

    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.testRunner)
    androidTestImplementation(Dependencies.Test.espresso)
}