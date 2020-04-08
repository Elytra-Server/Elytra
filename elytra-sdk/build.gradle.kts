import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation(project(":elytra-api"))
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.3.71")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("org.fusesource.jansi:jansi:1.17")
    testImplementation("io.mockk:mockk:1.9")
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
