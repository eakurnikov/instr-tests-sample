#!/bin/sh

set -xe

action=all # all|buildAndTest|test|clear
buildCommand="assembleDebug assembleDebugAndroidTest"
flags="-e debug false -e size small -e annotation com.kaspersky.kaspresso.annotations.E2e"
tests="package com.eakurnikov.instrsample.tests" # |"class com.eakurnikov.instrsample.tests.GoodKaspressoTest"
orchestration=none #orchestrator|none
orchestratorApk=./artifacts/orchestrator-1.4.0.apk
testServicesApk=./artifacts/test-services-1.4.0.apk
jUnitRunner=com.eakurnikov.instrsample.debug.test/io.qameta.allure.android.runners.AllureAndroidJUnitRunner #androidx.test.runner.AndroidJUnitRunner #com.eakurnikov.instrsample.runners.CustomAndroidJUnitRunner # com.eakurnikov.instrsample.debug.test|com.eakurnikov.instrsample.test/...
outputs=app/build/outputs
results=./allure-results
reports=./test-report

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

clear() {
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
  buildAndTest)
    build
    installApks
    test
    ;;
  test)
    test
    ;;
  clear)
    clear
    ;;
  *)
    echo "Unknown action"
    exit 1
    ;;
esac