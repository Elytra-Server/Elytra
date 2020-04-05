import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":elytra-api"))
    implementation("org.fusesource.jansi:jansi:1.17")
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
