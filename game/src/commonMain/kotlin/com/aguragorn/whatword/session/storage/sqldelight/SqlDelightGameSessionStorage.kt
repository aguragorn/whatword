package com.aguragorn.whatword.session.storage.sqldelight

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.session.model.GameSession
import com.aguragorn.whatword.session.storage.GameSessionStorage
import kotlinx.datetime.LocalDate

class SqlDelightGameSessionStorage : GameSessionStorage {
    override suspend fun save(session: GameSession): GameSession {
        TODO("Not yet implemented")
    }

    override suspend fun getSessionFor(gameConfig: GameConfig, date: LocalDate): GameSession? {
        TODO("Not yet implemented")
    }
}