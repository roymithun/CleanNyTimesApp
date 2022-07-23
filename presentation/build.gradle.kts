import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.HILT_ANDROID)
    id(GradlePluginId.KOTLIN_PARCELIZE)
}

android {
    compileSdk = AndroidConfig.COMPILE_SDK_VERSION
    defaultConfig {
        applicationId = AndroidConfig.ID
        minSdk = AndroidConfig.MIN_SDK_VERSION
        targetSdk = AndroidConfig.TARGET_SDK_VERSION
        buildToolsVersion = AndroidConfig.BUILD_TOOLS_VERSION

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        val properties = gradleLocalProperties(rootDir)
        val apiKey = properties["apiKey"]

        getByName(BuildType.RELEASE) {
            buildConfigField("String", "API_KEY", "\"$apiKey\"")

            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            buildConfigField("String", "API_KEY", "\"$apiKey\"")

            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }

        testOptions {
            unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.stetho)

    implementation(libs.play.core)
    implementation(libs.bundles.ktx)
    implementation(libs.bundles.navigation)
    implementation(libs.bundles.lifecycle)
    implementation(libs.timber)
    implementation(libs.constraintLayout)
    implementation(libs.coordinatorLayout)
    implementation(libs.appcompat)
    implementation(libs.recyclerview)
    implementation(libs.material)

    implementation(libs.hiltandroid)
    kapt(libs.hiltcompiler)

    // module
    implementation(project(Modules.domain)) {
        exclude(group = "com.inhouse.cleannytimesapp", module = "domain")
    }
    implementation(project(Modules.data)) {
        exclude(group = "com.inhouse.cleannytimesapp", module = "data")
    }

    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junit.jupiter.engine)
}

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }