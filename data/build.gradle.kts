plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
}

android {
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION

        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.corektx)

    // module
    implementation(project(ModuleDependency.DOMAIN))

    implementation(libs.appcompat)

    implementation(libs.bundles.retrofit)
    implementation(libs.bundles.okhttp)
    implementation(libs.okhttpprofiler)
    implementation(libs.gson)

    implementation(libs.bundles.room)
    kapt(libs.room.compiler)

    implementation(libs.hiltandroid)
    kapt(libs.hiltcompiler)

    implementation(libs.timber)

    // Unit Tests
    testImplementation(project(ModuleDependency.LIBRARY_TEST_UTILS))
    testImplementation(libs.bundles.test)
    testImplementation(libs.mockwebserver)

    // Instrumented Unit Tests
    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hiltcompiler)
}