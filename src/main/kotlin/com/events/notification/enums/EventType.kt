package com.events.notification.enums

enum class EventType(val description: String) {

    MUSIC("Music"),
    GASTRONOMY("Food"),
    SPORTS("Sporting"),
    THEATER("Theater"),
    CINEMA("Cinema"),
    ART_EXHIBITION("Art exhibitions"),
    WORKSHOP("Workshops"),
    CONFERENCE("Conferences"),
    FESTIVAL("Festival"),
    LITERATURE("Literature");

    override fun toString(): String {
        return description
    }
}