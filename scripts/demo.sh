#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p build
find src/main -name '*.java' -print0 | xargs -0 javac -d build
java -cp build example.document.Demo
