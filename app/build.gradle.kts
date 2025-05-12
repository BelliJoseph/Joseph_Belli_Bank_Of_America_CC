import org.jetbrains.kotlin.fir.declarations.builder.buildScript

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.navigation)
}

android {
    namespace = "com.example.josephbellibankofamericacc"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.josephbellibankofamericacc"
        minSdk = 27
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
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.testing)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter.api)
    testRuntimeOnly(libs.junit.jupiter.engine)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(kotlin("test"))
    implementation(libs.junit.ktx)
    androidTestImplementation(libs.espresso.contrib)
    androidTestImplementation(libs.espresso.intents)
    implementation(libs.espresso.idling.resource)
    androidTestImplementation(libs.espresso.idling.resource)

    implementation(libs.androidx.lifecycle.viewmodel)

    //hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.hilt.compiler)

    //retrofit
    implementation(libs.retrofit)

    //gson
    implementation(libs.gson)
    implementation(libs.gson.converter)

    //okhttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    //coroutines
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.core.testing)

    //mockK
    testImplementation(libs.mockk)

    //Room
    implementation(libs.room.android)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    //Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

}