plugins {
    id(GradlePluginId.JAVA_LIBRARY)
    id(GradlePluginId.KOTLIN_JVM)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.kotlinstdlib)
    implementation(libs.coroutines)

    implementation(libs.daggersupport)

    // Unit Tests
    testImplementation(libs.bundles.test.non.android)
    testRuntimeOnly(libs.junit.jupiter.engine)
}