package com.example.messager.repositories;

import com.example.messager.entities.MessageBD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface MessageBDRepository extends JpaRepository<MessageBD, Long> {
    List<MessageBD> findByConversationId(Long conversationId);
}
