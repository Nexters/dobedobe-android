plugins {
    alias(libs.plugins.dobedobe.jvm)
}

dependencies {
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
}
