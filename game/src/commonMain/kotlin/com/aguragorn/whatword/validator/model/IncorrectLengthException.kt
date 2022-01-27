package com.aguragorn.whatword.validator.model

class IncorrectLengthException(
    val expectedLength: Int
) : Exception()