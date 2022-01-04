package hu.szocialis_etkeztetes.szocialis_etkeztetes.controller;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.User;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.repository.UserRepository;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(path= " ", method = RequestMethod.GET)
    public String getLoginPage(HttpSession session) {
        session.removeAttribute("adminRoleType");
        session.removeAttribute("chefRoleType");
        session.removeAttribute("warehouseRoleType");
        session.removeAttribute("active");
        session.removeAttribute("uid");
        return "login";
    }

    @RequestMapping(path="index", method = RequestMethod.POST)
    public String postLoginPage(@RequestParam String userName, @RequestParam String password, HttpSession session){
        if((userName != null && !userName.isEmpty()) && (password != null && !password.isEmpty())){
            User user = userService.findUserName(userName);
                if(user != null && user.getPassword().equals(password)){
                    session.setAttribute("adminRoleType", user.isAdminRoleType());
                    session.setAttribute("chefRoleType", user.isChefRoleType());
                    session.setAttribute("warehouseRoleType", user.isWarehouseRoleType());
                    session.setAttribute("active", user.isActive());
                    session.setAttribute("uid", user.getId());
                    String activeUser = String.valueOf(session.getAttribute("active"));
                    if(activeUser.equals("false")){
                        session.removeAttribute("adminRoleType");
                        session.removeAttribute("chefRoleType");
                        session.removeAttribute("warehouseRoleType");
                        session.removeAttribute("active");
                        session.removeAttribute("uid");
                        return "redirect:/";
                    }
                    return "redirect:/index";
                }else{
                    return "redirect:/";
                }

            }else {
                return "redirect:/";
            }
        }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String getLogout(HttpSession session){
        session.removeAttribute("adminRoleType");
        session.removeAttribute("chefRoleType");
        session.removeAttribute("warehouseRoleType");
        session.removeAttribute("active");
        session.removeAttribute("uid");
        return "login";
    }
}
