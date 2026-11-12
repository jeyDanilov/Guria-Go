pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "YourApp"
include(":app")

rootProject.name = "Guria TO GO"
include(":app")
include(":data")
include(":domain")
include(":ui")
include(":feature:menu")
include(":feature:cart")
include(":feature:orders")
