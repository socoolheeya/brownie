import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
}

allprojects {

    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "com.cocoa"
    version = "0.0.1-SNAPSHOT"

    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_21
    }

    dependencies {
//        compileOnly("org.projectlombok:lombok")
//        annotationProcessor("org.projectlombok:lombok")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "21"
        }
    }
}

project("brownie-domain") {
    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtimeOnly("com.h2database:h2")
    }

    val bootJar: BootJar by tasks
    val jar: Jar by tasks
    bootJar.enabled = false
    jar.enabled = true
}

project("brownie-api") {
    dependencies{
        implementation(project(":brownie-domain"))
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")

        developmentOnly("org.springframework.boot:spring-boot-devtools")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    val bootJar: BootJar by tasks
    bootJar.mainClass.set("com.cocoa.brownie.api.BrownieApiApplication")

}




