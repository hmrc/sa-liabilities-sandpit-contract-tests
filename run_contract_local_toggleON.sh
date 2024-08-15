#!/usr/bin/env bash

echo "Environment : Local"
echo "Authorization Toggle: ON"
sbt -Denv=local -DbearerToken=true test