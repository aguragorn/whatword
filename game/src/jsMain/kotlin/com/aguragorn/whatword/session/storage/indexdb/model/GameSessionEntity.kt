package com.aguragorn.whatword.session.storage.indexdb.model

import com.aguragorn.whatword.config.indexdb.model.GameConfigEntity
import com.aguragorn.whatword.config.indexdb.model.toGameConfig
import com.aguragorn.whatword.config.indexdb.model.toGameConfigEntity
import com.aguragorn.whatword.game.storage.indexdb.model.MysteryWordEntity
import com.aguragorn.whatword.game.storage.indexdb.model.toMysteryWord
import com.aguragorn.whatword.game.storage.indexdb.model.toMysteryWordEntity
import com.aguragorn.whatword.indexdb.model.Entity
import com.aguragorn.whatword.indexdb.model.EntityMeta
import com.aguragorn.whatword.session.model.GameSession
import com.aguragorn.whatword.session.model.GridState
import com.aguragorn.whatword.session.model.KeyboardState
import kotlinext.js.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

typealias GridStateEntity = String
typealias KeyboardStateEntity = String

external interface GameSessionEntity : Entity {
    var gameConfig: GameConfigEntity
    var gridState: GridStateEntity
    var keyboardState: KeyboardStateEntity
    var mysteryWord: MysteryWordEntity
}

object GameSessionEntityMeta : EntityMeta {
    const val id: String = "id"
    const val gameConfig: String = "gameConfig"
    const val gridState: String = "gridState"
    const val keyboardState: String = "keyboardState"
    const val mysteryWord: String = "mysteryWord"

    override val tableName: String = "GameSession"
    override val identityField: String = "id"
}

fun GameSession.toGameSessionEntity(): GameSessionEntity = jso {
    id
    gameConfig = this@toGameSessionEntity.gameConfig.toGameConfigEntity()
    gridState = this@toGameSessionEntity.gridState.toGridStateEntity()
    keyboardState = this@toGameSessionEntity.keyboardState.toKeyboardStateEntity()
    mysteryWord = this@toGameSessionEntity.mysteryWord.toMysteryWordEntity()
}

fun GameSessionEntity.toGamesSession(): GameSession = GameSession(
    id = id,
    gameConfig = gameConfig.toGameConfig(),
    gridState = gridState.toGridState(),
    keyboardState = keyboardState.toKeyboardState(),
    mysteryWord = mysteryWord.toMysteryWord()
)

fun GridState.toGridStateEntity(): GridStateEntity = Json.Default.encodeToString(this)
fun GridStateEntity.toGridState(): GridState = Json.Default.decodeFromString(this)

fun KeyboardState.toKeyboardStateEntity(): KeyboardStateEntity = Json.Default.encodeToString(this)
fun KeyboardStateEntity.toKeyboardState(): KeyboardState = Json.Default.decodeFromString(this)
