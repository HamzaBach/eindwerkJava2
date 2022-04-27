package com.example.eindwerkJava2.repositories;

import com.example.eindwerkJava2.model.OrderReceive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderReceiveRepository extends JpaRepository<OrderReceive, Long> {
}
