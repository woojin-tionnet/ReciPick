import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.android.builder.model.PROPERTY_SIGNING_KEY_ALIAS
import com.android.builder.model.PROPERTY_SIGNING_KEY_PASSWORD

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("com.google.gms.google-services")
}

android {
    signingConfigs {
        create("release") {
            storeFile = file(project.properties["STORE_FILE"] as String)
            storePassword = project.properties["KEYSTORE_PASSWORD"] as String
            keyAlias = project.properties["KEY_ALIAS"] as String
            keyPassword = project.properties["KEY_PASSWORD"] as String
        }
    }
    namespace = "com.woojin.recipick"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.woojin.recipick"
        minSdk = 28
        targetSdk = 35
        versionCode = 5
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        val apiKey = gradleLocalProperties(rootDir, providers).getProperty("spoonacular_key")
        buildConfigField("String", "SPOONACULAR_KEY", "$apiKey")

        ksp {
            arg("room.schemaLocation", "$projectDir/schemas")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        debug {
            isMinifyEnabled = false
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
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.google.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    //viewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    //Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) //hiltViewModel 델리게이트 사용을 위한 추가

    //네비게이션
    implementation(libs.androidx.navigation.compose)

    //Room
    implementation(libs.androidx.room.runtime) //기본
    ksp(libs.androidx.room.compiler) //또는 kapt
    implementation(libs.androidx.room.ktx) //Flow지원을 위해서

    //Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    //GSON
    implementation(libs.gson)

    //okhttp
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //retrofit
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.adapter.rxjava2)

    //FireBase
    // Import the BoM for the Firebase platform
    implementation(platform(libs.firebase.bom))
    // Google Login, 버전은 BOM 으로 관리
    implementation(libs.firebase.auth)
    // Google Sign-In SDK 명시적으로 추가
    implementation(libs.play.services.auth)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    implementation(libs.play.services.ads)
}