import com.chipichiphi.dobedobe.configureKotlinJvm
import com.chipichiphi.dobedobe.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class JvmLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        apply(plugin = "org.jetbrains.kotlin.jvm")

        configureKotlinJvm()

        dependencies {
            "implementation"(libs.findLibrary("kotlinx.coroutines.core").get())
            "implementation"(libs.findLibrary("kotlinx.datetime").get())
            "testImplementation"(libs.findBundle("unit.test").get())
        }
    }
}