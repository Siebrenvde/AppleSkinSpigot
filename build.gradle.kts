plugins {
    id("java")
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.2.0"
}

group = "com.jmatt"
version = "1.2.0"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
}

bukkitPluginYaml {
    main = "com.jmatt.appleskinspigot.AppleSkinSpigotPlugin"
    authors.add("jmatt")
    authors.add("Siebrenvde")
    apiVersion = "1.20"
    website = "https://github.com/Siebrenvde/AppleSkinSpigot"
}

tasks.test {
    useJUnit()
    jvmArgs(
        "--illegal-access=permit",
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.util=ALL-UNNAMED",
        "--add-opens=java.base/java.nio=ALL-UNNAMED"
    )
}

val targetJavaVersion = 21
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"

    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
    }
}
