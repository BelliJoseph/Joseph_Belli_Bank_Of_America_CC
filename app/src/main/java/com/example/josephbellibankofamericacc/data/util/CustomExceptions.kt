package com.example.josephbellibankofamericacc.data.util

class NullResponse(message: String = "Response is null") : Exception(message)
class ResponseFailure(message: String?) : Exception(message)