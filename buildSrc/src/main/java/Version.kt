import org.gradle.api.JavaVersion

object Version {
    const val minSdk = 23
    const val targetSdk = 34
    const val compileSdk = 34

    const val appVersionCode = 1
    const val appVersionName = "Pre-Alpha 2 (v0.2.7)"

    val java = JavaVersion.VERSION_17
    val jvm = JavaVersion.VERSION_17.majorVersion.toInt()
}
