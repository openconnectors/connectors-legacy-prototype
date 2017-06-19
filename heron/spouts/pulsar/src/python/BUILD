package(default_visibility = ["//visibility:public"])

load(
    "@io_bazel_rules_pex//pex:pex_rules.bzl",
    "pex_binary",
    "pex_library",
    "pex_test",
    "pex_pytest",
)

# build binary for single thread python heron instance
pex_binary(
    name = "pulsar-spout",
    srcs = ["pulsar_spout.py"],
    reqs = [
        'pyheron>=0.0.1',
        'pulsar-client==1.18.dev1',
    ],
)
