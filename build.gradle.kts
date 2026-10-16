// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false

}
true // Needed to make the Suppress annotation work for the plugins block