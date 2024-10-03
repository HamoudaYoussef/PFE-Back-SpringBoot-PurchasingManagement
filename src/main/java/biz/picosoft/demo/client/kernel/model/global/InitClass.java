package biz.picosoft.demo.client.kernel.model.global;

import java.io.Serializable;
import java.util.HashMap;


public class InitClass implements Serializable {

    private String defaultState;
    private String simpleName;
    private String name;
    private String sequenceNameFM;
    private String packagename;
    private HashMap<String, String> event = new HashMap<>();
    private HashMap<String, String> state = new HashMap<>();

    public String getDefaultState() {
        return defaultState;
    }

    public void setDefaultState(String defaultState) {
        this.defaultState = defaultState;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

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

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename;
    }

    public String getSequenceNameFM() {
        return sequenceNameFM;
    }

    public void setSequenceNameFM(String sequenceNameFM) {
        this.sequenceNameFM = sequenceNameFM;
    }
}
