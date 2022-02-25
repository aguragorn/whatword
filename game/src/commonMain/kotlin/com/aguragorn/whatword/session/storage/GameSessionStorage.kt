package com.aguragorn.whatword.session.storage

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.session.model.GameSession
import kotlinx.datetime.LocalDate

interface GameSessionStorage {
    suspend fun save(session: GameSession): GameSession
    suspend fun getSessionFor(gameConfig: GameConfig, date: LocalDate): GameSession?
}