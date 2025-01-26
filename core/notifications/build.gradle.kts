plugins {
    alias(libs.plugins.dobedobe.android.library)
}

android {
    namespace = "com.chipichipi.dobedobe.core.notifications"
}

dependencies {
    api(projects.core.model)
    implementation(projects.core.common)

    compileOnly(platform(libs.androidx.compose.bom))
    implementation(libs.koin.android)
}
