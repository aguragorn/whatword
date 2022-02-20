package com.aguragorn.whatword.indexdb

import com.aguragorn.whatword.indexdb.model.EntityMeta
import com.juul.indexeddb.Database
import com.juul.indexeddb.KeyPath
import com.juul.indexeddb.ObjectStore
import com.juul.indexeddb.VersionChangeTransaction

interface IndexDbUpgradeHelper {
    val currentVersion: Int
    val onUpgrade: VersionChangeTransaction.(database: Database, meta: EntityMeta, oldVersion: Int) -> Unit
}

val String.keyPath: KeyPath get() = KeyPath(this)

fun VersionChangeTransaction.createNonUniqueIndex(store: ObjectStore, fieldName: String) {
    store.createIndex(fieldName, fieldName.keyPath, unique = false)
}