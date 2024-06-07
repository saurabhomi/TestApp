plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.ksp)
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = libs.versions.jvmTarget.get()
    }
}

dependencies {
    implementation(project(":base"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.converter.gson)
    implementation(libs.javax.inject)

    //dagger
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // testImplementation for pure JVM unit tests
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    // Run Blocking Test
    testImplementation(libs.core.testing)
    // Truth
    testImplementation(libs.google.truth)
    // For small test - large test annotations
    testImplementation(libs.androidx.runner)
    // Mock objects
    testImplementation(libs.mockk.mockk)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.turbine)

}