//package com.example.demo.repositories;
//
//
//import com.example.demo.entities.Message;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.*;
//
//@Component
//public class MessageRepository {
//    private List<HashMap<String, List<Message>>> messages; //все переписки
//
//    @PostConstruct
//    public void init() {
//        messages = new ArrayList<>();
//        List<Message> listMessage = new ArrayList<>(); //переписка одного с другим
//        HashMap<String, List<Message>> mes = new HashMap<>(); //переписка одного с другим по айди
//    }
//    public List<Message> getMessagesById(Long idUser1, Long idUser2){
//        String id;
//        if (idUser1 > idUser2)
//            id = idUser2.toString() + "-" + idUser1.toString();
//        else id = idUser1.toString() + "-" + idUser2.toString();
//        List<Message> mes = new ArrayList<>();
//        for (HashMap<String, List<Message>> map : messages) {
//            if (map.containsKey(id)) {
//                mes = map.get(id);
//                break;
//            }
//        }
//        return mes;
//    }
//
//    public Boolean findByData(String id){
//        for (HashMap<String, List<Message>> map : messages) {
//            if (map.containsKey(id)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void addNewMessages(Long idUser1, Long idUser2, String message) {
//        String id;
//        if (idUser1 > idUser2)
//            id = idUser2.toString() + "-" + idUser1.toString();
//        else id = idUser1.toString() + "-" + idUser2.toString();
//        HashMap<String, List<Message>> mes = new HashMap<>();
//        mes.put(id, new ArrayList<>(Arrays.asList(new Message(idUser1.toString(), message))));
//        messages.add(mes);
//    }
//
//    public void addMessages(Long idUser1, Long idUser2, String message){
//        String id;
//        if (idUser1 > idUser2)
//            id = idUser2.toString() + "-" + idUser1.toString();
//        else id = idUser1.toString() + "-" + idUser2.toString();
//        for (HashMap<String, List<Message>> map : messages) {
//            if (map.containsKey(id)) {
//                List<Message> list = map.get(id);
//                list.add(new Message(idUser1.toString(), message));
//                map.put(id, list);
//                break;
//            }
//        }
//    }
//
//    public List<HashMap<String, List<Message>>> getMessages() {
//        return messages;
//    }
//
//    public void setMessages(List<HashMap<String, List<Message>>> messages) {
//        this.messages = messages;
//    }
//
//    public List<Message> getMessagesOnly(){
//        List<Message> mes = new ArrayList<>();
//        for (HashMap<String, List<Message>> map : messages) {
//            for (List<Message> messageList : map.values()) {
//                mes.addAll(messageList);
//            }
//        }
//        return mes;
//    }
//
//    public void deleteMessage(String message){
//        for (HashMap<String, List<Message>> map : messages) {
//            for (List<Message> messageList : map.values()) {
//                for (Iterator<Message> iterator = messageList.iterator(); iterator.hasNext();) {
//                    Message msg = iterator.next();
//                    if (msg.getText().equals(message)) {
//                        iterator.remove();
//                        break;
//                    }
//                }
//            }
//        }
//    }
//
//    public void editMessage(String oldMessage, String newMessage) {
//        for (HashMap<String, List<Message>> map : messages) {
//            for (List<Message> messageList : map.values()) {
//                for (Message message : messageList) {
//                    if (message.getText().equals(oldMessage)) {
//                        message.setText(newMessage);
//                        break;
//                    }
//                }
//            }
//        }
//    }
//}