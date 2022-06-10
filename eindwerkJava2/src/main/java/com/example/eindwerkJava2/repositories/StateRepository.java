package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StateRepository extends JpaRepository<State, Long> {

    @Query(value = "select MAX(state_id) AS Max_Id from states",nativeQuery = true)
    Long getLastRecord();

    State findByStateName(String stateName);
}
