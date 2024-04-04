plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

sourceSets {
    val main by getting
    val test by getting

    val intTest by creating {
        compileClasspath += main.output + test.output
        runtimeClasspath += main.output + test.output
    }
}

configurations {
    val testImplementation by getting
    val testRuntimeOnly by getting

    "intTestImplementation" {
        extendsFrom(testImplementation)
    }
    "intTestRuntimeOnly" {
        extendsFrom(testRuntimeOnly)
    }
}

dependencies {
    // Hibernate
    implementation(platform("org.hibernate.orm:hibernate-platform:6.4.4.Final"))
    implementation("org.hibernate.orm:hibernate-core")
    implementation("jakarta.transaction:jakarta.transaction-api")
    testImplementation("com.h2database:h2:2.2.224")

    // Logging
    implementation("org.apache.logging.log4j:log4j-api:2.22.1")
    implementation("org.apache.logging.log4j:log4j-core:2.22.1")

    // Test
    implementation("org.assertj:assertj-core:3.25.3")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.awaitility:awaitility:4.2.0")

    // Testcontainers
    // https://central.sonatype.com/artifact/org.testcontainers/testcontainers-bom
    implementation(platform("org.testcontainers:testcontainers-bom:1.19.6"))
    testImplementation("org.testcontainers:mysql")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    // Define the main class for the application.
    mainClass.set("com.codehunter.hibernate_pure_playground.App")
}

tasks {
    val integrationTest by creating(Test::class) {
        description = "Runs integration tests."
        group = "integration"

        testClassesDirs = sourceSets["intTest"].output.classesDirs
        classpath = sourceSets["intTest"].runtimeClasspath
        useJUnitPlatform()
    }

    test {
        useJUnitPlatform()
    }

    check {
        dependsOn(integrationTest)
    }

}
