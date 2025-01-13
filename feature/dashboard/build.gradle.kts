plugins {
    alias(libs.plugins.dobedobe.android.feature)
}

android {
    namespace = "com.chipichipi.dobedobe.feature.dashboard"
}

dependencies {
    implementation(projects.core.data)
}
