import org.gradle.api.plugins.JavaApplication

plugins {
    id("java")
    id("application")
}

group = ""
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.16.0")
}

tasks.test {
    useJUnitPlatform()
}

configure<JavaApplication> {
    mainClass.set("main.Main")
}
