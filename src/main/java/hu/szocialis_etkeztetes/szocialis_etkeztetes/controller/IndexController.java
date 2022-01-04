package hu.szocialis_etkeztetes.szocialis_etkeztetes.controller;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.PersonalMessage;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.PersonalMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    PersonalMessageService personalMessageService;

    @RequestMapping(path = "index", method = RequestMethod.GET)
    public String getIndex(HttpSession session, Model model) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if(!activeUser.equals("true")){
            return "login";
        }else {
            List<PersonalMessage> callMessages = personalMessageService.getPersonalMessageCalls();
            model.addAttribute("calls", callMessages);
            return "index";
        }
    }
}
