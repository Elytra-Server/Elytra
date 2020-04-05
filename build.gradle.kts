import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

val groupPrefix = "io.elytra"
val pVersion = "1.0.0-SNAPSHOT"
val ktExperimental = setOf(
    "kotlin.Experimental",
    "kotlinx.coroutines.ObsoleteCoroutinesApi",
    "kotlin.contracts.ExperimentalContracts"
)

group = groupPrefix
version = pVersion

allprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.github.johnrengelman.shadow")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    repositories {
        jcenter()
        mavenCentral()
        maven("https://oss.sonatype.org/content/repositories/snapshots")
        maven("https://libraries.minecraft.net/")
    }

    dependencies {
        // Kotlin stuff
        implementation(kotlin("stdlib-jdk8"))
        // Project dependencies
        implementation("com.google.code.gson:gson:2.8.6")
        implementation("io.reactivex.rxjava3:rxkotlin:3.0.0")
        implementation("io.projectreactor.netty:reactor-netty:0.9.6.RELEASE")
        implementation("com.flowpowered:flow-network:1.0.0")
        implementation("com.mojang:authlib:1.5.21")
        // Logging dependencies
        implementation("org.slf4j:slf4j-api:2.0.0-alpha1")
        implementation("ch.qos.logback:logback-classic:1.3.0-alpha5")

        // Dependencies required for unit tests
        testRuntimeOnly(kotlin("stdlib"))
        testImplementation("junit:junit:4.11")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.3.41")
    }
}

subprojects {
    group = "$groupPrefix.$name"
    version = pVersion

    tasks {
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "11"
            kotlinOptions.freeCompilerArgs = listOf("-Xuse-experimental=" + ktExperimental.joinToString(","))
        }

        withType<ShadowJar> {
            // Make all resulting jars go to the `target` folder inside the project root dir
            archiveBaseName.set(project.name)
            destinationDirectory.set(File(rootDir, "target"))
        }
    }
}

gradle.startParameter.apply {
    if (taskNames.isEmpty() || !taskNames.contains("ktlintFormat")) {
        setTaskNames(taskNames.also { it.add(0, "ktlintFormat") })
    }
}

gradle.taskGraph.whenReady {

    // Ignore build & shadow tasks for the API module, since we don't need its jar file for now
    findProject("elytra-api")?.tasks?.also {
        it.findByPath(":elytra-api:build")?.enabled = true
        it.findByPath(":elytra-api:shadowJar")?.enabled = true
    }
}
