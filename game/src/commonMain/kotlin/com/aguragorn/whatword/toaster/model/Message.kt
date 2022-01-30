package com.aguragorn.whatword.toaster.model

data class Message(
    val type: Message.Type = Type.INFO,
    val text: String
) {
    enum class Type(level: Int) {
        INFO(0),
        SUCCESS(0),
        WARNING(1),
        ERROR(2);
    }
}