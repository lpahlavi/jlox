import org.gradle.api.plugins.BasePlugin.BUILD_GROUP

plugins {
    java
    application
}

group = "com.craftinginterpreters"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("org.projectlombok:lombok:1.18.26")

    implementation("org.projectlombok:lombok:1.18.26")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("com.craftinginterpreters.lox.Lox")
}

tasks.jar {
    manifest {
        attributes("Main-Class" to application.mainClass)
    }
}

val destinationDirectory = "src/main/java/com/craftinginterpreters/lox"
val generateAstClassName = "com.craftinginterpreters.tool.GenerateAst"
val errorCodeClassName = "com.craftinginterpreters.shared.ErrorCode"

fun sourceFile (className: String): String {
    return "src/main/java/${className.replace(".", "/")}.java"
}
tasks.register("generateAst") {
    description = "Generates the Abstract Syntax Tree (AST) Java code files"
    group = BUILD_GROUP
    doLast {
        exec {
            commandLine("javac", "-d", "build/classes/java/main", sourceFile(errorCodeClassName), sourceFile(generateAstClassName))
        }
        exec {
            commandLine("java", "-cp", "build/classes/java/main", generateAstClassName, destinationDirectory)
        }
    }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.named("build") {
    dependsOn("generateAst", "installDist")
}

tasks.test {
    useJUnitPlatform()
}