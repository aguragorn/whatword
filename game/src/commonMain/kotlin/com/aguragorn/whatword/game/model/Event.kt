package com.aguragorn.whatword.game.model

import com.aguragorn.whatword.statistics.model.Stats

sealed class Event {
    class ShowStats(stats: Stats) : Event()
}