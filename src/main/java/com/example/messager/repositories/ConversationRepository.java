package com.example.messager.repositories;

import com.example.messager.entities.Conversation;
import com.example.messager.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findByFirstId(Long firstId);

    List<Conversation> findBySecondId(Long secondId);

    List<Conversation> findByFirstIdAndSecondId(User firstUser, User secondUser);

    void deleteByFirstIdAndSecondId(User firstUser, User secondUser);
}
