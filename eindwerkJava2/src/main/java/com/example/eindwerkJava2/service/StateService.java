package com.example.eindwerkJava2.service;

import com.example.eindwerkJava2.model.State;
import com.example.eindwerkJava2.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StateService {
    @Autowired
    StateRepository stateRepository;

    public StateService(StateRepository stateRepository){
        this.stateRepository=stateRepository;
    }

    public void save(State state){
        stateRepository.save(state);
    }
    public State getLastRecord(){
        return stateRepository.findById(stateRepository.getLastRecord()).get();
    }

    public State findByStateName(String stateName) {
        return stateRepository.findByStateName(stateName);
    }
    public State findById(long stateId){
        return stateRepository.findById(stateId).get();
    }
}
