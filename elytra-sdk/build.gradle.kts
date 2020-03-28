import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
	implementation(project(":elytra-api"))
}
val compileKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-XXLanguage:+InlineClasses")
}
