package(default_visibility = ["//visibility:public"])

load("/tools/rules/pex_rules", "pex_library", "pex_binary")

# build binary for single thread python heron instance
pex_binary(
    name = "heron-pulsar-spout",
    srcs = ["pulsar_spout.py"],
    deps = [
        "//heron/pyheron/src/python:pyheron-py",
        "//heron/common/src/python:common-py",
    ],
    reqs = ['colorlog==2.6.1', 'pyyaml==3.10', 'pulsar-client==1.18'],
)
