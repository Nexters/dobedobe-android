plugins {
    alias(libs.plugins.dobedobe.android.library)
}

android {
    namespace = "com.chipichipi.dobedobe.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.datastore)
}
