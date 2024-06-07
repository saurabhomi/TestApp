plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = libs.versions.presentationNamespace.get()
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion =
            libs.versions.kotlinCompilerExtensionVersion.get()
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {

    implementation(project(":base"))
    implementation(project(":domain"))
    implementation(project(":common"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.ui.android)
    testImplementation(libs.junit)
    testImplementation(project(":network"))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.hilt.android)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v280)
    ksp(libs.hilt.android.compiler)

    // testImplementation for pure JVM unit tests
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    // Run Blocking Test
    testImplementation(libs.core.testing)
    // For small test - large test annotations
    testImplementation(libs.androidx.runner)
    // Mock objects
    testImplementation(libs.mockk.mockk)

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.navigation.compose)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.hilt.navigation.compose)

    // Service Manager
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    //Image Loader
    implementation(libs.coil.compose)
}