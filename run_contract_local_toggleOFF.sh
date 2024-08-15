#!/usr/bin/env bash

echo "Environment : Local"
echo "Authorization Toggle: OFF"
sbt -Denv=local -DbearerToken=false test