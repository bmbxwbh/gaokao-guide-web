plugins {
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
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

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
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
                implementation(projects.shared)
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
