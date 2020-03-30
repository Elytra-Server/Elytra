import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
	implementation(project(":elytra-api"))
	implementation("it.unimi.dsi:fastutil:8.3.1")
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions.freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
	}

	jar {
		manifest {
			attributes("Main-Class" to "io.elytra.sdk.ElytraServer")
		}
	}
}
