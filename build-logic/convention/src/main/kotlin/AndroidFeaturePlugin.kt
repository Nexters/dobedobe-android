import com.chipichiphi.dobedobe.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeaturePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply<AndroidLibraryPlugin>()
            apply<KotlinSerializationPlugin>()

            dependencies {
                "implementation"(project(":core:designsystem"))

                "implementation"(libs.findLibrary("androidx.lifecycle.runtimeCompose").get())
                "implementation"(libs.findLibrary("androidx.lifecycle.viewModelCompose").get())
                "implementation"(libs.findLibrary("androidx.navigation.compose").get())
                "implementation"(libs.findLibrary("kotlinx.serialization.json").get())
                "testImplementation"(libs.findLibrary("androidx.navigation.testing").get())
                "androidTestImplementation"(
                    libs.findLibrary("androidx.lifecycle.runtimeTesting").get(),
                )
            }
        }
    }
}
