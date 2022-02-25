package com.aguragorn.whatword.session.usecase

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.session.model.GameSession
import com.aguragorn.whatword.session.storage.GameSessionStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.LocalDate

class GetGameSession(
    private val gameSessionStore: GameSessionStorage
) : CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    /**
     * Retrieves an existing session for the given date, creates
     * a new one if there is none.
     */
    suspend fun invoke(
        gameConfig: GameConfig,
        date: LocalDate,
    ): GameSession = withContext(coroutineContext) {
        val existingSession = gameSessionStore.getSessionFor(gameConfig = gameConfig, date)

        return@withContext existingSession ?: GameSession(gameConfig = gameConfig).also {
            gameSessionStore.save(it)
        }
    }
}