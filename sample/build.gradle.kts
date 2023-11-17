plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.davemorrissey.labs.subscaleview.test"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.davemorrissey.labs.subscaleview.test"
        minSdk = 21
        targetSdk = 34

        versionCode = 4
        versionName = "3.1.0"

        defaultConfig {
            packagingOptions {
                jniLibs.keepDebugSymbols.addAll(listOf("*/mips/*.so", "*/mips64/*.so"))
            }
        }

        compileOptions {
            viewBinding.isEnabled = true
        }
    }

    sourceSets {
        getByName("main").assets.srcDirs("assets")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}

dependencies {
    implementation(project(":library"))
    implementation("androidx.appcompat:appcompat:1.7.0-alpha03")
    implementation("androidx.viewpager:viewpager:1.1.0-alpha01")
}
