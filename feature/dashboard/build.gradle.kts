plugins {
    alias(libs.plugins.dobedobe.android.feature)
    alias(libs.plugins.dobedobe.android.compose)
}

android {
    namespace = "com.chipichipi.dobedobe.feature.dashboard"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.feature.goal)

    implementation(libs.accompanist.permission)
    implementation(libs.compose.cloudy)
    implementation(libs.lottie)
    implementation(libs.image.cropper)
}
