# instr-tests-sample
The sample app for advanced instrumental testing lecture for Android Academy (https://www.youtube.com/watch?v=6XW6T0QOPpc&t=3633s).

There are simple scripts `dummyrunner.sh` and `runner.sh` in the root of the project to start the tests.

For example, just call
```
sh dummyrunner.sh
```
to start all the tests.

```
sh dummyrunner.sh -tests="class com.eakurnikov.instrsample.tests.posts.PostsTest"
```
to start all the tests in the specified test class.

```
sh dummyrunner.sh -orchestration=orchestrator
```
to launch all the tests with orchestrator (each test will be started in it's own instrumentation instance).

```
sh runner.sh
```
to start all the tests with the handmade simplified version of the orchestrator. It's made to show there is no magic.
