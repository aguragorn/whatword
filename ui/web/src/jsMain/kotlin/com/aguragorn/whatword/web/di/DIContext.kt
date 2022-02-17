package com.aguragorn.whatword.web.di

import com.aguragorn.whatword.game.storage.MysteryWordDataStore
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.game.usecase.RandomMysteryWord
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.aguragorn.whatword.statistics.storage.indexdb.IndexDbFactory
import com.aguragorn.whatword.statistics.storage.indexdb.IndexDbStatsDataStore
import com.aguragorn.whatword.statistics.storage.sqldelight.DriverProvider
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.statistics.usecase.GetGameStats
import com.aguragorn.whatword.statistics.usecase.SaveGamesStats
import com.aguragorn.whatword.toaster.ToasterViewModel
import com.aguragorn.whatword.validator.usecase.ValidateWord
import org.kodein.di.DI
import org.kodein.di.DirectDI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

val DI: DirectDI = DI.direct {
    bindStatistics()
    bindMysteryWord()
    bindValidator()
    bindToaster()
    bindGame()
}

private fun DI.MainBuilder.bindStatistics() {
    bindSingleton { IndexDbFactory() }
    bindSingleton { DriverProvider() }
    bindSingleton<StatsDataStore> { IndexDbStatsDataStore(instance()) }

    bindSingleton { SaveGamesStats(statsStore = instance()) }
    bindSingleton { GetGameStats(statsStore = instance()) }

    bindSingleton { StatisticsViewModel(getGameStats = instance()) }
}

private fun DI.MainBuilder.bindMysteryWord() {
    bindSingleton { MysteryWordDataStore() }
}

private fun DI.MainBuilder.bindValidator() {
    bindSingleton { ValidateWord(mysteryWordDataStore = instance()) }
}

private fun DI.MainBuilder.bindToaster() {
    bindSingleton { ToasterViewModel() }
}

private fun DI.MainBuilder.bindGame() {
    bindSingleton { RandomMysteryWord(mysteryWordDataStore = instance()) }
    bindSingleton {
        GameViewModel(
            getGameStats = instance(),
            randomMysteryWord = instance(),
            saveGameStats = instance(),
            statsViewModel = instance(),
            toaster = instance(),
            validate = instance(),
        )
    }
}