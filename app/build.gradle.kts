import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
}

android {
    namespace = "com.example.moviesapp2coders"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moviesapp2coders"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Acess local.properties and if the key exsist set it to a API_KEY variable in the
        // generated BuildConfige file
        val localProperties = File(rootDir, "local.properties")
        val apiKey = if (localProperties.exists()) {
            localProperties.inputStream().use { input ->
                Properties().apply { load(input) }.getProperty("api.key")
            }
        } else {
            null
        }
        buildConfigField("String", "API_KEY", "\"${apiKey ?: ""}\"")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    testOptions.unitTests.apply {
        all {
            it.useJUnitPlatform()
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    //AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3.android)
    implementation(compose.material)
    //Compose
    implementation(libs.lifecycle.compose)
    implementation(libs.lifecycle.viewmodel.ktx)

    //Destinations
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)

    //Dagger Hilt
    implementation(libs.dagger.hilt)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.ksp.compiler)

    //Pagination
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.room.paging)

    //Squareup
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.moshi)
    implementation(libs.squareup.okhttp3)

    //Coil
    implementation(libs.coil)

    //Room
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    //Testing
    testImplementation(libs.junit)
    testImplementation(libs.bundles.jupiter.test)
    testImplementation(libs.kotlinx.test.coroutine)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.cash.turbine)
    testImplementation(libs.mockk)
}