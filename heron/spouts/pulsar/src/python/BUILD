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
        'pyheron==0.0.0',
        'pulsar-client==1.18.dev1',
    ],
)

pulsar_spout_wheel_file = [
    "pulsar-spout.whl",
]

genrule(
    name = "generate-pulsar-spout-package",
    srcs = [
        "requirements.txt",
        "setup.py.template",
        "README.txt",
        "//scripts/packages:release-yaml",
    ],
    outs = pulsar_spout_wheel_file,
    stamp = 1,
    tools = [":pulsar-spout"],
    cmd = "\n".join([
        'export OUTPUT_DIR=$$(pwd)/$(@D)',
        'export RELEASE_FILE_DIR=$$(pwd)',
        'export TMP_DIR=$$(mktemp -d -t pulsar-spout.XXXXX)',
        'echo $$TMP_DIR',
        'export SPOUT_DIR=$$TMP_DIR/pulsar-spout',
        'export SPOUT_UNZIP=$$TMP_DIR/unzipped',
        'export SPOUT_VERSION=$$(grep version $$RELEASE_FILE_DIR/$(location //scripts/packages:release-yaml) | awk \'{print $$3}\')',
        'export SPOUT_VERSION=$$(echo $$SPOUT_VERSION | sed -e "s/^\'//" -e "s/\'$$//")',
        'export SPOUT_VERSION=$$(echo $$SPOUT_VERSION | grep "[0-9]*\.[0-9]*\.[0-9]*")',
        'export SPOUT_VERSION=$$([[ -z $$SPOUT_VERSION ]] && echo "0.0.0" || echo $$SPOUT_VERSION)',
        'echo $$SPOUT_VERSION',
        'mkdir -p $$TMP_DIR $$SPOUT_DIR',
        'unzip -qd $$SPOUT_UNZIP $(location :pulsar-spout)',
        'find $$SPOUT_UNZIP -name "*.pyc" -exec rm {} \;',
        'cp $(SRCS) $$SPOUT_DIR',
        'echo "Pulsar spout toplevel directory: $$SPOUT_DIR"',
        'cd $$SPOUT_DIR',
        'sed "s/VERSION/$$SPOUT_VERSION/" setup.py.template > setup.py',
        'rm setup.py.template',
        '/usr/bin/env python2.7 setup.py sdist',
        '/usr/bin/env python2.7 setup.py bdist_wheel',
        'ls -l $$SPOUT_DIR/dist',
        'cp $$SPOUT_DIR/dist/pulsar_*-py2-*.whl $$OUTPUT_DIR',
        'cp $$SPOUT_DIR/dist/pulsar_*.tar.gz $$OUTPUT_DIR',
        'touch $$OUTPUT_DIR/pulsar-spout.whl',
        'rm -rf $$TMP_DIR',
    ]),
)
