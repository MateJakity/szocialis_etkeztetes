package hu.szocialis_etkeztetes.szocialis_etkeztetes.service;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.PersonalMessage;
import hu.szocialis_etkeztetes.szocialis_etkeztetes.repository.PersonalMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonalMessageService {


    @Autowired
    private PersonalMessageRepository personalMessageRepository;

    public List<PersonalMessage> findAll() {
        return personalMessageRepository.findAll();
    }

    public PersonalMessage findMessage(String subject) {
        return personalMessageRepository.findBySubject(subject);
    }

    public PersonalMessage findById(long id) {
        return personalMessageRepository.findById(id).orElse(null);
    }

    public PersonalMessage saveMessage(PersonalMessage personalMessage) {
        return personalMessageRepository.save(personalMessage);
    }

    public void deleteByMessageId(long id) {
        personalMessageRepository.deleteById(id);
    }

    public String formatDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return now.format(formatter);
    }

    public List<PersonalMessage> getPersonalMessageCalls() {
        List<PersonalMessage> calls = new ArrayList<>();

        for (PersonalMessage call : findAll()) {
            if (call.getSubject().equals("call") && call.getReceiver().equals("everyone")) {
                calls.add(call);
            }
        }
        return calls;
    }

    public List<PersonalMessage> getPersonalMessage(String displayName, String state) {
        List<PersonalMessage> messagesAll = findAll();
        List<PersonalMessage> messages = new ArrayList<>();
        int i;
        for(i = messagesAll.size() - 1; i >= 0; i--){
            if(state.equals("received")){
                if(messagesAll.get(i).getReceiver().equals(displayName))
                messages.add(messagesAll.get(i));
            }else if(state.equals("sent")){
                if(messagesAll.get(i).getSender().equals(displayName)){
                    if(!messagesAll.get(i).getSubject().equals("adminMessage") && !messagesAll.get(i).getSubject().equals("call")) {
                        messages.add(messagesAll.get(i));
                    }
                }
            }else if(state.equals("call")) {
                if(messagesAll.get(i).getReceiver().equals("everyone") && messagesAll.get(i).getSubject().equals("call")){
                    messages.add(messagesAll.get(i));
                }
            }
        }
        return messages;
    }
}
