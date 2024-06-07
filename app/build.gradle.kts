plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    //id("kotlin-parcelize")

   /* id("com.google.devtools.ksp")
    //For Hilt
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")*/

}

android {
    namespace = "com.example.easyfood"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.easyfood"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val room = "2.6.0"

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    //implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-android:1.2.0-alpha10")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")


    //Room
    implementation("androidx.room:room-runtime:$room")
    ksp("androidx.room:room-compiler:$room")
    implementation("androidx.room:room-ktx:$room")


    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    implementation("androidx.navigation:navigation-runtime-ktx:2.7.7")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Retrofit
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("io.coil-kt:coil-compose:2.4.0")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //implementation("org.jetbrains.kotlin:kotlin-stdlib:1.8.0")
    //implementation("com.google.accompanist:accompanist-swiperefresh:0.32.0")
    //implementation("com.google.accompanist:accompanist-pager:0.32.0")


}
kapt {
    correctErrorTypes = true
}
