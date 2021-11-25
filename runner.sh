#!/bin/sh

set -xe

action=all # all|norebuild
buildCommand="assembleDebug assembleDebugAndroidTest"
jUnitRunner=com.eakurnikov.instrsample.debug.test/com.eakurnikov.instrsample.runner.CustomInstrRunner
outputs=app/build/outputs
results=./allure-results
reports=./test-report
testPlan=./test-plan.txt
testsSrc=./app/src/androidTest/kotlin/com/eakurnikov/instrsample/tests
testNamePattern="*Test.kt"
testPkgPattern="^package com.eakurnikov.instrsample.tests(.?\w+)*$"

getTestPlan() {
  find $testsSrc -type f -name $testNamePattern | \
    while read test; do \
      pkg=$(grep -E "$testPkgPattern" $test | cut -f 2 -d ' '); \
      className=$pkg.$(basename $test); \
      echo ${className%???}; \
    done
}

writeTestPlan() {
  getTestPlan > $testPlan
}

clear() {
  adb shell rm -rf /sdcard/allure-results
  adb shell rm -rf /sdcard/logcat
  adb shell rm -rf /sdcard/report
  adb shell rm -rf /sdcard/screenshots
  adb shell rm -rf /sdcard/view_hierarchy
  rm -rf $results
  rm -rf $reports
  rm -rf $testPlan
}

build() {
  ./gradlew $buildCommand
}

installApks() {
  adb install $outputs/apk/debug/*.apk
  adb install $outputs/apk/androidTest/debug/*.apk
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
  for testClass in $(cat ./test-plan.txt); do
    adb shell am instrument -w -m -e class $testClass $jUnitRunner #$flags
    adb shell pm clear com.eakurnikov.instrsample.debug
  done
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
    -jUnitRunner=*)
      jUnitRunner=${arg#*=}
      shift
      ;;
    -outputs=*)
      outputs=${arg#*=}
      shift
      ;;
    -results=*)
      results=${arg#*=}
      shift
      ;;
    -reports=*)
      reports=${arg#*=}
      shift
      ;;
    -testPlan=*)
      testPlan=${arg#*=}
      shift
      ;;
    -testsSrc=*)
      testsSrc=${arg#*=}
      shift
      ;;
    -testNamePattern=*)
      testNamePattern=${arg#*=}
      shift
      ;;
    -testPkgPattern=*)
      testPkgPattern=${arg#*=}
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
    writeTestPlan
    build
    installApks
    test
    pullResults
    report
    ;;
  norebuild)
    clear
    writeTestPlan
    test
    pullResults
    report
    ;;
  *)
    echo "Unknown action"
    exit 1
    ;;
esac
