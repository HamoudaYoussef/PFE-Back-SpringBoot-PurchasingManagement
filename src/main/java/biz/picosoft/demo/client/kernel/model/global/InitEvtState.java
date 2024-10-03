package biz.picosoft.demo.client.kernel.model.global;

import java.util.HashMap;

public class InitEvtState {


    private String name;
    private HashMap<String, String> event = new HashMap<>();
    private HashMap<String, String> state = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, String> getEvent() {
        return event;
    }

    public void setEvent(HashMap<String, String> event) {
        this.event = event;
    }

    public HashMap<String, String> getState() {
        return state;
    }

    public void setState(HashMap<String, String> state) {
        this.state = state;
    }
}
