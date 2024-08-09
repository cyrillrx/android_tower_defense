plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk
    }

    namespace = "org.es.game.mechanic"
}

dependencies {
    implementation(libs.kotlin.stdlib.jdk7)
    implementation(libs.support.v4)
}
