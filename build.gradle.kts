plugins {
    id(GradlePluginId.KOTLIN_ANDROID) apply false
    id(GradlePluginId.ANDROID_APPLICATION) apply false
    id(GradlePluginId.ANDROID_DYNAMIC_FEATURE) apply false
    id(GradlePluginId.ANDROID_LIBRARY) apply false
    id(GradlePluginId.SAFE_ARGS) apply false
    id(GradlePluginId.KTLINT_GRADLE)
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    // We want to apply ktlint at all project level because it also checks Gradle config files (*.kts)
    apply(plugin = GradlePluginId.KTLINT_GRADLE)

    ktlint {
        enableExperimentalRules.set(true)
        disabledRules.set(setOf("experimental:package-name", "no-wildcard-imports"))
    }

    // Gradle dependency locking - lock all configurations of the app
    // More: https://docs.gradle.org/current/userguide/dependency_locking.html
    dependencyLocking {
        lockAllConfigurations()
    }
}

subprojects {
    apply(plugin = GradlePluginId.KTLINT_GRADLE) // Version should be inherited from parent

    repositories {
        // Required to download KtLint
        mavenCentral()
    }
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
