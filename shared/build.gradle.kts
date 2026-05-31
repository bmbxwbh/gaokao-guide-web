plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    js(IR) {
        browser()
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                api(libs.miuix.ui)
                implementation(libs.miuix.preference)
                implementation(libs.miuix.icons)
                implementation(libs.jetbrains.compose.components.resources)
                implementation(libs.jetbrains.compose.foundation)
                implementation(libs.kotlinx.serialization.core)
            }
        }

        val skikoMain by creating {
            dependsOn(commonMain.get())
        }

        val webMain by creating {
            dependsOn(skikoMain)
        }

        wasmJsMain {
            dependsOn(webMain)
        }

        jsMain {
            dependsOn(webMain)
        }
    }
}

compose.resources {
    publicResClass = true
}
