plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android") version "2.48"
    id("androidx.navigation.safeargs.kotlin") version "2.8.3" // Исправил на актуальную стабильную версию
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" // Обновил для совместимости с Kotlin 2.0
}
android {
    namespace = "com.example.pokemon"
    compileSdk = 35
    defaultConfig {
        applicationId = "com.example.pokemon"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
// Удалил composeOptions, так как для Kotlin 2.0+ Compose Compiler встроен в плагин
}
dependencies {
    implementation ("com.airbnb.android:lottie:6.0.1")
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.moshi.converter)
    implementation(libs.moshi.kotlin.v1152)
    implementation (libs.androidx.material3.v112)
// Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
// Paging для Compose
    implementation(libs.androidx.paging.compose)
// Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
// Moshi codegen
    ksp(libs.moshi.kotlin.codegen.v1152)
// Debug для Compose
    debugImplementation(libs.androidx.ui.tooling)
}