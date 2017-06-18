git_repository(
    name = "io_bazel_rules_pex",
    remote = "https://github.com/benley/bazel_rules_pex.git",
    tag = "0.3.0",
)

load("@io_bazel_rules_pex//pex:pex_rules.bzl", "pex_repositories")
pex_repositories()

maven_jar(
  name = "antlr_antlr",
  artifact = "antlr:antlr:2.7.7",
)

maven_jar(
  name = "commons_cli_commons_cli",
  artifact = "commons-cli:commons-cli:1.3.1",
)

maven_jar(
  name = "commons_lang_commons_lang",
  artifact = "commons-lang:commons-lang:2.6",
)

maven_jar(
  name = "com_google_guava_guava",
  artifact = "com.google.guava:guava:18.0",
)

maven_jar(
  name = "com_puppycrawl_tools_checkstyle",
  artifact = "com.puppycrawl.tools:checkstyle:6.17",
)

maven_jar(
  name = "com_google_protobuf_protobuf_java",
  artifact = "com.google.protobuf:protobuf-java:2.5.0",
)

maven_jar(
  name = "commons_logging_commons_logging",
  artifact = "commons-logging:commons-logging:1.1.1",
)

maven_jar(
  name = "commons_collections_commons_collections",
  artifact = "commons-collections:commons-collections:3.2.1",
)

maven_jar(
  name = "commons_beanutils_commons_beanutils",
  artifact = "commons-beanutils:commons-beanutils:1.9.2",
)
