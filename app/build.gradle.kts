plugins {
    alias(libs.plugins.dobedobe.android.application)
}

android {
    namespace = "com.chipichipi.dobedobe"

    defaultConfig {
        applicationId = "com.chipichipi.dobedobe"
    }
}

dependencies {
    implementation(projects.feature.dashboard)

    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
    implementation(projects.core.model)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.lifecycle.viewModelCompose)

    testImplementation(libs.androidx.navigation.testing)

    androidTestImplementation(libs.androidx.lifecycle.runtimeTesting)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.espresso.core)
}
