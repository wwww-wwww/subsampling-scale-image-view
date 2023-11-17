plugins {
    id("com.android.library")
}

android {
    namespace = "com.davemorrissey.labs.subscaleview"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        consumerProguardFiles("proguard-rules.txt")
    }
}

dependencies {
    implementation("androidx.annotation:annotation:1.7.0")
    implementation("com.github.tachiyomiorg:image-decoder:fbd6601290")
}
