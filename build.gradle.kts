plugins {
    id("java")
    id("net.kyori.indra") version "4.0.0"
    id("net.kyori.indra.checkstyle") version "4.0.0"
    id("xyz.jpenilla.resource-factory-bukkit-convention") version "1.3.1"
    id("xyz.jpenilla.run-paper") version "3.0.2"
}

group = "com.jmatt"
version = "2.0.0-SNAPSHOT"

repositories {
    maven("https://repo.papermc.io/repository/maven-public/") {
        name = "papermc"
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.10-R0.1-SNAPSHOT")
}

bukkitPluginYaml {
    main = "com.jmatt.appleskinspigot.AppleSkinSpigot"
    authors.add("jmatt")
    authors.add("Siebrenvde")
    apiVersion = "1.20.5"
    website = "https://github.com/Siebrenvde/AppleSkinSpigot"
}

tasks.runServer {
    minecraftVersion("1.21.8")
}

indra {
    javaVersions {
        target(21)
    }
    checkstyle("12.1.1")
}

tasks.withType<Jar> {
    archiveBaseName = project.name
}
