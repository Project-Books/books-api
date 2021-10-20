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