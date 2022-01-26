# WhatWord
Unlocking clues every turn, you have 6 tries to guess that mystery word.

## Game Rules
- You have 6 tries to guess the mystery word
- On every trial letters will be marked as
  - Correct (Blue), when the letter exists in the word and is in the right position
  - Misplaced (Orange), when the letter exists in the word but is in the wrong position
  - Incorrect (Gray), when the letter does not exist in the word

## Architecture
### Modules
- **`core`** - models, logic, and utilities shared among all modules
- **`ui/common-ui`** - compose ui shared between android and desktop
- **`ui/android`** - android app
- **`ui/desktop`** - desktop app
- **`ui/web`** - web app, includes compose ui for web
- **`keyboard`** - keyboard behavior
- **`grid`** - grid behavior
- **`validator`** - validation logic
- **`game`** - main game logic
