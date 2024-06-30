plugins {
    alias(libs.plugins.kotlin.jvm)
    id(libs.plugins.java.gradle.plugin.get().pluginId)
}

kotlin {
    jvmToolchain(17)
}

gradlePlugin {
    plugins.create("publish") {
        id = "com.attafitamim.klib.publish"
        implementationClass = "com.attafitamim.klib.publish.PublishConventions"
    }
}

dependencies {
    compileOnly(libs.kotlin.plugin)
    compileOnly(libs.maven.publish.plugin)
}