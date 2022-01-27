package com.aguragorn.whatword.statistics.di

import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.aguragorn.whatword.statistics.usecase.SaveGamesStats
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bind
import org.kodein.di.singleton

var statsDi: DirectDI = DI.direct {
    bind { singleton { StatsDataStore() } }
    bind { singleton { SaveGamesStats() } }
}