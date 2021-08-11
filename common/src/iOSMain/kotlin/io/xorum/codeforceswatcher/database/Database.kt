package io.xorum.codeforceswatcher.database

import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.xorum.codeforceswatcher.CWDatabase
import io.xorum.codeforceswatcher.redux.sqlDriver

fun initDatabase() {
    sqlDriver = NativeSqliteDriver(CWDatabase.Schema, "database")
}
