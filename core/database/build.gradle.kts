plugins {
    alias(libs.plugins.dobedobe.android.library)
}

android {
    namespace = "com.chipichipi.dobedobe.core.database"
}

dependencies {
    api(projects.core.model)
}
