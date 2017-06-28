package(default_visibility = ["//visibility:public"])

load("@bazel_tools//tools/build_defs/pkg:pkg.bzl", "pkg_tar", "pkg_deb")

generated_release_files = [
    "release.yaml",
]

sh_binary(
    name = "package-info-generator",
    srcs = ["package_info_generator.sh"],
)

genrule(
    name = "generate-package-info",
    outs = generated_release_files,
    cmd = "$(location :package-info-generator) $$(find -L . -name '*status*.txt') >$@",
    stamp = 1,
    tools = [":package-info-generator"],
)

pulsar_spout_artifact = [
    "pulsar-spout.whl",
]

filegroup(
    name = "pypkgs",
    srcs = [
        ":generate-pulsar-spout-package",
    ],
)

genrule(
    name = "generate-pulsar-spout-package",
    srcs = [
        "pulsar_spout/requirements.txt",
        "pulsar_spout/setup.py.template",
        "pulsar_spout/README.txt",
    ] + generated_release_files,
    outs = pulsar_spout_artifact,
    stamp = 1,
    tools = ["//heron/spouts/pulsar/src/python:pulsar-spout"],
    cmd = "\n".join([
        'export OUTPUT_DIR=$$(pwd)/$(@D)',
        'export RELEASE_FILE_DIR=$$(pwd)',
        'export TMP_DIR=$$(mktemp -d -t pulsar-spout.XXXXX)',
        'echo $$TMP_DIR',
        'export SPOUT_DIR=$$TMP_DIR/pulsar_spout',
        'export SPOUT_UNZIP=$$TMP_DIR/unzipped',
        'export SPOUT_VERSION=$$(grep version $$RELEASE_FILE_DIR/$(location :release.yaml) | awk \'{print $$3}\')',
        'export SPOUT_VERSION=$$(echo $$SPOUT_VERSION | sed -e "s/^\'//" -e "s/\'$$//")',
        'export SPOUT_VERSION=$$(echo $$SPOUT_VERSION | grep "[0-9]*\.[0-9]*\.[0-9]*")',
        'export SPOUT_VERSION=$$([[ -z $$SPOUT_VERSION ]] && echo "0.0.22" || echo $$SPOUT_VERSION)',
        'echo $$SPOUT_VERSION',
        'mkdir -p $$TMP_DIR $$SPOUT_DIR',
        'unzip -qd $$SPOUT_UNZIP $(location //heron/spouts/pulsar/src/python:pulsar-spout)',
        'find $$SPOUT_UNZIP -name "*.pyc" -exec rm {} \;',
        'mkdir -p $$SPOUT_DIR/pulsar_spout',
        'mv $$SPOUT_UNZIP/heron/spouts/pulsar/src/python/* $$SPOUT_DIR/pulsar_spout',
        'cp $(SRCS) $$SPOUT_DIR',
        'echo "Pulsar spout toplevel directory: $$SPOUT_DIR"',
        'cd $$SPOUT_DIR',
        'sed "s/VERSION/$$SPOUT_VERSION/" setup.py.template > setup.py',
        'rm setup.py.template',
        'tree $$SPOUT_DIR',
        '/usr/bin/env python2.7 setup.py sdist',
        '/usr/bin/env python2.7 setup.py bdist_wheel',
        'ls -l $$SPOUT_DIR/dist',
        'cp $$SPOUT_DIR/dist/pulsar_*-py2-*.whl $$OUTPUT_DIR',
        'cp $$SPOUT_DIR/dist/pulsar_*.tar.gz $$OUTPUT_DIR',
        'touch $$OUTPUT_DIR/pulsar-spout.whl',
        'rm -rf $$TMP_DIR',
    ]),
)
