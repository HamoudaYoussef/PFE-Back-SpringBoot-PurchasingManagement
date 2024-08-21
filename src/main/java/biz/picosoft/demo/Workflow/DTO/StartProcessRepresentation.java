package biz.picosoft.demo.Workflow.DTO;

public class StartProcessRepresentation {

    private String initiator;
    private String processKey;


    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public String getProcessKey() {
        return processKey;
    }

    public void setProcessKey(String processKey) {
        this.processKey = processKey;
    }
}
