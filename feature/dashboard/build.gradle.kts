plugins {
    alias(libs.plugins.dobedobe.android.feature)
    alias(libs.plugins.dobedobe.android.compose)
}

android {
    namespace = "com.chipichipi.dobedobe.feature.dashboard"
}

dependencies {
    implementation(projects.core.data)
}
