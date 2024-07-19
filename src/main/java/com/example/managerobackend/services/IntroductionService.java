package com.example.managerobackend.services;

import com.example.managerobackend.models.Introduction;
import com.example.managerobackend.repositories.IntroductionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntroductionService {
    @Autowired
    private IntroductionRepository repository;

    public List<Introduction> findAll() {
        return repository.findAll();
    }

    public Introduction findById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Introduction save(Introduction introduction) {
        return repository.save(introduction);
    }

    public void deleteById(String id) {
        repository.deleteById(id);
    }
}