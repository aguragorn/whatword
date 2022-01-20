# WhatWord
Unlocking clues every turn, you have 6 tries to guess that mystery word.

## Game Rules
- You have 6 tries to guess the mystery word
- On very trial letters will be marked as 
  - Correct (Blue, #182379), when the letter exists in the word and is in the right position
  - Misplaced (Orange, #FF9214), when the letter exists in the word but is in the wrong position
  - Incorrect (White, #CCCCCC), when the letter does not exist in the word

## Architecture
### Modules
- **`core`** - models, logic, and utilities shared among all modules
- **`common-ui`** - compose ui shared between android and desktop
- **`android`** - android app
- **`desktop`** - desktop app
- **`web`** - web app, includes compose ui for web
- **`keyboard`** - keyboard behavior
- **`grid`** - grid behavior
