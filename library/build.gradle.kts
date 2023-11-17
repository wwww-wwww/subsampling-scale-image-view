plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.davemorrissey.labs.subscaleview"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        consumerProguardFiles("proguard-rules.txt")
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
    implementation("androidx.annotation:annotation:1.7.0")
    implementation("com.github.tachiyomiorg:image-decoder:fbd6601290")
}
