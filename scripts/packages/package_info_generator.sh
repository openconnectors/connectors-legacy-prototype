#!/bin/bash -eu

# Copyright 2015 The Bazel Authors. All rights reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Generate a RELEASE file for the package from the information provided
# by the build status command.

# Store the build status information we care about
build_version=
git_hash=
release_status=
build_time=
build_timestamp=
build_host=
build_user=

for i in "${@}"; do
  while read line; do
    key=$(echo "$line" | cut -d " " -f 1)
    value="$(echo "$line" | cut -d " " -f 2- | tr '\f' '\n')"
    case $key in
      BUILD_VERSION)
        build_version="$value"
        ;;
      BUILD_SCM_REVISION)
        git_hash="$value"
        ;;
      BUILD_RELEASE_STATUS)
        release_status="$value"
        ;;
      BUILD_TIME)
        build_time="$value"
        ;;
      BUILD_TIMESTAMP)
        build_timestamp="$value"
        ;;
      BUILD_HOST)
        build_host="$value"
        ;;
      BUILD_USER)
        build_user="$value"
        ;;
   esac
  done <<<"$(cat $i)"
done

echo "connectors.build.version : '${build_version}'"
echo "connectors.build.time : ${build_time}"
echo "connectors.build.timestamp : ${build_timestamp}"
echo "connectors.build.host : ${build_host}"
echo "connectors.build.user : ${build_user}"
echo "connectors.build.git.revision : ${git_hash}"
echo "connectors.build.git.status : ${release_status}"
