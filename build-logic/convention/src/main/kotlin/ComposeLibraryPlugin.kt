import com.android.build.gradle.LibraryExtension
import com.chipichiphi.dobedobe.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.getByType

class ComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(plugins) {
            apply("org.jetbrains.kotlin.plugin.compose")
            apply<AndroidLibraryPlugin>()
        }
        configureAndroidCompose(extensions.getByType<LibraryExtension>())
    }
}
