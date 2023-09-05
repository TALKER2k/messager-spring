package com.example.messager.controllers;

import com.example.messager.entities.MessageBD;
import com.example.messager.entities.User;
import com.example.messager.services.MessagesBDService;
import com.example.messager.services.UsersService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MessageController {

    private final UsersService userService;

    private final MessagesBDService messagesBDService;

    public MessageController(UsersService userService, MessagesBDService messagesBDService) {
        this.userService = userService;
        this.messagesBDService = messagesBDService;
    }

    @GetMapping()
    public String index() {
        return "index";
    }

    @GetMapping("/sendmessage")
    public String sendMessage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("userid", user.getId());
        return "sendmessage";
    }

    @GetMapping("/sendmessage/{userid}")
    public String sendMessageForUser(@PathVariable(value = "userid") Long userid,
                                     @AuthenticationPrincipal User user, Model model) {
        model.addAttribute("userid", userid);
        model.addAttribute("userMain", user.getId().toString());
        model.addAttribute("messageList", messagesBDService.getMessageById(user, userid));
        return "messager";
    }

    @PostMapping("/sendmessage/{userid}")
    public String sendMessageFor(@PathVariable(value = "userid") Long userid,
                                 @RequestParam(value = "message") String message,
                                 @AuthenticationPrincipal User user) {
        messagesBDService.addMessage(user, userid, message);
        return "redirect:/sendmessage/" + userid;
    }

    @GetMapping("/download")
    public void downloadMessage(@RequestParam("message") String message,
                                HttpServletResponse response) throws IOException {
        String csvData = generateCsvData(message);
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=message.csv");
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(csvData.getBytes());
            outputStream.flush();
        }
    }

    @GetMapping("/downloadAll")
    public void downloadAllMessages(@AuthenticationPrincipal User userSender, HttpServletResponse response,
                                    @RequestParam("userid") Long userid) throws IOException {
        StringBuilder csvData = new StringBuilder();
        List<MessageBD> messages = messagesBDService.getAllList(userSender, userid);
        for (MessageBD mes : messages) {
            csvData.append("id-").append(mes.getSender().getId()).append(" : ").append(mes.getMessageText()).append("\n");
        }
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=all_messages.csv");

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(csvData.toString().getBytes());
            outputStream.flush();
        }
    }

    @GetMapping("/delete")
    public String deleteMessage(@RequestParam("messageId") Long messageId,
                                @RequestParam("userid") Long userid) {
        messagesBDService.deleteMessage(messageId);
        return "redirect:/sendmessage/" + userid;
    }

    @GetMapping("/editMessage")
    public String editMessagePage(@RequestParam("messageId") Long messageId,
                                  @RequestParam("userid") Long userid,
                                  Model model) {
        model.addAttribute("oldMessage", messagesBDService.findMessegeById(messageId).getMessageText());
        model.addAttribute("messageId", messageId);
        model.addAttribute("userid", userid);
        System.out.println(messageId + "  " + userid);
        return "editMessage";
    }

    @PostMapping("/edit")
    public String edit(@RequestParam("newMessage") String newMessage,
                       @RequestParam("messageId") Long messageId,
                       @RequestParam("userid") Long userid) {
        messagesBDService.editMessage(newMessage, messageId);
        return "redirect:/sendmessage/" + userid;
    }

    private String generateCsvData(String message) {
        return "message\r\n" + message.substring(message.indexOf(":") + 1).trim();
    }

}
