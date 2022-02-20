package com.aguragorn.whatword.android.di

import android.content.Context
import com.aguragorn.whatword.game.storage.MysteryWordStorage
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.game.usecase.RandomMysteryWord
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.aguragorn.whatword.statistics.storage.sqldelight.DB
import com.aguragorn.whatword.statistics.storage.sqldelight.DriverProvider
import com.aguragorn.whatword.statistics.storage.sqldelight.SqlDelightStatsDataStore
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.statistics.usecase.GetGameStats
import com.aguragorn.whatword.statistics.usecase.SaveGamesStats
import com.aguragorn.whatword.toaster.ToasterViewModel
import com.aguragorn.whatword.validator.usecase.ValidateWord
import org.kodein.di.DirectDI
import org.kodein.di.bindSingleton
import org.kodein.di.instance
import org.kodein.di.DI as KodeInDI

object DI {
    const val TAG_APPLICATION = "Application"

    lateinit var instance: DirectDI

    fun init(appContext: Context) {
        instance = KodeInDI.direct {
            bindPlatform(context = appContext)
            bindStatistics()
            bindMysteryWord()
            bindValidator()
            bindToaster()
            bindGame()
        }
    }
}

private fun KodeInDI.MainBuilder.bindPlatform(context: Context) {
    bindSingleton(tag = DI.TAG_APPLICATION) { context }
}

private fun KodeInDI.MainBuilder.bindStatistics() {
    bindSingleton { DriverProvider(context = instance(tag = DI.TAG_APPLICATION)) }
    bindSingleton<StatsDataStore> { SqlDelightStatsDataStore(DB(driverProvider = instance())) }

    bindSingleton { SaveGamesStats(statsStore = instance()) }
    bindSingleton { GetGameStats(statsStore = instance()) }

    bindSingleton { StatisticsViewModel(getGameStats = instance()) }
}

private fun KodeInDI.MainBuilder.bindMysteryWord() {
    bindSingleton { MysteryWordStorage() }
}

private fun KodeInDI.MainBuilder.bindValidator() {
    bindSingleton { ValidateWord(mysteryWordStorage = instance()) }
}

private fun KodeInDI.MainBuilder.bindToaster() {
    bindSingleton { ToasterViewModel() }
}

private fun KodeInDI.MainBuilder.bindGame() {
    bindSingleton { RandomMysteryWord(mysteryWordStorage = instance()) }
    bindSingleton {
        GameViewModel(
            validate = instance(),
            randomMysteryWord = instance(),
            saveGameStats = instance(),
            toaster = instance(),
            statsViewModel = instance(),
            getGameStats = instance()
        )
    }
}