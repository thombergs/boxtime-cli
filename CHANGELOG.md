# Changelog

## v.0.0.6
- `boxtime task list`
  - Now returns tasks in order of recent activity (created date, last logged date).
  - New parameter `--filter` to add a filter string that filters by task title and tag names.
  - New parameter `--count` to limit the number of tasks returned.
- `boxtime track log` 
  - works with task ID prefixes as well as full IDs now (i.e. entering `v2` will work for a task with ID `v2fyu2ws`).

## v0.0.5
- Add tags when adding a task: `boxtime task add "#project1 Do this #work"`. The tags are stripped from the task name and associated with the task for upcoming filtering functionality.

## v0.0.2

- Task IDs are now only 8 characters and you can specify just the first couple characters to identify a task (e.g. `boxtime track start v2` if the task ID is `v2fyu2ws`).

## v0.0.1

- Basic CLI commands for managing tasks and tracking time.
- Basic Alfred workflow to have the CLI commands always at your fingertips.