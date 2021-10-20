/*
 * Copyright (C) 2021  Karan Kumar
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

package com.karankumar.booksapi.mutations;

import com.acme.DgsConstants;
import com.karankumar.booksapi.model.award.Award;
import com.karankumar.booksapi.model.award.AwardName;
import com.karankumar.booksapi.service.AwardService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;

import java.util.HashSet;


@DgsComponent
public class AwardMutation {
    private final AwardService awardService;

    public AwardMutation(AwardService awardService) {
        this.awardService = awardService;
    }

    @DgsData(parentType = DgsConstants.MUTATION.TYPE_NAME, field = DgsConstants.MUTATION.AddAward)
    public Award addAward(DataFetchingEnvironment dataFetchingEnvironment) {
        AwardName awardName = dataFetchingEnvironment.getArgument(DgsConstants.AWARD.AwardName);
        String category = dataFetchingEnvironment.getArgument(DgsConstants.AWARD.Category);
        int year = dataFetchingEnvironment.getArgument(DgsConstants.AWARD.Year);
        return awardService.save(new Award(awardName, category, year, new HashSet<>()));
    }
}