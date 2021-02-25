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

package com.karankumar.booksapi.utils;

import com.karankumar.booksapi.exception.InvalidISBN13Exception;
import org.apache.commons.validator.routines.ISBNValidator;

public class Validator {

    public static boolean validateISBN(String isbn) throws InvalidISBN13Exception {
        ISBNValidator validator = new ISBNValidator();
        boolean validISBN = validator.isValidISBN13(isbn);

        if (!validISBN) {
            throw new InvalidISBN13Exception("Invalid ISBN 13");
        } else {
            return true;
        }
    }
}