package com.aguragorn.whatword.statistics.di

import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.aguragorn.whatword.statistics.usecase.GetGameStats
import com.aguragorn.whatword.statistics.usecase.SaveGamesStats
import org.kodein.di.*

var statsDi: DirectDI = DI.direct {
    bind { singleton { StatsDataStore() } }
    bind { singleton { SaveGamesStats(instance()) } }
    bind { singleton { GetGameStats(instance()) } }
}