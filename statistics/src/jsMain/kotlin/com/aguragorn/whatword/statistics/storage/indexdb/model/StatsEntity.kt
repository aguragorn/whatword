package com.aguragorn.whatword.statistics.storage.indexdb.model

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.model.RoundsStat
import com.aguragorn.whatword.statistics.model.Stats
import com.aguragorn.whatword.statistics.storage.indexdb.toLongOrZero
import com.benasher44.uuid.uuid4
import com.juul.indexeddb.KeyPath
import kotlinext.js.jso
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

external interface StatsEntity : Entity {
    override var id: String?
    var bestTimeMs: String
    var currentStreak: String
    var gameConfig: GameConfigEntity
    var gamesPlayed: String
    var lastMysteryWord: String
    var maxStreak: String
    var roundsStats: String
    var wins: String
}

external interface GameConfigEntity {
    var language: String
    var wordLength: String
    var maxTurnCount: String
}

object StatsEntityMeta : EntityMeta {
    const val id: String = "id"
    const val bestTimeMs: String = "bestTimeMs"
    const val currentStreak: String = "currentStreak"
    const val gameConfig: String = "gameConfig"
    const val gamesPlayed: String = "gamesPlayed"
    const val lastMysteryWord: String = "lastMysteryWord"
    const val maxStreak: String = "maxStreak"
    const val roundsStats: String = "roundsStat"
    const val wins: String = "wins"

    override val tableName: String = "StatsEntity"
    override val identityField: String = id
}

val String.keyPath: KeyPath get() = KeyPath(this)

// region Stats converter

fun Stats.toEntity(): StatsEntity {
    val model = this

    return jso {
        id = model.id ?: uuid4().toString()
        bestTimeMs = model.bestTimeMs.toString()
        currentStreak = model.currentStreak.toString()
        gameConfig = model.gameConfig.toEntity()
        gamesPlayed = model.gamesPlayed.toString()
        lastMysteryWord = model.lastMysteryWord
        maxStreak = model.maxStreak.toString()
        roundsStats = model.roundsStats.toEntity()
        wins = model.wins.toString()
    }
}

fun StatsEntity.toDomain(): Stats = Stats(
    id = id,
    gamesPlayed = gamesPlayed.toLongOrZero(),
    gameConfig = gameConfig.toDomain(),
    wins = wins.toLongOrZero(),
    currentStreak = currentStreak.toLongOrZero(),
    maxStreak = maxStreak.toLongOrZero(),
    bestTimeMs = bestTimeMs.toLongOrZero(),
    roundsStats = roundsStats.toDomain(),
    lastMysteryWord = lastMysteryWord,
)

// endregion

// region GameConfig converter

fun GameConfig.toEntity(): GameConfigEntity {
    val model = this

    return jso {
        language = model.language
        maxTurnCount = model.maxTurnCount.toString()
        wordLength = model.wordLength.toString()
    }
}

fun GameConfigEntity.toDomain(): GameConfig = GameConfig(
    language = language,
    wordLength = wordLength.toInt(),
    maxTurnCount = maxTurnCount.toInt(),
)

// endregion

// region RoundsStat converter

fun List<RoundsStat>.toEntity(): String = Json.Default.encodeToString(this)

fun String.toDomain(): MutableList<RoundsStat> = Json.Default
    .decodeFromString<List<RoundsStat>>(this)
    .toMutableList()

// endregion
