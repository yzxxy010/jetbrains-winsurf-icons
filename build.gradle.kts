import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.20"
    id("org.jetbrains.intellij") version "1.16.0"
}

group = "com.modernicons"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.10.1")
}

// Configure Gradle IntelliJ Plugin
intellij {
    version.set("2023.3.2") // Stable version compatible with Rider 2025
    type.set("IC") // IntelliJ IDEA Community (faster download, works with all JetBrains IDEs)
    
    plugins.set(listOf())
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    
    // Include LICENSE file in the distribution
    processResources {
        from("LICENSE") {
            into("META-INF")
        }
    }
    
    // Disable instrumentCode task due to path issues
    instrumentCode {
        enabled = false
    }
    
    // Disable buildSearchableOptions as it's not needed for icon themes
    buildSearchableOptions {
        enabled = false
    }

    patchPluginXml {
        sinceBuild.set("233")
        untilBuild.set("252.*")
        
        changeNotes.set("""
            <h2>1.0.0</h2>
            <ul>
                <li>Initial release</li>
                <li>Modern custom file and folder icons with beautiful design</li>
                <li>Support for various file extensions and special folders</li>
            </ul>
        """.trimIndent())
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
