package biz.picosoft.demo.client.kernel.model.objects;

import java.util.ArrayList;
import java.util.List;

public class EventsDetails {

    private List<EventObjectDto> events = new ArrayList<>();
    private List<EmailDetails> emailNotif = new ArrayList<>();
    private List<EmailDetails> emailOut = new ArrayList<>();
    private List<EmailDetails> emailIn = new ArrayList<>();

    public List<EventObjectDto> getEvents() {
        return events;
    }

    public void setEvents(List<EventObjectDto> events) {
        this.events = events;
    }

    public List<EmailDetails> getEmailNotif() {
        return emailNotif;
    }

    public void setEmailNotif(List<EmailDetails> emailNotif) {
        this.emailNotif = emailNotif;
    }

    public List<EmailDetails> getEmailOut() {
        return emailOut;
    }

    public void setEmailOut(List<EmailDetails> emailOut) {
        this.emailOut = emailOut;
    }

    public List<EmailDetails> getEmailIn() {
        return emailIn;
    }

    public void setEmailIn(List<EmailDetails> emailIn) {
        this.emailIn = emailIn;
    }
}
