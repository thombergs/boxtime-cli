![Boxtime logo](boxtime-logo.png)

# boxtime - A CLI to get control of your time

boxtime is a command line tool to track your time. It focuses on doing everything with as few keystrokes as possible, because every bit of friction decreases the chance of building a time tracking habit. And only when you habitually track your time can you learn about your time spending patterns and take control of your time!

A CLI tool only goes so far in terms of productivity, however. You still need to switch to your console and type in the commands. To make it _really_ easy to track time, user the Boxtime [Alfred](https://alfredapp.com) workflow, so that tracking your time is only a few keystrokes away from wherever (on your computer) you currently are.

## Installation

Install the `boxtime` command via homebrew:

```
brew tap thombergs/boxtime
brew install boxtime
```

Repeat these steps after a time to get the newest version.

If you want to use the Alfred workflow, additionally run:

```
boxtime alfred install
```

If that doesn't work for some reason, you can download the Alfred workflow in the [latest release](https://github.com/thombergs/boxtime-cli/releases/latest) and execute it. Alfred should pick it up.

## Alfred workflow usage

Open your Alfred console (CMD+SPACE by default) and start by typing "t<SPACE>". Boxtime commands will come up and you just need to follow the prompts.

## CLI Usage

Add the `-h` flag to a command to show all available options and arguments.

```shell
# print out information about the task you're currently tracking 
# and how much time you've tracked today
./boxtime status

# add a new task
./boxtime task add "Reticulate splines"

# list all available added tasks
./boxtime task list

# delete all tasks (mostly used for development to clear the database)
./boxtime task reset

# start tracking a task
./boxtime track start <taskId>

# stop tracking
./boxtime track stop

# log a task without tracking (for example when you forgot to track)
./boxtime track log <taskId> <duration>

# delete all time logs (mostly used for development to clear the database)
./boxtime log reset
```

## Configuration

### Environment variables

- `BOXTIME_DATABASE_FILE`: Path to the file in which to store the local database. Default: `~/.boxtime/boxtime-db` 

## Help

Feel free to reach out via GitHub issues!