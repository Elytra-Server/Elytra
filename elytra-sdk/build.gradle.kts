import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
	implementation(project(":elytra-api"))
	implementation("it.unimi.dsi:fastutil:8.3.1")
}
val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}
