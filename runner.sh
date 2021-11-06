#!/bin/sh

set -xe

action=all # all|buildAndTest|test|clear
buildCommand="assembleDebug assembleDebugAndroidTest"
flags="-e debug false" #-e size small -e annotation com.kaspersky.kaspresso.annotations.E2e
jUnitRunner=com.eakurnikov.instrsample.debug.test/io.qameta.allure.android.runners.AllureAndroidJUnitRunner #androidx.test.runner.AndroidJUnitRunner #com.eakurnikov.instrsample.runners.CustomAndroidJUnitRunner # com.eakurnikov.instrsample.debug.test|com.eakurnikov.instrsample.test/...
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
  rm -rf $testPlan
  getTestPlan > $testPlan
}

build() {
  ./gradlew $buildCommand
}

installApks() {
  adb install $outputs/apk/debug/*.apk
  adb install $outputs/apk/androidTest/debug/*.apk
}

test() {
  for testClass in $(cat ./test-plan.txt); do
    adb shell am instrument -w -m -e class $testClass $jUnitRunner #$flags
    adb shell pm clear com.eakurnikov.instrsample.debug
  done
}

clearDeviceResults() {
  adb shell rm -rf /sdcard/allure-results
  adb shell rm -rf /sdcard/logcat
  adb shell rm -rf /sdcard/report
  adb shell rm -rf /sdcard/screenshots
  adb shell rm -rf /sdcard/view_hierarchy
}

pullResults() {
  rm -rf $results
  adb pull /sdcard/allure-results .
}

report() {
  rm -rf $reports
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
    writeTestPlan
    clearDeviceResults
    build
    installApks
    test
    pullResults
    report
    ;;
  buildAndTest)
    build
    installApks
    test
    ;;
  test)
    test
    ;;
  clear)
    clearDeviceResults
    ;;
  *)
    echo "Unknown action"
    exit 1
    ;;
esac
