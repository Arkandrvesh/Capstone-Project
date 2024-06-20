plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-parcelize")
    id ("kotlin-kapt")
}

android {
    namespace = "com.dicoding.skivent"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.skivent"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
        buildConfig = true
    }

    buildTypes {
        debug{
            buildConfigField("String", "API_URL", "\"https://capstone-project-424408.et.r.appspot.com/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_URL", "\"https://capstone-project-424408.et.r.appspot.com/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    implementation ("androidx.camera:camera-camera2:1.1.0-beta03")
    implementation("androidx.camera:camera-view:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation ("com.github.yalantis:ucrop:2.2.8")

    implementation("androidx.datastore:datastore-core-android:1.1.1")
    implementation("androidx.activity:activity:1.8.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation ("androidx.paging:paging-runtime-ktx:3.1.1")
    implementation ("androidx.room:room-paging:2.5.1")

    implementation ("androidx.room:room-runtime:2.5.1")
    kapt ("androidx.room:room-compiler:2.5.1")
    implementation ("androidx.room:room-ktx:2.5.1")

    implementation ("com.github.bumptech.glide:glide:4.15.0")
    implementation ("id.zelory:compressor:3.0.1")

    // api
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.5")

    // core
    implementation ("androidx.datastore:datastore-preferences:1.0.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.activity:activity-ktx:1.2.3")


}