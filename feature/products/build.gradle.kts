plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.br.products"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(project(":network"))
    implementation(project(":design_system"))
    implementation(project(":infra"))
    implementation(project(":navigation"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material3)

    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)
    implementation(libs.androidx.ui.tooling.preview.android)

    // Coil
    implementation(libs.image.coil.compose)

    // Json Reader
    implementation(libs.thiago.souto.json.reader)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.core)
    implementation(libs.koin.android.compat)
    implementation(libs.koin.androidx.navigation)

    // Pager3
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.adapter.corutines)
    implementation(libs.okhttp.logging.interceptor)

    // Moshi
    implementation(libs.retrofit.moshi)
    implementation(libs.moshi)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.okhttp)
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockwebserver)
    testImplementation(libs.flow.turbine)
    testImplementation(libs.kotlinx.coroutines.core.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}