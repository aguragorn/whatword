package com.aguragorn.whatword.statistics.storage.sqldelight

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SqlDelightStatsDataStore(
    private val db: DB
) : StatsDataStore,
    CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    override suspend fun saveStats(
        stats: Stats
    ): Stats = withContext(coroutineContext) {
        return@withContext db.getInstance().run {
            val updated = stats.id
                ?.let { statsQueries.findById(it).executeAsOneOrNull() }
                ?.apply {
                    statsQueries.update(
                        id = id,
                        games_played = games_played,
                        game_config = game_config,
                        wins = wins,
                        current_streak = current_streak,
                        best_time_ms = best_time_ms,
                        round_stats = round_stats,
                        last_mystery_word = last_mystery_word
                    )
                }

            // updated is null if there's no existing record in DB
            if (updated != null) return@run updated.toDomain()

            val statsEntity = stats.toEntity()
            statsQueries.save(statsEntity)
            return@run statsEntity.toDomain()
        }
    }

    override suspend fun getStatsFor(
        gameConfig: GameConfig
    ): Stats? = withContext(coroutineContext) {

        return@withContext db.getInstance().statsQueries
            .findByGameConfig(game_config = gameConfig)
            .executeAsOneOrNull()
            ?.toDomain()
    }

}