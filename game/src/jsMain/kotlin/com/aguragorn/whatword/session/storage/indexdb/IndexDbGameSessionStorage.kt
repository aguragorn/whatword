package com.aguragorn.whatword.session.storage.indexdb

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.indexdb.DatabaseWrapper
import com.aguragorn.whatword.indexdb.IndexDbFactory
import com.aguragorn.whatword.session.model.GameSession
import com.aguragorn.whatword.session.storage.GameSessionStorage
import com.aguragorn.whatword.session.storage.indexdb.model.GameSessionEntity
import com.aguragorn.whatword.session.storage.indexdb.model.GameSessionEntityMeta
import com.aguragorn.whatword.session.storage.indexdb.model.toGameSessionEntity
import com.aguragorn.whatword.session.storage.indexdb.model.toGamesSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IndexDbGameSessionStorage(
    private val dbFactory: IndexDbFactory
) : GameSessionStorage,
    CoroutineScope {

    override val coroutineContext = Dispatchers.Default
    private lateinit var gameSessionDb: DatabaseWrapper<GameSessionEntity>

    init {
        launch {
            gameSessionDb = dbFactory.getDatabase(meta = GameSessionEntityMeta)
        }
    }

    override suspend fun save(session: GameSession): GameSession {
        return gameSessionDb
            .save(session.toGameSessionEntity())
            .toGamesSession()
    }

    override suspend fun getSessionFor(gameConfig: GameConfig): GameSession? {
        return gameSessionDb
            .findOneOrNull { it.toGamesSession().gameConfig == gameConfig }
            ?.toGamesSession()
    }
}