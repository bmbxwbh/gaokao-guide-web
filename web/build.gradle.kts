import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.kotlinMultiplatform)
}

kotlin {
    js(IR) {
        outputModuleName = "gaokaoGuide"
        browser {
            commonWebpackConfig {
                outputFileName = "gaokaoGuide.js"
            }
        }
        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "gaokaoGuide"
        browser {
            commonWebpackConfig {
                outputFileName = "gaokaoGuide.js"
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(project(":shared"))
            }
        }

        val webMain by creating {
            dependsOn(commonMain.get())
        }

        wasmJsMain {
            dependsOn(webMain)
        }

        jsMain {
            dependsOn(webMain)
        }
    }
}
