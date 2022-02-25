@file:Suppress("UnusedImport")

package com.aguragorn.whatword.web.di

import com.aguragorn.whatword.game.storage.MysteryWordStorage
import com.aguragorn.whatword.game.ui.GameViewModel
import com.aguragorn.whatword.game.usecase.RandomMysteryWord
import com.aguragorn.whatword.indexdb.DatabaseWrapper
import com.aguragorn.whatword.session.storage.GameSessionStorage
import com.aguragorn.whatword.session.storage.indexdb.IndexDbGameSessionStorage
import com.aguragorn.whatword.session.storage.indexdb.model.GameSessionEntityMeta
import com.aguragorn.whatword.session.usecase.GetGameSession
import com.aguragorn.whatword.session.usecase.SaveGameSession
import com.aguragorn.whatword.statistics.storage.StatsDataStore
import com.aguragorn.whatword.statistics.storage.indexdb.IndexDbStatsDataStore
import com.aguragorn.whatword.statistics.storage.indexdb.StatsDbUpgradeHelper
import com.aguragorn.whatword.statistics.storage.indexdb.model.StatsEntityMeta
import com.aguragorn.whatword.statistics.storage.sqldelight.DriverProvider
import com.aguragorn.whatword.statistics.ui.StatisticsViewModel
import com.aguragorn.whatword.statistics.usecase.GetGameStats
import com.aguragorn.whatword.statistics.usecase.SaveGamesStats
import com.aguragorn.whatword.storage.indexdb.GameDbUpgradeHelper
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
    bindSession()
    bindGame()
}

private fun DI.MainBuilder.bindStatistics() {
    bindSingleton { DriverProvider() }
    bindSingleton<StatsDataStore> {
        IndexDbStatsDataStore(
            DatabaseWrapper(StatsDbUpgradeHelper(), StatsEntityMeta)
        )
    }

    bindSingleton { SaveGamesStats(statsStore = instance()) }
    bindSingleton { GetGameStats(statsStore = instance()) }

    bindSingleton { StatisticsViewModel(getGameStats = instance()) }
}

private fun DI.MainBuilder.bindMysteryWord() {
    bindSingleton { MysteryWordStorage() }
}

private fun DI.MainBuilder.bindValidator() {
    bindSingleton { ValidateWord(mysteryWordStorage = instance()) }
}

private fun DI.MainBuilder.bindToaster() {
    bindSingleton { ToasterViewModel() }
}

private fun DI.MainBuilder.bindSession() {
    bindSingleton<GameSessionStorage> {
        IndexDbGameSessionStorage(
            DatabaseWrapper(GameDbUpgradeHelper(), GameSessionEntityMeta)
        )
    }
    bindSingleton { GetGameSession(gameSessionStore = instance()) }
    bindSingleton { SaveGameSession(gameSessionStore = instance()) }

}

private fun DI.MainBuilder.bindGame() {
    bindSingleton { RandomMysteryWord(mysteryWordStorage = instance()) }
    bindSingleton {
        GameViewModel(
            getGameStats = instance(),
            getSession = instance(),
            randomMysteryWord = instance(),
            saveGameStats = instance(),
            saveSession = instance(),
            statsViewModel = instance(),
            toaster = instance(),
            validate = instance(),
        )
    }
}