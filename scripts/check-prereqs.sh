#!/usr/bin/env bash
# Checks local machine for tools needed to build/run this project:
# Java 21, Maven 3.9+, Docker (for Consul + ELK stack).
# Run: bash scripts/check-prereqs.sh

set -uo pipefail
failures=()

check_cmd() {
    local name="$1"
    local cmd="$2"
    shift 2
    if ! command -v "$cmd" >/dev/null 2>&1; then
        echo "[MISSING] ${name} not found on PATH (${cmd})"
        failures+=("$name")
        return
    fi
    echo "[OK] ${name} found:"
    "$cmd" "$@" 2>&1 | head -n 3
}

echo "=== Checking prerequisites for spring_box ==="

check_cmd "Java" java -version
check_cmd "Maven" mvn -version
check_cmd "Docker" docker --version
check_cmd "Docker Compose" docker compose version

echo ""
if [ "${#failures[@]}" -gt 0 ]; then
    echo "Missing prerequisites: ${failures[*]}"
    echo "Install the missing tools and re-run this script."
    exit 1
else
    echo "All prerequisites found."
    exit 0
fi
