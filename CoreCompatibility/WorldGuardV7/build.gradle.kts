/*
 * This file was generated by the Gradle 'init' task.
 */

plugins {
    id("me.deecaad.java-conventions")
}

dependencies {
    api("org.spigotmc:spigot-api:1.18-R0.1-SNAPSHOT")
    implementation(project(":MechanicsCore"))
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.7")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.6")
}

//java {
//    toolchain {
//        languageVersion.set(JavaLanguageVersion.of(16))
//    }
//}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name() // We want UTF-8 for everything
        options.release.set(16)
    }
}

description = "WorldGuardV7"
