plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
    }
    namespace = "org.es.game.utils"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:2.0.0")

    implementation("com.android.support:support-v4:28.0.0")
}
