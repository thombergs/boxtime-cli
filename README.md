![Boxtime logo](boxtime-logo.png)

# boxtime - A CLI to get control of your time

boxtime is a command line tool to track your time. It focuses on doing everything with as few keystrokes as possible, because every bit of friction decreases the chance of building a time tracking habit. And only when you habitually track your time can you learn about your time spending patterns and take control of your time!

A CLI tool only goes so far in terms of productivity, however. You still need to switch to your console and type in the commands. To make it _really_ easy to track time, I plan to build an integration with a productivity tool like [Alfred](https://www.alfredapp.com/) or [Raycast](https://www.raycast.com/), so that tracking your time is only a few keystrokes away from wherever (on your computer) you currently are.

## Build 

```
./gradlew nativeCompile
```

This creates a native executable `boxtime` in `build/native/nativeCompile`. Requires GraalVM to compile. See more info in [HELP.md](HELP.md).

## Usage

The current set of commands. More to come.

### Manage tasks
```shell
# add a new task
./boxtime task add "Reticulate splines"

# list all previously added tasks
./boxtime task list

# delete all tasks (mostly used for development to clear the database)
./boxtime task reset
```

### Track your time

```shell
# start tracking a task
./boxtime track start <taskId>

# stop tracking
./boxtime track stop

# log a task without tracking (for example when you forgot to track)
./boxtime track log <taskId> <duration>
```

### Manage your time logs

```shell
# delete all time logs (mostly used for development to clear the database)
./boxtime log reset
```