//package com.example.demo.services;
//
//import com.example.demo.entities.Message;
//import com.example.demo.repositories.MessageRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import java.util.*;
//
//@Service
//public class MessagesService {
//    @Autowired
//    private MessageRepository messageRepository;
//
//    public List<Message> getMessageById(Long user1, Long user2){
//        return messageRepository.getMessagesById(user1, user2);
//    }
//    public void addNewMessages(Long user1ID, Long user2ID, String message){
//        String id;
//        if (user1ID > user2ID)
//            id = user2ID.toString() + "-" + user1ID.toString();
//        else id = user1ID.toString() + "-" + user2ID.toString();
//        if (messageRepository.findByData(id))
//            messageRepository.addMessages(user1ID, user2ID, message);
//        else messageRepository.addNewMessages(user1ID, user2ID, message);
//    }
//    public List<HashMap<String, List<Message>>> getAllList(){
//        return messageRepository.getMessages();
//    }
//
//    public List<Message> getMessage(){
//        return messageRepository.getMessagesOnly();
//    }
//
//    public void deleteMessage(String message) {
//        messageRepository.deleteMessage(message);
//    }
//
//    public void editMessage(String oldMessage, String NewMessage){
//        messageRepository.editMessage(oldMessage, NewMessage);
//    }
//}
