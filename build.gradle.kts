import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.61"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

val groupPrefix = "io.inb"
val pVersion = "1.0.0-SNAPSHOT"

group = groupPrefix
version = pVersion

allprojects {
    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("com.github.johnrengelman.shadow")
    }

    repositories {
        jcenter()
        mavenCentral()

        maven("https://oss.sonatype.org/content/repositories/snapshots")
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        implementation(kotlin("reflect"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
        implementation("io.reactivex.rxjava2:rxjava:2.2.0")
        implementation("io.projectreactor.netty:reactor-netty:0.9.6.RELEASE")
		implementation("com.flowpowered:flow-network:1.0.0")
    }
}

subprojects {
    group = groupPrefix
    version = pVersion

    dependencies {
        implementation("org.apache.commons:commons-lang3:3.9")

        testRuntimeOnly(kotlin("stdlib"))
        testImplementation("junit:junit:4.11")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.3.41")
    }

    tasks {
		withType<KotlinCompile> {
			kotlinOptions.jvmTarget = "11"
			kotlinOptions.freeCompilerArgs =
					listOf("-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi,kotlin.Experimental,kotlin.contracts.ExperimentalContracts")
		}

		withType<ShadowJar> {
			archiveBaseName.set(project.name)
			destinationDirectory.set(File(rootDir, "target"))
		}
	}
}

gradle.taskGraph.whenReady {
    findProject("inb-api")?.tasks?.also {
        it.findByPath(":inb-api:build")?.enabled = false
        it.findByPath(":inb-api:shadowJar")?.enabled = false
    }
}


