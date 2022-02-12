package com.aguragorn.whatword.statistics.storage.sqldelight

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual class DriverProvider : CoroutineScope {
    override val coroutineContext = Dispatchers.IO

    actual suspend fun getDriver(): SqlDriver = withContext(coroutineContext) {
        return@withContext JdbcSqliteDriver("data/statistics.db").also { driver ->
            StatisticsDB.Schema.create(driver)
        }
    }
}