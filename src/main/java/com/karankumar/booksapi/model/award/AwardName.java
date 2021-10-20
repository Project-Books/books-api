package com.karankumar.booksapi.model.award;

public enum AwardName {
    WOMENS_PRIZE("Women's Prize for Fiction"),
    ORWELL_PRIZE("Orwell Book Prize"),
    PORTICO_PRIZE("The Portico Prize"),
    BAILLIE_PRIZE("The Baillie Gifford Prize for Non-Fiction"),
    DYLAN_PRIZE("International Dylan Thomas Prize"),
    BOOKER_PRIZE("The Man Booker Prize"),
    BOOKER_INTER_PRIZE("The Man Booker International Prize"),
    NOBEL_PRIZE("Nobel Prize in Literature");

    private String awardName;

    public String getAwardName() {
        return this.awardName;
    }

    AwardName(String name) {
        this.awardName = name;
    }

    public static AwardName fromString(String inputName) {
        switch (inputName.toLowerCase()) {
            case "women's prize for fiction":
                return AwardName.WOMENS_PRIZE;
            case "orwell book prize":
                return AwardName.ORWELL_PRIZE;
            case "the portico prize":
                return AwardName.PORTICO_PRIZE;
            case "the baillie gifford prize for non-fiction":
                return AwardName.BAILLIE_PRIZE;
            case "international dylan thomas prize":
                return AwardName.DYLAN_PRIZE;
            case "the man booker prize":
                return AwardName.BOOKER_PRIZE;
            case "the man booker international prize":
                return AwardName.BOOKER_INTER_PRIZE;
            case "nobel prize in literature":
                return AwardName.NOBEL_PRIZE;
            default:
                throw new IllegalArgumentException("Literary Award [" + inputName
                        + "] not available.");
        }
    }
}

