package com.dicoding.skivent.dataclass

data class RegisterDataAccount(
    var name: String,
    var email: String,
    var password: String
)
data class LoginDataAccount(
    var email: String,
    var password: String
)
data class ResponseDetail(
    var error: Boolean,
    var message: String
)
data class ResponseLogin(
    var error: Boolean,
    var message: String,
    var loginResults: LoginResults
)
data class LoginResults(
    var userId: String,
    var name: String,
    var token: String
)