import com.android.build.gradle.BaseExtension
import com.moya.funch.plugins.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

class ComposePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(plugins) {
            apply("org.jetbrains.kotlin.plugin.compose")
            apply<AndroidLibraryPlugin>()
        }
        extensions.getByType<BaseExtension>().apply {
            buildFeatures.apply {
                compose = true
            }
            testOptions {
                animationsDisabled = true
                unitTests {
                    // For Robolectric
                    isIncludeAndroidResources = true
                }
            }
        }

        extensions.configure<ComposeCompilerGradlePluginExtension> {
            includeSourceInformation = true
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom))
            "implementation"(libs.findBundle("compose").get())
            "implementation"(libs.findLibrary("coil.kt.compose").get())
            // test
            "testImplementation"(platform(bom))
            "testImplementation"(libs.findLibrary("robolectric").get())
            "testImplementation"(libs.findLibrary("androidx.compose.ui.test.junit4").get())
            // androidTest
            "androidTestImplementation"(platform(bom))
            "androidTestImplementation"(libs.findLibrary("androidx.compose.ui.test.junit4").get())
            "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling").get())
            "debugImplementation"(libs.findLibrary("androidx.compose.ui.test.manifest").get())
        }
    }
}
