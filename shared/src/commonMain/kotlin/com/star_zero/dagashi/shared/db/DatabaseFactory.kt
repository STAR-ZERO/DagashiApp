package com.star_zero.dagashi.shared.db

object DatabaseFactory {
    fun createDatabase(driverFactory: DatabaseDriverFactory): DagashiDatabase {
        return DagashiDatabase(driverFactory.createDriver())
    }
}
