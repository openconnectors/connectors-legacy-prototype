package(default_visibility = ["//visibility:public"])

load(
    "@io_bazel_rules_pex//pex:pex_rules.bzl",
    "pex_binary",
    "pex_library",
    "pex_test",
    "pex_pytest",
)

pex_binary(
    name = "word_pulsar_count",
    srcs = ["word_count_pulsar_topology.py"],
    reqs = ['pulsar_spout>=0.0.8', 'pyheron>=0.0.3'],
)
