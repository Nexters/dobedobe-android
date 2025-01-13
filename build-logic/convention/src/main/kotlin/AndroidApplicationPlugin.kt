import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.BaseExtension
import com.chipichiphi.dobedobe.configureAndroidCompose
import com.chipichiphi.dobedobe.configureKotlinAndroid
import com.moya.funch.plugins.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.plugin.compose")
            apply(plugin = "kotlin-android")

            extensions.getByType<BaseExtension>().apply {
                buildFeatures.apply {
                    buildConfig = true
                }
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureAndroidCompose(this)
                defaultConfig.targetSdk =
                    libs.findVersion("targetSdk").get().requiredVersion.toInt()
                defaultConfig.testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                defaultConfig.testInstrumentationRunnerArguments["runnerBuilder"] =
                    "de.mannodermaus.junit5.AndroidJUnit5Builder"
            }

            dependencies {
                "implementation"(libs.findLibrary("kotlinx.coroutines.android").get())
                "implementation"(libs.findLibrary("kotlinx.datetime").get())
                // test
                "testImplementation"(libs.findBundle("unit.test").get())
                "testRuntimeOnly"(libs.findLibrary("junit5.vintage").get())
                // androidTest
                "androidTestImplementation"(libs.findBundle("androidx.android.test").get())
                "androidTestImplementation"(libs.findBundle("unit.test").get())
                "androidTestImplementation"(libs.findLibrary("junit5.android.test.core").get())
                "androidTestRuntimeOnly"(libs.findLibrary("junit5.android.test.runner").get())
            }
        }
    }
}
