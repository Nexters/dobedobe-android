import com.chipichiphi.dobedobe.configureKotlinJvm
import com.moya.funch.plugins.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        with(plugins) {
            apply("org.jetbrains.kotlin.jvm")
        }

        configureKotlinJvm()

        dependencies {
            "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())
            "implementation"(libs.findLibrary("kotlinx.datetime").get())
            "testImplementation"(libs.findBundle("unit.test").get())
        }
    }
}