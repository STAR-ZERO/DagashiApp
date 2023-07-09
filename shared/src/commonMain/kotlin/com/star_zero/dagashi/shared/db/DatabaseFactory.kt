package com.star_zero.dagashi.shared.db

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import com.starzero.dagashi.shared.db.Milestones

object DatabaseFactory {
    fun createDatabase(driverFactory: DatabaseDriverFactory): DagashiDatabase {
        return DagashiDatabase(
            driverFactory.createDriver(),
            Milestones.Adapter(
                sortAdapter = IntColumnAdapter
            )
        )
    }
}
