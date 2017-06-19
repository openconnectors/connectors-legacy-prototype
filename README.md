# Connectors
The central place where all heron/pulsar/bookkeeper spouts/syncs/connectors are maintained
# How to Build
Currently this requires bazel 0.3.1
bazel build heron/...
# Generate pypi packages
After building run
bazel build scripts/packages:pypkgs
