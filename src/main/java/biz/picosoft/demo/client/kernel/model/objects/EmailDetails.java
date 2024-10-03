package biz.picosoft.demo.client.kernel.model.objects;

public class EmailDetails {

    private String from;
    private String sender;
    private String sendto;
    private String cc;
    private String bcc;
    private String subject;
    private Integer displayedNb;
    private Long emlId;
    private String check;

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }

    public Integer getDisplayedNb() {
        return displayedNb;
    }

    public void setDisplayedNb(Integer displayedNb) {
        this.displayedNb = displayedNb;
    }

    public Long getEmlId() {
        return emlId;
    }

    public void setEmlId(Long emlId) {
        this.emlId = emlId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSendto() {
        return sendto;
    }

    public void setSendto(String sendto) {
        this.sendto = sendto;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
