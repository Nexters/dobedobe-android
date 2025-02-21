import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.dobedobe.android.application)
    alias(libs.plugins.dobedobe.kotlinx.serialization)
}

val properties =
    Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

android {
    namespace = libs.versions.applicationId.get()

    signingConfigs {
        create("release") {
            keyAlias = properties.getProperty("KEY_ALIAS")
            keyPassword = properties.getProperty("KEY_PASSWORD")
            storeFile = file("${project.rootDir.absolutePath}/keystore/dobedobe_key.keystore")
            storePassword = properties.getProperty("STORE_PASSWORD")
        }
    }

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        versionName = libs.versions.appVersion.get()
        versionCode = libs.versions.versionCode.get().toInt()
    }

    buildTypes {
        debug {
            applicationIdSuffix = ".dev"
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    packaging {
        resources.excludes.apply {
            add("/META-INF/{AL2.0,LGPL2.1}")
            add("META-INF/**")
        }
    }
}

dependencies {
    implementation(projects.feature.dashboard)
    implementation(projects.feature.goal)
    implementation(projects.feature.setting)

    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    implementation(projects.core.notifications)
    implementation(projects.core.ui)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)
    implementation(libs.androidx.core.splashscreen)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.koin.androidx.startup)
    testImplementation(libs.koin.test.junit5)
    testImplementation(libs.koin.android.test)
    testImplementation(libs.androidx.navigation.testing)

    androidTestImplementation(libs.androidx.lifecycle.runtimeTesting)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
