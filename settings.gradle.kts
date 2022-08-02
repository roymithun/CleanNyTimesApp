rootProject.buildFileName = "build.gradle.kts"

enableFeaturePreview("VERSION_CATALOGS")

// Set single lock file (gradle.lockfile)
// This preview feature should be enabled by default in Gradle 7
// More: https://docs.gradle.org/current/userguide/dependency_locking.html#single_lock_file_per_project
enableFeaturePreview("ONE_LOCKFILE_PER_PROJECT")

include(
    ":presentation",
    ":domain",
    ":data",
    ":library_test_utils"
)

// Gradle plugins are added via plugin management, not the classpath
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }

    // Using the plugins  DSL allows generating type-safe accessors for Kotlin DSL
    plugins {
        val agpVersion: String by settings
        id("com.android.application") version agpVersion
        id("com.android.library") version "7.2.1"
        id("com.android.dynamic-feature") version agpVersion

        val kotlinVersion: String by settings
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.android") version "1.7.10"

        val navigationVersion: String by settings
        id("androidx.navigation.safeargs.kotlin") version navigationVersion

        val androidJUnit5Version: String by settings
        id("de.mannodermaus.android-junit5") version androidJUnit5Version

        val agpHiltVersion: String by settings
        id("dagger.hilt.android.plugin") version agpHiltVersion

        val ktlintGradleVersion: String by settings
        id("org.jlleitschuh.gradle.ktlint") version ktlintGradleVersion

        val detektVersion: String by settings
        id("io.gitlab.arturbosch.detekt") version detektVersion
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "com.android.application",
                "com.android.library",
                "com.android.dynamic-feature" -> {
                    val agpCoordinates: String by settings
                    useModule(agpCoordinates)
                }
                "androidx.navigation.safeargs.kotlin" -> {
                    val navigationCoordinates: String by settings
                    useModule(navigationCoordinates)
                }
                "de.mannodermaus.android-junit5" -> {
                    val androidJnit5Coordinates: String by settings
                    useModule(androidJnit5Coordinates) // navigationVersion
                }
                "dagger.hilt.android.plugin" -> {
                    val agpHiltCoordinates: String by settings
                    useModule(agpHiltCoordinates)
                }
            }
        }
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {

            val kotlinVersion: String by settings
            version("kotlin-version", kotlinVersion)
            // Required by Android dynamic feature modules and SafeArgs
            alias("kotlin-reflect").to("org.jetbrains.kotlin", "kotlin-reflect")
                .versionRef("kotlin-version")

            version("coroutines", "1.+")
            alias("coroutines").to("org.jetbrains.kotlinx", "kotlinx-coroutines-android")
                .versionRef("coroutines")

            bundle("kotlin", listOf("kotlin-reflect", "coroutines"))

            alias("kotlinstdlib").to("org.jetbrains.kotlin", "kotlin-stdlib-jdk8")
                .versionRef("kotlin-version")

            version("retrofit", "2.+")
            alias("retrofit-core").to("com.squareup.retrofit2", "retrofit").versionRef("retrofit")
            alias("converter-moshi").to("com.squareup.retrofit2", "converter-moshi")
                .versionRef("retrofit")
            alias("converter-gson").to("com.squareup.retrofit2", "converter-gson")
                .versionRef("retrofit")

            version("gson-version", "2.+")
            alias("gson").to("com.google.code.gson", "gson").versionRef("gson-version")

            // KotlinJsonAdapterFactory unresolved reference -- https://stackoverflow.com/a/70036501/2694480
            version("moshi-kotlin", "1.+")
            alias("moshi-kotlin").to("com.squareup.moshi", "moshi-kotlin")
                .versionRef("moshi-kotlin")
            bundle(
                "retrofit",
                listOf(
                    "retrofit-core",
                    "converter-moshi",
                    "converter-gson",
                    "moshi-kotlin",
                )
            )

            // Retrofit will use okhttp 4 (it has binary capability with okhttp 3)
            // See: https://square.github.io/okhttp/upgrading_to_okhttp_4/
            version("okhttp", "4.+")
            alias("okhttp-okhttp").to("com.squareup.okhttp3", "okhttp").versionRef("okhttp")
            alias("okhttp-interceptor").to("com.squareup.okhttp3", "logging-interceptor")
                .versionRef("okhttp")
            // bundle is basically an alias for several dependencies
            bundle(
                "okhttp",
                listOf("okhttp-okhttp", "okhttp-interceptor")
            )

            version("okhttpprofiler-version", "1.0.8")
            alias("okhttpprofiler").to("com.localebro", "okhttpprofiler")
                .versionRef("okhttpprofiler-version")

            version("stetho", "1.5.0")
            alias("stetho-core").to("com.facebook.stetho", "stetho").versionRef("stetho")
            alias("stetho-okhttp3").to("com.facebook.stetho", "stetho-okhttp3").versionRef("stetho")
            bundle("stetho", listOf("stetho-core", "stetho-okhttp3"))

            alias("timber").to("com.jakewharton.timber:timber:4.+")
            alias("constraintLayout").to("androidx.constraintlayout:constraintlayout:2.+")
            alias("coordinatorLayout").to("androidx.coordinatorlayout:coordinatorlayout:1.+")
            alias("appcompat").to("androidx.appcompat:appcompat:1.+")
            alias("recyclerview").to("androidx.recyclerview:recyclerview:1.+")
            alias("material").to("com.google.android.material:material:1.4.0")
            alias("play-core").to("com.google.android.play:core:1.+")

            alias("corektx").to("androidx.core:core-ktx:1.+")
            alias("fragment-ktx").to("androidx.fragment:fragment-ktx:1.+")
            bundle("ktx", listOf("corektx", "fragment-ktx"))

            version("lifecycle", "2.+")
            alias("viewmodel-ktx").to("androidx.lifecycle", "lifecycle-viewmodel-ktx")
                .versionRef("lifecycle")
            alias("livedata-ktx").to("androidx.lifecycle", "lifecycle-livedata-ktx")
                .versionRef("lifecycle")
            alias("lifecycle-common").to("androidx.lifecycle", "lifecycle-common-java8")
                .versionRef("lifecycle")
            bundle("lifecycle", listOf("viewmodel-ktx", "livedata-ktx", "lifecycle-common"))

            val navigationVersion: String by settings
            version("navigation", navigationVersion)
            alias("navigation-fragment").to("androidx.navigation", "navigation-fragment-ktx")
                .versionRef("navigation")
            alias("navigation-dynamic")
                .to("androidx.navigation", "navigation-dynamic-features-fragment")
                .versionRef("navigation")
            alias("navigation-ui-ktx").to("androidx.navigation", "navigation-ui-ktx")
                .versionRef("navigation")
            bundle(
                "navigation",
                listOf("navigation-fragment", "navigation-dynamic", "navigation-ui-ktx")
            )

            version("room", "2.+")
            alias("room-ktx").to("androidx.room", "room-ktx").versionRef("room")
            alias("room-runtime").to("androidx.room", "room-runtime").versionRef("room")
            bundle("room", listOf("room-ktx", "room-runtime"))

            alias("room.compiler").to("androidx.room", "room-compiler").versionRef("room")

            // Test dependencies
            alias("test.coroutines").to("org.jetbrains.kotlinx", "kotlinx-coroutines-test")
                .versionRef("coroutines")

            version("glide-version", "4.+")
            alias("glide").to("com.github.bumptech.glide", "glide").versionRef("glide-version")
            alias("glidecompiler").to("com.github.bumptech.glide", "compiler")
                .versionRef("glide-version")

            version("swiperefreshlayout-version", "1.1.0")
            alias("swiperefreshlayout").to("androidx.swiperefreshlayout", "swiperefreshlayout")
                .versionRef("swiperefreshlayout-version")

            // Dagger 2 (version works only ** androidx.appcompat » appcompat	1.1.0)
            version("dagger-version", "2.29.1")
            alias("daggerandroid").to("com.google.dagger", "dagger-android")
                .versionRef("dagger-version")
            alias("daggersupport").to("com.google.dagger", "dagger-android-support")
                .versionRef("dagger-version")

            val agpHiltVersion: String by settings
            version("hilt-version", agpHiltVersion)
            alias("hiltandroid").to("com.google.dagger", "hilt-android").versionRef("hilt-version")
            alias("hiltcompiler").to("com.google.dagger", "hilt-android-compiler")
                .versionRef("hilt-version")

            version("kluent", "1.+")
            alias("kluent-android").to("org.amshove.kluent", "kluent-android").versionRef("kluent")

            alias("test-runner").to("androidx.test:runner:1.+")
            alias("espresso").to("androidx.test.espresso:espresso-core:3.+")
            alias("mockk").to("io.mockk:mockk:1.+")
            alias("arch").to("androidx.arch.core:core-testing:2.+")

            version("mockwebserver", "4.+")
            alias("mockwebserver").to("com.squareup.okhttp3", "mockwebserver")
                .versionRef("mockwebserver")

            version("junit", "5.+")
            alias("junit-jupiter-api").to("org.junit.jupiter", "junit-jupiter-api")
                .versionRef("junit")

            version("roboelectric", "4.+")
            alias("robolectric").to("org.robolectric", "robolectric").versionRef(
                "roboelectric"
            )

            // test flow
            version("turbine", "0.8.0")
            alias("turbine").to("app.cash.turbine", "turbine").versionRef("turbine")

            bundle(
                "test",

                listOf(
                    "test.coroutines",
                    "kluent-android",
                    "test-runner",
                    "espresso",
                    "mockk",
                    "arch",
                    "junit-jupiter-api",
                    "robolectric",
                    "turbine"
                )
            )

            version("truth", "1.+")
            alias("truth").to("com.google.truth", "truth").versionRef("truth")
            version("junit4", "4.+")
            alias("junit4").to("junit", "junit").versionRef("junit4")

            bundle(
                "test.non.android",
                listOf(
                    "test.coroutines",
                    "truth",
                    "mockk",
                    "junit4",
                    "turbine"
                )
            )

            version("hilt-android-testing", agpHiltVersion)
            alias("hilt.android.testing").to("com.google.dagger", "hilt-android-testing")
                .versionRef("hilt-android-testing")

            version("espresso-contrib", "3.+")
            alias("espresso.contrib").to("androidx.test.espresso", "espresso-contrib")
                .versionRef("espresso-contrib")

            version("navigation-testing", "2.3.5")
            alias("navigation.testing").to("androidx.navigation", "navigation-testing")
                .versionRef("navigation-testing")

            version("androidx.test.ext", "1.+")
            alias("test.ext").to("androidx.test.ext", "junit").versionRef("androidx.test.ext")

            alias("junit-jupiter-engine").to("org.junit.jupiter", "junit-jupiter-engine")
                .versionRef("junit")

            version("mavericks", "2.7.0")
            alias("mvrx").to("com.airbnb.android", "mavericks").versionRef("mavericks")
            alias("mvrx.hilt").to("com.airbnb.android", "mavericks-hilt").versionRef("mavericks")

            bundle(
                "mavericks",
                listOf(
                    "mvrx",
                    "mvrx.hilt",
                )
            )

            alias("mvrx.testing").to("com.airbnb.android", "mavericks-testing").versionRef("mavericks")
            alias("mvrx.mocking").to("com.airbnb.android", "mavericks-mocking").versionRef("mavericks")
            bundle(
                "mavericks.test",
                listOf(
                    "mvrx.testing",
                    "mvrx.mocking"
                )
            )
        }
    }
}
