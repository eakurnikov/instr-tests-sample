#!/bin/sh

set -xe

action=all # all|norebuild
buildCommand="assembleDebug assembleDebugAndroidTest"
flags="-e debug false" #-e size small -e annotation com.kaspersky.kaspresso.annotations.E2e
tests="package com.eakurnikov.instrsample.tests" # |"class com.eakurnikov.instrsample.tests.posts.PostsTest"
orchestration=none #orchestrator|none
orchestratorApk=./artifacts/orchestrator-1.4.0.apk
testServicesApk=./artifacts/test-services-1.4.0.apk
jUnitRunner=com.eakurnikov.instrsample.debug.test/com.eakurnikov.instrsample.runner.CustomInstrRunner
outputs=app/build/outputs
results=./allure-results
reports=./test-report

clear() {
    adb shell rm -rf /sdcard/allure-results
    adb shell rm -rf /sdcard/logcat
    adb shell rm -rf /sdcard/report
    adb shell rm -rf /sdcard/screenshots
    adb shell rm -rf /sdcard/view_hierarchy
    rm -rf $results
    rm -rf $reports
}

build() {
  ./gradlew $buildCommand
}

installApks() {
  adb install $outputs/apk/debug/*.apk
  adb install $outputs/apk/androidTest/debug/*.apk
  if [ $orchestration = orchestrator ]; then
    adb install $orchestratorApk
    adb install $testServicesApk
  fi
}

test() {
  launchAdbServer & runTests
  adbServerPid=$(ps -ef | awk '$NF~"adbserver" {print $2}')
  kill $adbServerPid
}

launchAdbServer() {
  java -jar ./artifacts/adbserver-desktop.jar
}

runTests() {
  case $orchestration in
    none)
      adb shell am instrument -w -m "$flags" -e "$tests" $jUnitRunner
      ;;
    orchestrator)
      adb shell 'CLASSPATH=$(pm path androidx.test.services) app_process / \
        androidx.test.services.shellexecutor.ShellMain \
        am instrument -w -m \
        '"$flags"' \
        -e '"$tests"' \
        -e clearPackageData true \
        -e targetInstrumentation '$jUnitRunner' \
        androidx.test.orchestrator/.AndroidTestOrchestrator'
      ;;
    *)
      echo "Unknown test orchestration type"
      exit 1
      ;;
  esac
}

pullResults() {
  adb pull /sdcard/allure-results .
}

report() {
  allure generate -o $reports $results
  allure open $reports
}

for arg in "$@"; do
  case $arg in
    -action=*)
      action=${arg#*=}
      shift
      ;;
    -buildCommand=*)
      buildCommand=${arg#*=}
      shift
      ;;
    -flags=*)
      flags=${arg#*=}
      shift
      ;;
    -tests=*)
      tests=${arg#*=}
      shift
      ;;
    -orchestration=*)
      orchestration=${arg#*=}
      shift
      ;;
    -orchestratorApk=*)
      orchestratorApk=${arg#*=}
      shift
      ;;
    -testServicesApk=*)
      testServicesApk=${arg#*=}
      shift
      ;;
    -jUnitRunner=*)
      jUnitRunner=${arg#*=}
      shift
      ;;
    -outputs=*)
      outputs=${arg#*=}
      shift
      ;;
    *)
      shift
      ;;
  esac
done

case $action in
  all)
    clear
    build
    installApks
    test
    pullResults
    report
    ;;
  norebuild)
    clear
    test
    pullResults
    report
    ;;
  *)
    echo "Unknown action"
    exit 1
    ;;
esac