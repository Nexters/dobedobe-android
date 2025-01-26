plugins {
    alias(libs.plugins.dobedobe.android.feature)
    alias(libs.plugins.dobedobe.android.compose)
}

android {
    namespace = "com.chipichipi.dobedobe.feature.setting"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.notifications)
}
