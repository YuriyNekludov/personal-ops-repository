fun Settings.gradleProperty(name: String) = providers.gradleProperty(name)

beforeSettings {
    val gitHubUrl = gradleProperty("url")
    val username = gradleProperty("username")
    val password = gradleProperty("password")

    fun RepositoryHandler.gitHubRepository() =
        maven(gitHubUrl.getOrElse(System.getenv("MAVEN_URL"))) {
            credentials {
                this.username = username.getOrElse(System.getenv("MAVEN_USERNAME"))
                this.password = password.getOrElse(System.getenv("MAVEN_PASSWORD"))
            }
            mavenContent { releasesOnly() }
        }
    dependencyResolutionManagement {
        repositories {
            mavenCentral()
            mavenLocal()
            gradlePluginPortal()
            gitHubRepository()
        }
    }
    pluginManagement {
        repositories {
            gradlePluginPortal()
            mavenLocal()
            gitHubRepository()
        }
    }
}