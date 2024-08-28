plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.dagger.hilt.android)
    id("kotlin-kapt")
    id("kotlin-android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.android.template"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.android.template"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // KOTLIN COROUTINES
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    //HILT
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // VIEWMODEL & LIVEDATA
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)

    // DIMENSIONS
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)

    // PICASSO
    implementation(libs.squareup.picasso)
    implementation(libs.jakewharton.picasso)

    // GLIDE
    implementation(libs.bumptech.glide)
    annotationProcessor(libs.bumptech.glide.compiler)

    // RETROFIT
    implementation(libs.converter.gson)
    implementation(libs.retrofit2)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // CIRCULAR IMAGEVIEW
    implementation(libs.circleimageview)

    // GOOGLE SIGN IN
    implementation(libs.google.signin)

    // FACEBOOK
    implementation(libs.facebook.login)
}