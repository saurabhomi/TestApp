plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = libs.versions.domainNamespace.get()
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
    }

    android {
        flavorDimensions.add(
            "environment"
        )
        productFlavors {
            create(
                "dev"
            ) {
                dimension =
                    "environment"

            }
            create(
                "prod"
            ) {
                dimension =
                    "environment"
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
}

dependencies {
    implementation(project(":common"))
    implementation(libs.javax.inject)


    // testImplementation for pure JVM unit tests
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    // Run Blocking Test
    testImplementation(libs.core.testing)
    // For small test - large test annotations
    testImplementation(libs.androidx.runner)
    // Mock objects
    testImplementation(libs.mockk.mockk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

}