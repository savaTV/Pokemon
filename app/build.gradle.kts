plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.dagger.hilt.android") version "2.48"
    id("androidx.navigation.safeargs.kotlin") version "2.8.3" // Исправил на актуальную стабильную версию
    id("com.google.devtools.ksp") version "2.0.0-1.0.21" // Обновил для совместимости с Kotlin 2.0
}
android {
    namespace = "com.example.pokeapp"
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
}
dependencies {
    implementation ("androidx.cardview:cardview:1.0.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0") // Для Java
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation("androidx.compose.foundation:foundation:1.9.0")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation(platform(libs.androidx.compose.bom))
    implementation("io.coil-kt:coil-compose:2.4.0")
    implementation("androidx.compose.material:material-icons-extended:1.1.0")
    implementation ("androidx.room:room-runtime:2.7.2")
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
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.swiperefreshlayout)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.appcompat)
    implementation(libs.google.material)
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