object Versions {
    const val kotlin = "1.3.31"
    const val rxJava = "2.2.8"
    const val rxAndroid = "2.1.1"
    const val dagger = "2.22.1"
    const val moshi = "1.8.0"
    const val cicerone = "5.0.0"
    const val room = "2.1.0-alpha07"
    const val ktx = "1.0.1"
    const val stetho = "1.5.1"
    const val retrofit = "2.5.0"
    const val picasso = "2.7182"
}

object Dependencies {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val rxJava = "io.reactivex.rxjava2:rxjava:${Versions.rxJava}"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroid}"

    object Dagger {
        const val runtime = "com.google.dagger:dagger:${Versions.dagger}"
        const val kapt = "com.google.dagger:dagger-compiler:${Versions.dagger}"
    }

    const val moshiKapt = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi}"
    const val cicerone = "ru.terrakok.cicerone:cicerone:${Versions.cicerone}"

    object Room {
        const val runtime = "androidx.room:room-runtime:${Versions.room}"
        const val kapt = "androidx.room:room-compiler:${Versions.room}"
        const val rx = "androidx.room:room-rxjava2:${Versions.room}"
        const val test = "androidx.room:room-testing:${Versions.room}"
    }

    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    const val stetho = "com.facebook.stetho:stetho:${Versions.stetho}"

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val rxAdapter = "com.squareup.retrofit2:adapter-rxjava:${Versions.retrofit}"
    }

    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}
