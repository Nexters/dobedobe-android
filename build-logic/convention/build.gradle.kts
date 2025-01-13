import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.chipichipi.dobedobe.buildlogic"

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        create("android-application") {
            id = libs.plugins.dobedobe.android.application.get().pluginId
            implementationClass = "AndroidApplicationPlugin"
        }
        create("android-compose") {
            id = libs.plugins.dobedobe.android.compose.get().pluginId
            implementationClass = "ComposeLibraryPlugin"
        }
        create("android-feature") {
            id = libs.plugins.dobedobe.android.feature.get().pluginId
            implementationClass = "AndroidFeaturePlugin"
        }
        create("android-library") {
            id = libs.plugins.dobedobe.android.library.get().pluginId
            implementationClass = "AndroidLibraryPlugin"
        }
        create("kotlin-serialization") {
            id = libs.plugins.dobedobe.kotlinx.serialization.get().pluginId
            implementationClass =
                "KotlinSerializationPlugin"
        }
        register("jvm-library") {
            id = libs.plugins.dobedobe.jvm.get().pluginId
            implementationClass = "JvmLibraryPlugin"
        }
    }
}
