plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
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
    implementation(project(":conferences-feature-domain"))
    implementation(project(":conferences-feature-core"))
    implementation(project(":core-ui"))
    implementation(project(":core-utils"))
    implementation(project(":core-domain"))

    implementation(Dependencies.kotlin)

    implementation(Dependencies.appCompat)
    implementation(Dependencies.design)
    implementation(Dependencies.constraintLayout)
    implementation(Dependencies.viewModel)
    kapt("androidx.lifecycle:lifecycle-compiler:2.0.0")

    implementation(Dependencies.rxRelay)

    implementation(Dependencies.cicerone)

    implementation(Dependencies.ktx)
    implementation(Dependencies.rxAndroid)

    implementation(Dependencies.Dagger.runtime)
    kapt(Dependencies.Dagger.kapt)

    implementation(Dependencies.picasso)

    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.testRunner)
    androidTestImplementation(Dependencies.Test.espresso)
}
