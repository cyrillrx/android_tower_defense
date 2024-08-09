import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    compileSdk = Version.compileSdk

//    val date = SimpleDateFormat("yyyy-MM-dd").format(Date())
//    applicationVariants.all {
//        outputs.all {
//            val variant = this
//            variant.outputFileName = "${date}_TowerDefense_v${variant.versionName}(${variant.versionCode})_${variant.flavorName}-${variant.buildType.name}.apk"
//        }
//    }

    defaultConfig {
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk

        applicationId = "org.es.towerdefense"

        versionCode = Version.appVersionCode
        versionName = Version.appVersionName

        val secureFile = rootProject.file("secure.properties")
        val secureProp = Properties()
        FileInputStream(secureFile).use { secureProp.load(it) }

        manifestPlaceholders["google_game_api"] = secureProp["google_game_api"] as String
        manifestPlaceholders["crashlytics_api"] = secureProp["crashlytics_api"] as String
    }

    signingConfigs {
        getByName("debug") {
            storeFile = rootProject.file("debug.keystore")
        }

        create("release") {
            storeFile = rootProject.file("release.keystore")

            val propertiesFile = rootProject.file("keystore.properties")
            val properties = Properties()
            FileInputStream(propertiesFile).use { properties.load(it) }

            storePassword = properties["storePassword"] as String
            keyAlias = properties["keyAlias"] as String
            keyPassword = properties["keyPassword"] as String
        }
    }

    buildTypes {
        getByName("debug") {
            applicationIdSuffix = ".debug"
        }
    }

    namespace = "org.es.towerdefense"

    dependencies {
        implementation(project(":game-mechanic"))
        // implementation(project(":game-utils"))

        implementation(libs.kotlin.stdlib.jdk7)
        implementation(libs.support.v4)
    }
}
