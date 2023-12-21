package com.triforce.malacprodavac.domain.util.enum

enum class DaysOfTheWeek(val text: String) {
    Monday("Ponedeljak"),
    Tuesday("Utorak"),
    Wednesday("Sreda"),
    Thursday("Četvrtak"),
    Friday("Petak"),
    Saturday("Subota"),
    Sunday("Nedelja");

    fun toText(): String {
        if(text == "Ponedeljak")
            return "Monday"
        else if(text == "Utorak")
            return "Tuesday"
        else if(text == "Sreda")
            return "Wednesday"
        else if(text == "Četvrtak")
            return "Thursday"
        else if(text == "Petak")
            return "Friday"
        else if(text == "Subota")
            return "Saturday"
        return "Sunday"
    }

    override fun toString(): String {
        return text
    }
}