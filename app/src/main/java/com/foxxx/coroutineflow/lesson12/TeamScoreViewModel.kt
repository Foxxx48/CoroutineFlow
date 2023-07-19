package com.foxxx.coroutineflow.lesson12

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TeamScoreViewModel : ViewModel() {

    private val _stateSharedFlow = MutableStateFlow<TeamScoreState>(TeamScoreState.Game(0, 0))
    val stateSharedFlow = _stateSharedFlow.asStateFlow()

    fun increaseScoreWithFlow(team: Team) {
        val currentState = _stateSharedFlow.value
        if (currentState is TeamScoreState.Game) {
            if (team == Team.TEAM_1) {
                val oldValue = currentState.score1
                val newValue = oldValue + 1
                _stateSharedFlow.value =
                    currentState.copy(
                        score1 = newValue,
                        score2 = currentState.score2
                    )

                if (newValue >= WINNER_SCORE) {
                    _stateSharedFlow.value =
                        TeamScoreState.Winner(
                            winnerTeam = Team.TEAM_1,
                            score1 = newValue,
                            score2 = currentState.score2
                        )
                }
            } else {
                val oldValue = currentState.score2
                val newValue = oldValue + 1

                _stateSharedFlow.value =
                    currentState.copy(
                        score1 = currentState.score1,
                        score2 = newValue
                    )

                if (newValue >= WINNER_SCORE) {
                    _stateSharedFlow.value =
                        TeamScoreState.Winner(
                            winnerTeam = Team.TEAM_2,
                            score1 = currentState.score1,
                            score2 = newValue
                        )
                }
            }
        }
    }

    companion object {
        const val WINNER_SCORE = 7
    }
}