package com.aguragorn.whatword.session.storage.indexdb

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.game.utils.asPuzzleNumber
import com.aguragorn.whatword.indexdb.DatabaseWrapper
import com.aguragorn.whatword.session.model.GameSession
import com.aguragorn.whatword.session.storage.GameSessionStorage
import com.aguragorn.whatword.session.storage.indexdb.model.GameSessionEntity
import com.aguragorn.whatword.session.storage.indexdb.model.toGameSessionEntity
import com.aguragorn.whatword.session.storage.indexdb.model.toGamesSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.datetime.LocalDate

class IndexDbGameSessionStorage constructor(
    private val gameSessionDb: DatabaseWrapper<GameSessionEntity>
) : GameSessionStorage,
    CoroutineScope {

    override val coroutineContext = Dispatchers.Default

    override suspend fun save(session: GameSession): GameSession {
        return gameSessionDb
            .save(session.toGameSessionEntity())
            .toGamesSession()
    }

    override suspend fun getSessionFor(gameConfig: GameConfig, date: LocalDate): GameSession? {
        return gameSessionDb
            .findOneOrNull { entity ->
                entity.toGamesSession().let {
                    it.gameConfig == gameConfig
                            && it.mysteryWord.puzzleNumber == date.asPuzzleNumber()
                }
            }
            ?.toGamesSession()
    }
}