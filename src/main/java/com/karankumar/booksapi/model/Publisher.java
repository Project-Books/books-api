/*
 * Copyright (C) 2020  Karan Kumar
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.  See the GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package com.karankumar.booksapi.model;

/**
 * The publisher name represents the name displayed on the book (not necessarily the name of the
 * company). This can also include imprints
 */
public enum Publisher {
    // This should be kept in alphabetical order
    ALLEN_LANE("Allen Lane"),
    AMISTAD_PRESS("Amistad Press"),
    BLOOMSBURY("Bloomsbury"),
    CAMBRIDGE_UNIVERSITY_PRESS("Cambridge University Press"),
    DK("DK"),
    GRANTA("Granta"),
    HARLEQUIN_ENTERPRISES("Harlequin Enterprises"),
    HARPER_COLLINS("HarperCollins"),
    HARPER_ONE("HarperOne"),
    LITTLE_BROWN("Little, Brown"),
    MICHAEL_JOSEPH("Michael Joseph"),
    OXFORD_UNIVERSITY_PRESS("Oxford University Press"),
    PELICAN_BOOKS("Pelican Books"),
    PENGUIN_BOOKS("Penguin Books"),
    PRENTICE_HALL("Prentice Hall"),
    VINTAGE("Vintage"),
    WH_ALLEN("WH Allen");

    private final String name;

    Publisher(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
