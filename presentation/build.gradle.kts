import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_KAPT)
    id(GradlePluginId.HILT_ANDROID)
    id(GradlePluginId.KOTLIN_PARCELIZE)
    id(GradlePluginId.SAFE_ARGS)
}

apply {
    from("jacoco.gradle")
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
    packagingOptions {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        )
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
    implementation(libs.swiperefreshlayout)

    implementation(libs.hiltandroid)
    kapt(libs.hiltcompiler)

    // image processing
    implementation(libs.glide)
    kapt(libs.glidecompiler)

    implementation(libs.bundles.mavericks)

    // module
    implementation(project(ModuleDependency.DOMAIN)) {
        exclude(group = "com.inhouse.cleannytimesapp", module = "domain")
    }
    implementation(project(ModuleDependency.DATA)) {
        exclude(group = "com.inhouse.cleannytimesapp", module = "data")
    }

    // Unit Tests
    testImplementation(project(ModuleDependency.LIBRARY_TEST_UTILS))
    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junit.jupiter.engine)
    testImplementation(libs.bundles.mavericks.test)

    // Instrumented Unit Tests
    androidTestImplementation(libs.arch)
    androidTestImplementation(libs.truth)
    androidTestImplementation(libs.test.coroutines)
    androidTestImplementation(libs.test.ext)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hiltcompiler)
    androidTestAnnotationProcessor(libs.hiltcompiler)
    androidTestImplementation(libs.navigation.testing)
    androidTestImplementation(libs.espresso.contrib) {
        exclude("org.checkerframework")
    }
}
fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }
