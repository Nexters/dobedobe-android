plugins {
    alias(libs.plugins.dobedobe.android.library)
    alias(libs.plugins.dobedobe.android.compose)
}

android {
    namespace = "com.chipichipi.dobedobe.core.ui"
}

dependencies {
    api(projects.core.designsystem)
    api(projects.core.model)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
}
