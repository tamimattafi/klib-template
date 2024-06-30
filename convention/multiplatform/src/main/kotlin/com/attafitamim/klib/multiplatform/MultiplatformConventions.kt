package com.attafitamim.klib.multiplatform

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.dsl.kotlinExtension

class MultiplatformConventions : Plugin<Project> {
  override fun apply(project: Project) {
    project.plugins.apply {
      apply("org.jetbrains.kotlin.multiplatform")
      apply("com.android.library")
    }

    val extension = project.kotlinExtension as KotlinMultiplatformExtension
    extension.apply {
      applyDefaultHierarchyTemplate()
      jvmToolchain(17)

      jvm()

      androidTarget {
        compilations.all {
          it.kotlinOptions {
            jvmTarget = "17"
          }
        }
      }

      js {
        browser {
          testTask {
            it.useKarma {
              useChromeHeadless()
            }
          }
        }
        compilations.configureEach {
          it.kotlinOptions {
            moduleKind = "umd"
          }
        }
      }

      iosX64()
      iosArm64()
      iosSimulatorArm64()
    }

    val jvmSourceSet = extension.sourceSets.getByName("jvmMain")
    val androidSourceSet = extension.sourceSets.getByName("androidMain")
    androidSourceSet.dependsOn(jvmSourceSet)

    val androidExtension = project.extensions.getByName("android") as LibraryExtension
    androidExtension.apply {
      namespace = "com.attafitamim.klib.${project.name}"
      compileSdk = 34

      defaultConfig {
        minSdk = 21
      }

      compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
      }
    }
  }
}
