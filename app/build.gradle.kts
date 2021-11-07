import proguard.gradle.ProGuardTask

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java application project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.2/userguide/building_java_projects.html
 */

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("com.guardsquare:proguard-gradle:7.1.0")
    }
}

plugins {
    java

    // Apply the application plugin to add support for building a CLI application in Java.
    application

    checkstyle
    jacoco

    id("net.henryhc.fork.org.openjfx.javafxplugin")
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:22.0.0")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.7.2")
}

javafx {
    version = "17.0.0.1"
    modules("javafx.controls")
}

application {
    // Define the main class for the application.
    mainClass.set("hk.ust.cse.comp3021.pa2.Main")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

checkstyle {
    toolVersion = "9.0"
}

tasks {
    withType<JavaCompile> {
        options.compilerArgs = listOf("--enable-preview")
        options.encoding = "UTF-8"
    }
    withType<Javadoc> {
        (options as? CoreJavadocOptions)?.apply {
            addStringOption("source", java.toolchain.languageVersion.get().toString())
            addBooleanOption("-enable-preview", true)
        }
    }
    withType<JavaExec> {
        jvmArgs("--enable-preview")
    }
    withType<Jar> {
        manifest {
            attributes.apply {
                this["Main-Class"] = application.mainClass.get()
            }
        }
    }
    withType<JacocoReport> {
        dependsOn(test)

        reports {
            xml.required.set(false)
            csv.required.set(false)
            html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
        }
    }
    withType<Test> {
        group = "verification"

        // Use JUnit Platform for unit tests.
        useJUnitPlatform()

        systemProperties(
            "junit.jupiter.execution.timeout.testable.method.default" to "2000 ms"
        )

        jvmArgs("--enable-preview")
    }

    create<Test>("testSanity") {
        useJUnitPlatform {
            includeTags("sanity")
        }
    }

    create<Test>("testProvided") {
        useJUnitPlatform {
            includeTags("provided")
        }
    }

    create<Test>("testActual") {
        useJUnitPlatform {
            includeTags("actual")
        }
    }

    create<ProGuardTask>("proguard") {
        injars(jar.flatMap { it.archiveFile })
        outjars(jar.flatMap { it.destinationDirectory.file("${project.name}-proguard.jar") })

        libraryjars("${System.getProperty("java.home")}/jmods")
        libraryjars(sourceSets.main.map {
            (it.compileClasspath + it.runtimeClasspath).distinct() - jar.flatMap { jar -> jar.archiveFile }.get().asFile
        })

        keep("public class hk.ust.cse.comp3021.pa2.Main { public static void main(java.lang.String[]); }")

        printmapping(jar.flatMap { it.destinationDirectory.file("${project.name}-proguard-mapping.txt") })
        overloadaggressively()
        flattenpackagehierarchy()
        allowaccessmodification()
        mergeinterfacesaggressively()
        dontskipnonpubliclibraryclassmembers()
        useuniqueclassmembernames()
        optimizationpasses(5)
        obfuscationdictionary(file("emoji.txt"))
        classobfuscationdictionary(file("emoji.txt"))
        packageobfuscationdictionary(file("emoji.txt"))
    }
}
