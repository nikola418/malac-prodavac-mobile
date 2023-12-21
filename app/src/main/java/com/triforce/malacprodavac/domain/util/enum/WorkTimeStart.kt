package com.triforce.malacprodavac.domain.util.enum

enum class WorkTimeStart(val text: String) {
    Time1("07:00"),
    Time2("07:30"),
    Time3("08:00"),
    Time4("08:30"),
    Time5("09:00"),
    Time6("09:30"),
    Time7("10:00");

    override fun toString(): String {
        return text
    }
}