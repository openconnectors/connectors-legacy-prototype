package(default_visibility = ["//visibility:public"])

load(
    "@io_bazel_rules_pex//pex:pex_rules.bzl",
    "pex_binary",
    "pex_library",
    "pex_test",
    "pex_pytest",
)

pex_library(
    name = "bolts-py",
    srcs = glob(['bolts/*.py']),
)

pex_library(
    name = "spouts-py",
    srcs = glob(['spouts/*.py']),
)

pex_binary(
    name = "word_pulsar_count",
    srcs = ["word_count_pulsar_topology.py"],
    deps = [
      '//heron/examples/src/python:bolts-py',
      '//heron/examples/src/python:spouts-py',
    ],
    reqs = ['pulsar_spout==0.0.21.dev5', 'pyheron>=0.0.5'],
)
