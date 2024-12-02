plugins {
    id("com.android.library")
    kotlin("android")
    `maven-publish`
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
    implementation(libs.androidx.annotation)
    api(libs.image.decoder)
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/wwww-wwww/image-decoder")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }
        publications {
            register<MavenPublication>("gpr") {
                from(components["release"])
                groupId = "dev.mihon"
                artifactId = "subsampling-scale-image-view"
                version = "4.0.0"
            }
        }
    }
}
