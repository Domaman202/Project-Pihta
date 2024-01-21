plugins {
    kotlin("jvm") version "1.9.21"
    `maven-publish`
}

group = "ru.DmN.pht"
version = "1.10.0"

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("ru.DmN.siberia:Project-Siberia:1.10.0")
    implementation(kotlin("reflect"))
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-tree:9.5")
    implementation("org.ow2.asm:asm-util:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
    implementation("uk.co.caprica:vlcj:4.8.2")
    implementation("com.itextpdf:itextpdf:5.5.13.3")
    testImplementation(kotlin("test"))
    testImplementation("org.apache.commons:commons-lang3:3.14.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    val fatJar = register<Jar>("fatJar") {
        dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
        archiveClassifier.set("standalone")
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        manifest { attributes(mapOf("Main-Class" to "ru.DmN.pht.std.Console")) }
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