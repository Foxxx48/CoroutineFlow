package com.foxxx.coroutineflow.lesson12

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class TeamScoreViewModel : ViewModel() {

    private var cachedState: TeamScoreState = TeamScoreState.Game(0, 0)

    private val _stateSharedFlow = MutableSharedFlow<TeamScoreState>(replay = 1)
    val stateSharedFlow = _stateSharedFlow.asSharedFlow()
        .onEach { cachedState = it }

    fun increaseScoreWithFlow(team: Team) {
        viewModelScope.launch {
            if (cachedState is TeamScoreState.Game) {
                val currentState = cachedState as TeamScoreState.Game
                if (team == Team.TEAM_1) {
                    val oldValue = currentState.score1
                    val newValue = oldValue + 1
                    _stateSharedFlow.emit(
                        currentState.copy(
                            score1 = newValue,
                            score2 = currentState.score2
                        )
                    )

                    if (newValue >= WINNER_SCORE) {
                        _stateSharedFlow.emit(
                            TeamScoreState.Winner(
                                winnerTeam = Team.TEAM_1,
                                score1 = newValue,
                                score2 = currentState.score2
                            )
                        )
                    }
                } else {
                    val oldValue = currentState.score2
                    val newValue = oldValue + 1

                    _stateSharedFlow.emit(
                        currentState.copy(
                            score1 = currentState.score1,
                            score2 = newValue
                        )
                    )

                    if (newValue >= WINNER_SCORE) {
                        _stateSharedFlow.emit(
                            TeamScoreState.Winner(
                                winnerTeam = Team.TEAM_2,
                                score1 = currentState.score1,
                                score2 = newValue
                            )
                        )
                    }
                }
            }
        }

    }

    companion object {
        const val WINNER_SCORE = 7
    }
}