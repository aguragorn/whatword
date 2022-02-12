package com.aguragorn.whatword.statistics.storage.sqldelight

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DriverProvider(
    private val context: Context,
) {
    actual suspend fun getDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = StatisticsDB.Schema,
            context = context
        )
    }
}
