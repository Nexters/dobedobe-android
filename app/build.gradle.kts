plugins {
    alias(libs.plugins.dobedobe.android.application)
    alias(libs.plugins.dobedobe.kotlinx.serialization)
}

android {
    namespace = "com.chipichipi.dobedobe"

    defaultConfig {
        applicationId = "com.chipichipi.dobedobe"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    packaging {
        resources.excludes.apply {
            add("/META-INF/{AL2.0,LGPL2.1}")
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
