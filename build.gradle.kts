plugins {
    kotlin("jvm") version "1.9.21"
    `maven-publish`
}

group = "ru.DmN.pht"
version = "1.23.2"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    api("ru.DmN.siberia:Project-Siberia:1.18.11")
    implementation(kotlin("reflect"))
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-tree:9.5")
    implementation("org.ow2.asm:asm-util:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
    testImplementation(kotlin("test"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    jvmToolchain(8)
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to "ru.DmN.pht.ConsoleImpl")) }
        val sourcesMain = sourceSets.main.get()
        val contents = configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) } + sourcesMain.output
        from(contents)
    }

    build {
        dependsOn(fatJar)
    }

    java {
        withSourcesJar()
    }

    test {
        useJUnitPlatform()
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = "Project-Pihta"
            version = project.version as String
            from(components["java"])
        }
    }
}