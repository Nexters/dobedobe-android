plugins {
    alias(libs.plugins.dobedobe.android.library)
}

android {
    namespace = "com.chipichipi.dobedobe.core.datastore"
}

dependencies {
    api(projects.core.datastoreProto)
    api(projects.core.model)

    implementation(projects.core.common)
}
