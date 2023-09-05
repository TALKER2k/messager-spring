package com.example.messager.services;

import com.example.messager.entities.Conversation;
import com.example.messager.entities.MessageBD;
import com.example.messager.entities.User;
import com.example.messager.repositories.ConversationRepository;
import com.example.messager.repositories.MessageBDRepository;
import com.example.messager.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.time.LocalDateTime;

@Service
public class MessagesBDService {
    private final MessageBDRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public MessagesBDService(MessageBDRepository messageRepository, ConversationRepository conversationRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    public void addMessage(User senderUser, Long recipientId, String message) {
        if (message == null || message.isEmpty())
            return;
        Optional<User> recipientOptional = userRepository.findById(recipientId);
        if (recipientOptional.isPresent()) {
            User recipientUser = recipientOptional.get();
            List<Conversation> conversationList;
            if (senderUser.getId() > recipientUser.getId()) {
                conversationList = conversationRepository
                        .findByFirstIdAndSecondId(recipientUser, senderUser);
            } else conversationList = conversationRepository
                    .findByFirstIdAndSecondId(senderUser, recipientUser);

            if (conversationList.isEmpty()) {
                Conversation newConversation = new Conversation();
                newConversation.setFirstId(senderUser);
                newConversation.setSecondId(recipientUser);
                conversationRepository.save(newConversation);
                conversationList.add(newConversation);
            }

            Conversation conversation = conversationList.get(0);

            MessageBD newMessage = new MessageBD();
            newMessage.setConversation(conversation);
            newMessage.setSender(senderUser);
            newMessage.setMessageText(message);
            newMessage.setSentAt(LocalDateTime.now());
            messageRepository.save(newMessage);
        } else {
            throw new IllegalArgumentException("Recipient user not found");
        }
    }


    public List<MessageBD> getMessageById(User senderUser, Long recipientId) {
        Optional<User> userOptional = userRepository.findById(recipientId);

        if (userOptional.isPresent()) {
            User recipientUser = userOptional.get();
            if (senderUser.getId() > recipientUser.getId()) {
                User temp = senderUser;
                senderUser = recipientUser;
                recipientUser = temp;
            }
            List<Conversation> conversations = conversationRepository
                    .findByFirstIdAndSecondId(senderUser, recipientUser);

            if (conversations.isEmpty()) {
                messageRepository.save(new MessageBD());
                return new ArrayList<>();
            } else {
                Long conversationId = conversations.get(0).getId();
                List<MessageBD> messages = messageRepository.findByConversationId(conversationId);
                Collections.sort(messages, Comparator.comparingLong(MessageBD::getId));
                return messages;
            }
        } else {
            return new ArrayList<>();
        }
    }

    public List<MessageBD> getAllList(User userSender, Long userRecipientId) {
        User userRecipient = userRepository.findById(userRecipientId).get();
        if (userSender.getId() > userRecipient.getId()) {
            User temp = userSender;
            userSender = userRecipient;
            userRecipient = temp;
        }
        return messageRepository.findByConversationId(conversationRepository.findByFirstIdAndSecondId(userSender, userRecipient).get(0).getId());
    }

    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }

    public void editMessage(String NewText, Long messageId) {
        messageRepository.findById(messageId).get().setMessageText(NewText);
        messageRepository.save(messageRepository.findById(messageId).get());
    }

    public MessageBD findMessegeById(Long id) {
        return messageRepository.findById(id).get();
    }
}
