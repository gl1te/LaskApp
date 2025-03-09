plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    id ("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.newsapp.lask"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.newsapp.lask"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose.android)
    implementation(libs.androidx.media3.common.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("io.coil-kt:coil-compose:2.5.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation ("com.google.dagger:hilt-android:2.51.1")
    kapt ("com.google.dagger:hilt-compiler:2.51.1")
    implementation ("androidx.hilt:hilt-navigation-compose:1.2.0")

    implementation("com.airbnb.android:lottie-compose:6.0.0")

    implementation("androidx.datastore:datastore-preferences:1.1.2")

    implementation ("androidx.core:core-splashscreen:1.0.1")

    implementation ("androidx.compose.material3:material3-window-size-class:1.2.1")

    implementation ("androidx.compose.foundation:foundation:1.4.3")

    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.31.4-beta")

    val paging_version = "3.1.1"
    implementation ("androidx.paging:paging-runtime:$paging_version")
    implementation ("androidx.paging:paging-compose:3.2.0-rc01")

    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")

    implementation ("androidx.room:room-runtime:2.6.1")
    kapt ("androidx.room:room-compiler:2.6.1")
    implementation ("androidx.room:room-ktx:2.6.1")
}