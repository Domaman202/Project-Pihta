plugins {
    kotlin("jvm") version "1.9.0"
}

group = "ru.DmN.pht"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("DmNUnsafeUtils-11.0.jar"))
    implementation(kotlin("reflect"))
    implementation("org.ow2.asm:asm:9.5")
    implementation("org.ow2.asm:asm-tree:9.5")
    implementation("org.ow2.asm:asm-util:9.5")
    implementation("org.ow2.asm:asm-commons:9.5")
    testImplementation(kotlin("test"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}