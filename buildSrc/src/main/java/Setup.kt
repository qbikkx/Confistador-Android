import org.gradle.api.JavaVersion

object Setup {
    const val applicationId = "dev.qbikkx.confistador"
    const val minSdkVersion = 21
    const val targetSdkVersion = 28
    const val compileSdkVersion = 28
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    val sourceCompat = JavaVersion.VERSION_1_8
    val targetCompat = JavaVersion.VERSION_1_8
}