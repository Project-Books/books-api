package com.karankumar.booksapi.model;

public enum AwardName {
    WOMENS_PRIZE("Women's Prize for Fiction"),
    ORWELL_PRIZE("Orwell Book Prize"),
    PORTICO_PRIZE("The Portico Prize"),
    BAILLIE_PRIZE("The Baillie Gifford Prize for Non-Fiction"),
    DYLAN_PRIZE("International Dylan Thomas Prize"),
    BOOKER_PRIZE("The Man Booker Prize"),
    BOOKER_INTER_PRIZE("The Man Booker International Prize"),
    NOBEL_PRIZE("Nobel Prize in Literature");

    private String prizeName;

    AwardName(String prizeName) {
        this.prizeName=prizeName;
    }

    public String getPrizeName() {
        return prizeName;
    }

    @Override
    public String toString() {
        return prizeName;
    }
}
