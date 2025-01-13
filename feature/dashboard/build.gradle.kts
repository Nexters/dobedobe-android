plugins {
    alias(libs.plugins.dobedobe.feature)
}

android {
    namespace = "com.chipichipi.dobedobe.feature.dashboard"
}

dependencies {
    implementation(projects.core.data)
    implementation(projects.core.designsystem)
}
