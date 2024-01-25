package org.company.app.presentation.authentication.login

import cafe.adriel.voyager.core.model.StateScreenModel
import kotlinx.coroutines.flow.update

class LoginScreenModel : StateScreenModel<LoginScreenModel.State>(
    initialState = State()
) {

    fun onUsernameChanged(username: String) = mutableState.update {
        it.copy(
            username = username
        )
    }

    data class State(
        val username: String = "",
        val password: String = "",
    )
}