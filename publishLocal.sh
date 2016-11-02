#!/usr/bin/env bash

cd $(dirname $0)

./gradlew clean && ./gradlew uploadArchives