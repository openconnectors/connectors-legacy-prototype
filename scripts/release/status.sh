#!/bin/bash

# This script will be run when the bazel build process starts to
# generate key-value information that represents the status of the
# workspace. The output should be like
#
# KEY1 VALUE1
# KEY2 VALUE2
#
# If the script exits with non-zero code, it's considered as a failure
# and the output will be discarded.

set -eu

function die {
    echo >&2 "$@"
    exit 1
}

function disable_e_and_execute {
  set +e
  $@
  ret_status=$?
  set -e
  echo $ret_status
}

# get the release tag version or the branch name
if [ -z ${BUILD_VERSION+x} ];
then
  cmd="git rev-parse --abbrev-ref HEAD"
  build_version=$($cmd) || die "Failed to run command to check head: $cmd"

  if [ "${build_version}" = "HEAD" ];
  then
    cmd="git describe --tags --always"
    build_version=$($cmd) || die "Failed to run command to get git release: $cmd"
  fi
else
  build_version=${BUILD_VERSION}
fi
echo "BUILD_VERSION ${build_version}"

# The code below presents an implementation that works for git repository
if [ -z ${GIT_REV+x} ];
then
  cmd="git rev-parse HEAD"
  git_rev=$($cmd) || die "Failed to get git revision: $cmd"
else
  git_rev=${GIT_REV}
fi

echo "BUILD_SCM_REVISION ${git_rev}"

if [ -z ${BUILD_HOST+x} ];
then
  build_host=$(hostname)
else
  build_host=${BUILD_HOST}
fi
echo "BUILD_HOST ${build_host}"

if [ -z ${BUILD_TIME+x} ];
then
  build_time=$(LC_ALL=en_EN.utf8 date)
else
  build_time=${BUILD_TIME}
fi
echo "BUILD_TIME ${build_time}"

if [ -z ${BUILD_TIMESTAMP+x} ];
then
  build_timestamp=$(date +%s000)
else
  build_timestamp=${BUILD_TIMESTAMP}
fi
echo "BUILD_TIMESTAMP ${build_timestamp}"

if [ -z ${BUILD_USER+x} ];
then
  build_user=${USER}
else
  build_user=${BUILD_USER}
fi
echo "BUILD_USER ${build_user}"

# Check whether there are any uncommited changes
if [ -z ${TREE_STATUS+x} ];
then
  status=$(disable_e_and_execute "git diff-index --quiet HEAD --")
  if [[ $status == 0 ]];
  then
    tree_status="Clean"
  else
    tree_status="Modified"
  fi
else
  tree_status=${TREE_STATUS}
fi
echo "BUILD_RELEASE_STATUS ${tree_status}"
