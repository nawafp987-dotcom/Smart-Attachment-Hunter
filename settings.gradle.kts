pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://maven.pkg.github.com/supabase/postgrest-kt")
        maven(url = "https://maven.pkg.github.com/supabase/gotrue-kt")
    }
}

rootProject.name = "FileRecoveryPro"
include(":app")
