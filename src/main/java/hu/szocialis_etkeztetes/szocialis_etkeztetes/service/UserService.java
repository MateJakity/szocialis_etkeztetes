package hu.szocialis_etkeztetes.szocialis_etkeztetes.service;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.PersonalMessage;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.User;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PersonalMessageService personalMessageService;

    public List<User> findAll(){return userRepository.findAll();}

    public User findUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public User findById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user){return userRepository.save(user);}

    public void deleteByUserId(long id){userRepository.deleteById(id);}

    public boolean userUpdate(long id, String userName, String displayName, String password,
                           String roleName, boolean adminRoleType, boolean chefRoleType, boolean warehouseRoleType, boolean active){

        User user = findById(id);
        for (User users: findAll()) {
            if(users.getUserName().equalsIgnoreCase(userName) && users.getId() != id){
                return false;
            }
        }

        if(user != null) {
            user.setUserName(userName);
            user.setDisplayName(displayName);
            user.setPassword(password);
            user.setRoleName(roleName);
            user.setAdminRoleType(adminRoleType);
            user.setChefRoleType(chefRoleType);
            user.setWarehouseRoleType(warehouseRoleType);
            user.setActive(active);
            saveUser(user);
            for (PersonalMessage message: personalMessageService.findAll()) {
                if(message.getSender().equalsIgnoreCase(userName)){
                    message.setSender(userName);
                    personalMessageService.saveMessage(message);
                }else if(message.getReceiver().equalsIgnoreCase(userName)){
                    message.setReceiver(userName);
                    personalMessageService.saveMessage(message);
                    message.setReceiver(userName);
                }else if(message.getReceiver().equalsIgnoreCase(userName) && message.getSender().equalsIgnoreCase(userName)){
                    message.setReceiver(userName);
                    message.setSender(userName);
                    personalMessageService.saveMessage(message);
                }
            }
            return true;
        }else{
            return false;
        }

    }

}
