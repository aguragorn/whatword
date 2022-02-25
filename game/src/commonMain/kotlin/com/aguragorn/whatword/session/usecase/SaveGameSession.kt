package com.aguragorn.whatword.session.usecase

import com.aguragorn.whatword.session.model.GameSession
import com.aguragorn.whatword.session.storage.GameSessionStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveGameSession(
    private val gameSessionStore: GameSessionStorage
) : CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    suspend operator fun invoke(
        gameSession: GameSession
    ): GameSession = withContext(coroutineContext) {
        return@withContext gameSessionStore.save(gameSession)
    }
}