plugins {
    kotlin("jvm") version "1.9.0"
}

group = "ru.DmN.pht"
version = "1.3.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("Project-Siberia-1.5.4.jar", "Lazurite-2.7.0.jar"))
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

tasks {
    build {
        dependsOn(register<Jar>("fatJar") {
            dependsOn.addAll(listOf("compileJava", "compileKotlin", "processResources"))
            archiveClassifier.set("standalone")
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
            manifest { attributes(mapOf("Main-Class" to "ru.DmN.pht.base.Console")) }
            val sourcesMain = sourceSets.main.get()
            val contents = configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) } + sourcesMain.output
            from(contents)
        })
    }
}