import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = libs.versions.networkNamespace.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = libs.versions.androidJUnitRunner.get()
        consumerProguardFiles(libs.versions.consumerRules.get())
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(libs.versions.progaurdTxt.get()),
                libs.versions.progaurdRules.get()
            )
        }

        android {
            flavorDimensions.add(
                "environment"
            )
            productFlavors {
                val properties = Properties()
                properties.load(project.rootProject.file("gradle.properties").inputStream())
                create(
                    "dev"
                ) {
                    dimension =
                        "environment"
                    buildConfigField(
                        "String", "API_KEY", properties.getProperty("api_key")
                    )
                    buildConfigField(
                        "String", "BASE_URL", "\"https://api.themoviedb.org/3/\""
                    )

                }
                create(
                    "prod"
                ) {
                    dimension =
                        "environment"
                    buildConfigField(
                        "String", "API_KEY", properties.getProperty("api_key")
                    )
                    buildConfigField(
                        "String", "BASE_URL", "\"https://api.themoviedb.org/3/\""
                    )

                }
            }
        }


    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }

    buildFeatures {
        buildConfig = true
    }
}



dependencies {

    implementation(project(":common"))
    implementation(libs.javax.inject)

    //dagger
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Service Manager
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    // testImplementation for pure JVM unit tests
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    // Run Blocking Test
    testImplementation(libs.core.testing)
    // For small test - large test annotations
    testImplementation(libs.androidx.runner)
    // Mock objects
    testImplementation(libs.mockk.mockk)

    // For small test - large test annotations
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}