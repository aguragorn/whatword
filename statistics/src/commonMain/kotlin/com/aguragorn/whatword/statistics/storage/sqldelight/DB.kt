package com.aguragorn.whatword.statistics.storage.sqldelight

import com.aguragorn.whatword.config.model.GameConfig
import com.aguragorn.whatword.statistics.model.RoundsStat
import com.squareup.sqldelight.ColumnAdapter
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

expect class DriverProvider {
    suspend fun getDriver(): SqlDriver
}

class DB constructor(
    private val driverProvider: DriverProvider,
) {
    private var driver: SqlDriver? = null
    private var instance: StatisticsDB? = null

    suspend fun getInstance(): StatisticsDB {
        return instance ?: StatisticsDB(
            driver = driverProvider.getDriver().also { driver = it },
            StatsAdapter = Stats.Adapter(
                game_configAdapter = GameConfigAdapter,
                round_statsAdapter = RoundsStatsAdapter,
            )
        ).also { db ->
            driver?.let {
                try {
                    StatisticsDB.Schema.create(it)
                } catch (e: Throwable) {
                    println("Create schema failed: ${e.message}")
                }

            }
            instance = db
        }
    }
}

object RoundsStatsAdapter : ColumnAdapter<List<RoundsStat>, String> {
    private val json = Json.Default
    override fun decode(databaseValue: String): List<RoundsStat> {
        return json.decodeFromString(databaseValue)
    }

    override fun encode(value: List<RoundsStat>): String {
        return json.encodeToString(value)
    }

}

object GameConfigAdapter : ColumnAdapter<GameConfig, String> {
    private val json = Json.Default
    override fun decode(databaseValue: String): GameConfig {
        return json.decodeFromString(databaseValue)
    }

    override fun encode(value: GameConfig): String {
        return json.encodeToString(value)
    }

}