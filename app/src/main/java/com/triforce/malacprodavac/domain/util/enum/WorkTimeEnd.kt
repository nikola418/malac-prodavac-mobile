package com.triforce.malacprodavac.domain.util.enum

enum class WorkTimeEnd (val text: String) {
    Time1("15:00"),
    Time2("15:30"),
    Time3("16:00"),
    Time4("16:30"),
    Time5("17:00"),
    Time6("17:30"),
    Time7("18:00"),
    Time8("18:30"),
    Time9("19:00"),
    Time10("19:30"),
    Time11("20:00"),
    Time12("20:30"),
    Time13("21:00"),
    Time14("21:30"),
    Time15("22:00");

    override fun toString(): String {
        return text
    }
}