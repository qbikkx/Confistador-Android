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
        getByName("debug") {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_API_URL", "\"https://raw.githubusercontent.com/tech-conferences/conference-data/master/\"")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
            proguardFile("proguard-rules.pro")
            buildConfigField("String", "BASE_API_URL", "\"https://raw.githubusercontent.com/tech-conferences/conference-data/master/\"")
        }
    }
    compileOptions {
        sourceCompatibility = Setup.sourceCompat
        targetCompatibility = Setup.targetCompat
    }
}

dependencies {
    implementation(Dependencies.kotlin)

    implementation(Dependencies.rxJava)

    implementation(Dependencies.Dagger.runtime)
    kapt(Dependencies.Dagger.kapt)

    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.rxAdapter)
    implementation(Dependencies.Retrofit.moshiConverter)

    implementation(Dependencies.Moshi.runtime)
    kapt(Dependencies.Moshi.kapt)

    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.testRunner)
}