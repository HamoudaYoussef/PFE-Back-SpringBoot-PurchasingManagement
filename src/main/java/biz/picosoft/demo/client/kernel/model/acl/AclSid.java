package biz.picosoft.demo.client.kernel.model.acl;

import java.time.ZonedDateTime;


public class AclSid {


    private Integer id;

    private boolean principal = true;

    private String sid;

    private String htmlCard;

    private String anomalieDescription;

    private ZonedDateTime anomalieDate;

    public AclSid(boolean principal, String sid) {
        this.principal = principal;
        this.sid = sid;

    }

    public AclSid() {
    }

    public String getAnomalieDescription() {
        return anomalieDescription;
    }

    public void setAnomalieDescription(String anomalieDescription) {
        this.anomalieDescription = anomalieDescription;
    }

    public ZonedDateTime getAnomalieDate() {
        return anomalieDate;
    }

    public void setAnomalieDate(ZonedDateTime anomalieDate) {
        this.anomalieDate = anomalieDate;
    }

    public Integer getId() {

        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean getPrincipal() {
        return principal;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getHtmlCard() {
        return htmlCard;
    }

    public void setHtmlCard(String htmlCard) {
        this.htmlCard = htmlCard;
    }
}
