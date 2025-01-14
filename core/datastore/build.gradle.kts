plugins {
    alias(libs.plugins.dobedobe.android.library)
}

android {
    namespace = "com.chipichipi.dobedobe.core.datastore"

    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
}

dependencies {
    api(libs.androidx.dataStore)
    api(projects.core.datastoreProto)
    api(projects.core.model)

    implementation(projects.core.common)
}
