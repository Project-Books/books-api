package com.karankumar.booksapi.service;

import com.karankumar.booksapi.model.award.Award;
import com.karankumar.booksapi.repository.AwardRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AwardService {
    private final AwardRepository awardRepository;

    public AwardService(AwardRepository awardRepository) {
        this.awardRepository = awardRepository;
    }

    public Award save(@NonNull Award author) {
        return awardRepository.save(author);
    }

    public Optional<Award> findById(@NonNull Long id) {
        return awardRepository.findById(id);
    }

    public List<Award> findAll() {
        return awardRepository.findAllAwards();
    }

}
