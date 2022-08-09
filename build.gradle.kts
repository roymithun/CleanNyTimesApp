import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id(GradlePluginId.KOTLIN_ANDROID) apply false
    id(GradlePluginId.ANDROID_APPLICATION) apply false
    id(GradlePluginId.ANDROID_DYNAMIC_FEATURE) apply false
    id(GradlePluginId.ANDROID_LIBRARY) apply false
    id(GradlePluginId.SAFE_ARGS) apply false
    id(GradlePluginId.KTLINT_GRADLE)
    id(GradlePluginId.DETEKT)
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

    apply(plugin = GradlePluginId.DETEKT)

    detekt {
        // The directories where detekt looks for source files.
        // Defaults to `files("src/main/java", "src/test/java", "src/main/kotlin", "src/test/kotlin")`.
        source = files("src/main/java", "src/main/kotlin")

        // Builds the AST in parallel. Rules are always executed in parallel.
        // Can lead to speedups in larger projects. `false` by default.
        parallel = true

        // Define the detekt configuration(s) you want to use.
        // Defaults to the default detekt configuration.
        config = files("$rootDir/config/detekt/detekt.yml")

        // Android: Don't create tasks for the specified build types (e.g. "release")
        ignoredBuildTypes = listOf("release")
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

// Target version of the generated JVM bytecode. It is used for type resolution.
tasks.withType<Detekt> {
    this.jvmTarget = "1.8"
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}
