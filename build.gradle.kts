plugins {
    id(GradlePluginId.KOTLIN_ANDROID) apply false
    id(GradlePluginId.ANDROID_APPLICATION) apply false
    id(GradlePluginId.ANDROID_DYNAMIC_FEATURE) apply false
    id(GradlePluginId.ANDROID_LIBRARY) apply false
    id(GradlePluginId.SAFE_ARGS) apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
    // Gradle dependency locking - lock all configurations of the app
    // More: https://docs.gradle.org/current/userguide/dependency_locking.html
    dependencyLocking {
        lockAllConfigurations()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
