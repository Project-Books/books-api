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

package com.karankumar.booksapi.model.enums;

public enum Language {
  
    AFRIKAANS("Afrikaans"),
    ALBANIAN("Albanian"),
    ARABIC("Arabic"),
    ARMENIAN("Armenian"),
    BENGALI("Bengali"),
    BULGARIAN("Bulgarian"),
    CHINESE("Chinese"),
    CZECH("Czech"),
    DANISH("Danish"),
    DUTCH("Dutch"),
    ENGLISH("English"),
    ESTONIAN("Estonian"),
    FINNISH("Finnish"),
    FRENCH("French"),
    GERMAN("German"),
    GREEK("Greek"),
    GUJARATI("Gujarati"),
    ITALIAN("Italian"),
    HAWAIIAN("Hawaiian"),
    HEBREW("Hebrew"),
    HINDI("Hindi"),
    JAPANESE("Japanese"),
    KOREAN("Korean"),
    LATIN("Latin"),
    POLISH("Polish"),
    PORTUGUESE("Portuguese"),
    PUNJABI("Punjabi"),
    RUSSIAN("Russian"),
    SANSKRIT("Sanskrit"),
    SERBIAN("Serbian"),
    SINDHI("Sindhi"),
    SPANISH("Spanish"),
    SWAHILI("Swahili"),
    SWEDISH("Swedish"),
    TAMIL("Tamil"),
    TELEGU("Telegu"),
    THAI("Thai"),
    TURKISH("Turkish"),
    URDU("Urdu"),
    WELSH("Welsh");
      
    private final String lang;
    
    Language(String lang) {
        this.lang = lang;
    }
    
    @Override
    public String toString() {
        return lang;
    }

}
