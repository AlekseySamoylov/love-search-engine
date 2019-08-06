pluginManagement {
  repositories {
    gradlePluginPortal()
  }
}
rootProject.name = "love-search-engine"
include("telegram-bot", "base-spring", "crawler-by")
object DependencyVersions {
  const val JETTY_VERSION = "9.4.12.v20180830"
}