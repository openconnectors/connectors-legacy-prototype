package(default_visibility = ["//visibility:public"])

load(
    "@io_bazel_rules_pex//pex:pex_rules.bzl",
    "pex_binary",
    "pex_library",
    "pex_test",
    "pex_pytest",
)

# build binary for single thread python heron instance
# We don't specify the dependencies here; they go while packaging whl
pex_binary(
    name = "pulsar-spout",
    srcs = ["pulsar_spout.py", "__init__.py"],
)
