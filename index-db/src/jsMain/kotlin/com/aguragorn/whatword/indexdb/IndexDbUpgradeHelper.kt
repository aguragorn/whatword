package com.aguragorn.whatword.indexdb

import com.juul.indexeddb.Database
import com.juul.indexeddb.KeyPath
import com.juul.indexeddb.ObjectStore
import com.juul.indexeddb.VersionChangeTransaction

interface IndexDbUpgradeHelper {
    val databaseName: String get() = "default"
    val currentVersion: Int
    val onUpgrade: VersionChangeTransaction.(database: Database, oldVersion: Int) -> Unit
}

val String.keyPath: KeyPath get() = KeyPath(this)

fun VersionChangeTransaction.createNonUniqueIndex(store: ObjectStore, fieldName: String) {
    store.createIndex(fieldName, fieldName.keyPath, unique = false)
}