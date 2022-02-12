package com.aguragorn.whatword.statistics.storage.sqldelight

import com.benasher44.uuid.uuid4
import com.aguragorn.whatword.statistics.model.Stats as StatsDomain

fun StatsDomain.toEntity(): Stats = Stats(
    id = id ?: uuid4().toString(),
    games_played = gamesPlayed,
    game_config = gameConfig,
    wins = wins,
    current_streak = currentStreak,
    max_streak = maxStreak,
    best_time_ms = bestTimeMs,
    round_stats = roundsStats,
    last_mystery_word = lastMysteryWord
)

fun Stats.toDomain(): StatsDomain = StatsDomain(
    id = id,
    gamesPlayed = games_played,
    gameConfig = game_config,
    wins = wins,
    currentStreak = current_streak,
    maxStreak = max_streak,
    bestTimeMs = best_time_ms,
    roundsStats = round_stats.toMutableList(),
    lastMysteryWord = last_mystery_word
)