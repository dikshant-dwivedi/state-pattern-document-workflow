#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
mkdir -p build
javac -d build $(rg --files src -g '*.java')
java -cp build example.document.DocumentTest
