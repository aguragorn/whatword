package com.aguragorn.whatword.session.storage

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.session.model.GameSession

interface GameSessionStorage {
    suspend fun save(session: GameSession): GameSession
    suspend fun getSessionFor(gameConfig: GameConfig): GameSession?
}