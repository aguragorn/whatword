package com.aguragorn.whatword.indexdb

import com.aguragorn.whatword.indexdb.model.Entity
import com.aguragorn.whatword.indexdb.model.EntityMeta
import com.juul.indexeddb.openDatabase

class IndexDbFactory(
    private val upgradeHelper: IndexDbUpgradeHelper
) {

    suspend fun <T : Entity> getDatabase(
        meta: EntityMeta,
    ): DatabaseWrapper<T> = DatabaseWrapper(
        database = openDatabase(
            name = upgradeHelper.databaseName,
            version = upgradeHelper.currentVersion
        ) { database, oldVersion, _ ->
            upgradeHelper.onUpgrade(this, database, oldVersion)
        },
        meta = meta
    )
}