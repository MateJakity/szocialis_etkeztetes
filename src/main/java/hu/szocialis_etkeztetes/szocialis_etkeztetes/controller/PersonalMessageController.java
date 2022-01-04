package hu.szocialis_etkeztetes.szocialis_etkeztetes.controller;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.PersonalMessage;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.User;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.PersonalMessageService;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class PersonalMessageController {

    @Autowired
    UserService userService;

    @Autowired
    PersonalMessageService personalMessageService;

    @RequestMapping(path = "/forumHandling", method = RequestMethod.GET)
    private String getForumHandling(HttpSession session, Model model) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        } else {
            User user = userService.findById((Long) session.getAttribute("uid"));
            model.addAttribute("user", user);
            model.addAttribute("messages", personalMessageService.findAll());
            return "forumHandling";
        }
    }

    @RequestMapping(path = "/forumHandling", method = RequestMethod.POST)
    private String postForumMessage(HttpSession session, @RequestParam String sender, @RequestParam String receiver, @RequestParam String messageBody) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        } else {
            if(messageBody.isEmpty()){
                return "redirect:/forumHandling";
            }
            User senderName = userService.findUserName(sender);
            String formatDateTime = personalMessageService.formatDateTime();
            if (senderName.isAdminRoleType()) {
                personalMessageService.saveMessage(new PersonalMessage(senderName.getDisplayName(), receiver, "adminMessage", messageBody, formatDateTime));
            } else {
                personalMessageService.saveMessage(new PersonalMessage(senderName.getDisplayName(), receiver, "all", messageBody, formatDateTime));
            }
            return "redirect:/forumHandling";
        }
    }

    //Üzenetek főoldal
    @RequestMapping(path = "/personalMessageHandling", method = RequestMethod.GET)
    private String getMessageHandling(Model model, HttpSession session) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        } else {
            User user = userService.findById((Long) session.getAttribute("uid"));
            List<PersonalMessage> receivedMessages = personalMessageService.getPersonalMessage(user.getDisplayName(), "received");
            model.addAttribute("messages", receivedMessages);
            return "personalMessageHandling";
        }
    }

    //Bejövő olvasása
    @RequestMapping(path="personalMessageCalls/open", method = RequestMethod.GET)
    private String getPersonalMessageRead(Model model, HttpSession session, @RequestParam long id) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        } else {
            PersonalMessage personalMessage = personalMessageService.findById(id);
            model.addAttribute("message", personalMessage);
            return "personalMessageOpen";
        }
    }

    //Kimenő olvasása
    @RequestMapping(path="personalMessageCalls/openSent", method = RequestMethod.GET)
    private String getPersonalMessageReadSent(Model model, HttpSession session, @RequestParam long id) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        } else {
            PersonalMessage personalMessage = personalMessageService.findById(id);
            model.addAttribute("message", personalMessage);
            return "personalMessageSentOpen";
        }
    }

    //Új üzenet
    @RequestMapping(path = "/personalMessageNew", method = RequestMethod.GET)
    private String getPersonalMessageNew(Model model, HttpSession session){
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        }else{
            model.addAttribute("users", userService.findAll());
            return "personalMessageNew";
        }
    }

    @RequestMapping(path = "personalMessageNew", method = RequestMethod.POST)
    private String postPersonalMessageNew(HttpSession session, @RequestParam String receiver, @RequestParam String subject, @RequestParam String messageBody){
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        }else {
            User user = userService.findById((Long) session.getAttribute("uid"));
            String formatDateTime = personalMessageService.formatDateTime();
            personalMessageService.saveMessage(new PersonalMessage(user.getDisplayName(), receiver, subject, messageBody, formatDateTime));
            return "redirect:/personalMessageSent";
        }
    }

    //Küldött üzenetek
    @RequestMapping(path = "/personalMessageSent", method = RequestMethod.GET)
    private String getPersonalMessageSent(Model model, HttpSession session){
        String activeUser = String.valueOf(session.getAttribute("active"));
        if (!activeUser.equals("true")) {
            return "login";
        }else {
            User user = userService.findById((Long) session.getAttribute("uid"));
            List<PersonalMessage> sentMessages = personalMessageService.getPersonalMessage(user.getDisplayName(), "sent");
            model.addAttribute("messages", sentMessages);
            model.addAttribute("users", userService.findUserName(user.getUserName()));
            return "personalMessageSent";
        }
    }

    //Hirdetmény
    @RequestMapping(path="/personalMessageCalls", method=RequestMethod.GET)
    private String getPersonalMessageCalls(Model model, HttpSession session){
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true")) {
            return "redirect:/index";
        }else{
            User user = userService.findById((Long) session.getAttribute("uid"));
            List<PersonalMessage> callMessages = personalMessageService.getPersonalMessage(user.getDisplayName(), "call");
            model.addAttribute("messages", callMessages);
            return "personalMessageCalls";
        }
    }

    //Hirdetmény
    @RequestMapping(path = "/personalMessageCall", method = RequestMethod.GET)
    private String getPersonalMessageCall(Model model, HttpSession session){
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true")) {
            return "redirect:/index";
        }else{
            return "personalMessageCall";
        }
    }

    @RequestMapping(path = "personalMessageCall", method = RequestMethod.POST)
    private String postPersonalMessageCall(HttpSession session, @RequestParam String receiver, @RequestParam String subject, @RequestParam String messageBody){
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true")) {
            return "redirect:/index";
        }else{
            User user = userService.findById((Long) session.getAttribute("uid"));
            String sender = user.getDisplayName();
            String formatDateTime = personalMessageService.formatDateTime();
            personalMessageService.saveMessage(new PersonalMessage(sender, receiver, subject, messageBody, formatDateTime));
            return "redirect:/personalMessageCalls";
        }
    }

    //Hirdetmény törlése
    @RequestMapping(path = "personalMessageCalls/delete", method = RequestMethod.GET)
    public String getPersonalMessageCallDelete(@RequestParam long id, HttpSession session) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        }else if(!adminRoleType.equals("true")) {
            return "redirect:/index";
        }else{
            personalMessageService.deleteByMessageId(id);
            return "redirect:/personalMessageCalls";
        }
    }

    //Hirdetmény Módosítása
    @RequestMapping(path = "personalMessageCalls/personalMessageCallsUpdate", method = RequestMethod.GET)
    private String getPersonalMessageCallsUpdate(@RequestParam long id, HttpSession session, Model model){
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true")) {
            return "redirect:/index";
        }else{
            PersonalMessage personalMessageUpdate = personalMessageService.findById(id);
            model.addAttribute("call", personalMessageUpdate);
            return "personalMessageCallsUpdate";
        }
    }

    @RequestMapping(path = "personalMessageCalls/personalMessageCallsUpdate", method = RequestMethod.POST)
    private String postPersonalMessageCallsUpdate(HttpSession session, @RequestParam long id, @RequestParam String messageBody){
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true")) {
            return "redirect:/index";
        }else{
            PersonalMessage personalMessageCall = personalMessageService.findById(id);
            personalMessageCall.setMessageBody(messageBody);
            personalMessageService.saveMessage(personalMessageCall);
            return "redirect:/personalMessageCalls";
        }
    }

}
