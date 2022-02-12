package com.aguragorn.whatword.statistics.storage.sqldelight

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.sqljs.initSqlDriver
import kotlinx.coroutines.await

actual class DriverProvider {
    actual suspend fun getDriver(): SqlDriver {
        return initSqlDriver(StatisticsDB.Schema).await()
    }
}