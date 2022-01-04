package hu.szocialis_etkeztetes.szocialis_etkeztetes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class PersonalMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long id;

    private String sender;
    private String receiver;
    private String subject;
    private String messageBody;
    private String formatDateTime;

    public PersonalMessage() {
    }

    public PersonalMessage(String sender, String receiver, String subject, String messageBody, String formatDateTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.subject = subject;
        this.messageBody = messageBody;
        this.formatDateTime = formatDateTime;
    }

    public long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public String getFormatDateTime() {
        return formatDateTime;
    }

    public void setFormatDateTime(String formatDateTime) {
        this.formatDateTime = formatDateTime;
    }
}
