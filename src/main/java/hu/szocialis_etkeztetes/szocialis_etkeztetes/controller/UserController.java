package hu.szocialis_etkeztetes.szocialis_etkeztetes.controller;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.User;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    //Alkalmazottak kezelése főoldal
    @RequestMapping(path = "userHandling", method = RequestMethod.GET)
    public String getUser(Model model, HttpSession session) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        String adminRoleType = String.valueOf(session.getAttribute("adminRoleType"));
        if (!activeUser.equals("true")) {
            return "login";
        } else if(!adminRoleType.equals("true")) {
            return "redirect:/index";
        }else {
            model.addAttribute("users", userService.findAll());
            return "userHandling";
        }
    }

    //Alkalmazott törlése
    @RequestMapping(path = "userHandling/userDelete", method = RequestMethod.GET)
    public String getUserDelete(@RequestParam long id, HttpSession session) {
        long userId = (Long) session.getAttribute("uid");
        User user = userService.findById(userId);
        boolean check = user.isAdminRoleType();

        if (check && user.getId() != id) {
            userService.deleteByUserId(id);
            return "redirect:/userHandling";
        } else {
            return "redirect:/userHandling";
        }
    }

    //Alkalmazott módosítása és saját beállítások módosítása
    @RequestMapping(path = "userHandling/userUpdate", method = RequestMethod.GET)
    public String getUserUpdate(Model model, HttpSession session, @RequestParam long id) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        if(!activeUser.equals("true")){
            return "login";
        }else {
            User user = userService.findById(id);
            model.addAttribute("user", user);
            return "userUpdate";
        }
    }

    @RequestMapping(path = "userHandling/userUpdate", method = RequestMethod.POST)
    public String postUserUpdate(HttpSession session, @RequestParam long id, @RequestParam String userName, @RequestParam String displayName, @RequestParam String password,
                             @RequestParam String roleName, @RequestParam boolean adminRoleType, @RequestParam boolean chefRoleType, @RequestParam boolean warehouseRoleType, @RequestParam boolean active) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        User user = userService.findById((Long)session.getAttribute("uid"));
        if(!activeUser.equals("true")){
            return "login";
        }else {
            User userExists = userService.findUserName(userName);
            if(userExists != null && userExists.getId() != id){
                return "redirect:/userUpdate";
            }
            boolean userUpdateSuccess = userService.userUpdate(id, userName, displayName, password, roleName, adminRoleType, chefRoleType, warehouseRoleType, active);
            if(userUpdateSuccess){
                if(id == user.getId()) {
                    session.removeAttribute("adminRoleType");
                    session.removeAttribute("chefRoleType");
                    session.removeAttribute("warehouseRoleType");
                    session.removeAttribute("active");
                    session.setAttribute("adminRoleType", user.isAdminRoleType());
                    session.setAttribute("chefroleType", user.isChefRoleType());
                    session.setAttribute("chefroleType", user.isWarehouseRoleType());
                    session.setAttribute("active", user.isActive());
                }
            }
            if(user.isAdminRoleType()){
                return "redirect:/" +
                        "userHandling";
            }else{
                return "redirect:/index";
            }
        }
    }

    //Sidebar link
    //Új alkalmazott regisztrációja
    @RequestMapping(path = "userNew", method = RequestMethod.GET)
    public String getNewUser(HttpSession session) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        User user = userService.findById((Long)session.getAttribute("uid"));
        if(!activeUser.equals("true")){
            return "login";
        }else if(user.isAdminRoleType()){
            return "userNew";
        }
        return "redirect:/userHandling";
    }

    @RequestMapping(path = "userNew", method = RequestMethod.POST)
    public String postNewUser(HttpSession session, @RequestParam String userName, @RequestParam String displayName, @RequestParam String password,
                              @RequestParam String roleName, @RequestParam boolean adminRoleType, @RequestParam boolean chefRoleType, @RequestParam boolean warehouseRoleType, @RequestParam boolean active) {
        String activeUser = String.valueOf(session.getAttribute("active"));
        User user = userService.findById((Long)session.getAttribute("uid"));
        if(!activeUser.equals("true")){
            return "login";
        }else if(user.isAdminRoleType()){
            for (User users: userService.findAll()) {
                if (users.getUserName().equalsIgnoreCase(userName)){
                    return "redirect:/userNew";
                }
            }
            userService.saveUser(new User(userName, displayName, password, roleName, adminRoleType, chefRoleType, warehouseRoleType,active));
        }
        return "redirect:/userHandling";
        }
}
