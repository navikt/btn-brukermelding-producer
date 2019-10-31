import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val btnBomVersion = "3"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.50"
    application
}

val githubUser: String by project
val githubPassword: String by project

repositories {
    jcenter()
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/ktor")
    maven("http://packages.confluent.io/maven/")
    maven {
        url = uri("https://maven.pkg.github.com/navikt/btn-common")
        credentials {
            username = githubUser
            password = githubPassword
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(platform("no.nav.btn:btn-bom:$btnBomVersion"))
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-gson")
    implementation("io.prometheus:simpleclient_hotspot")
    implementation("io.prometheus:simpleclient_common")
    implementation("ch.qos.logback:logback-classic")
    implementation("net.logstash.logback:logstash-logback-encoder")
    implementation("com.natpryce:konfig")
    implementation("org.apache.kafka:kafka-clients")
    implementation("io.confluent:kafka-json-serializer")
    implementation("no.nav.btn:btn-common")

    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "no.nav.btn.ApplicationKt"
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Wrapper> {
    gradleVersion = "5.6.3"
}
