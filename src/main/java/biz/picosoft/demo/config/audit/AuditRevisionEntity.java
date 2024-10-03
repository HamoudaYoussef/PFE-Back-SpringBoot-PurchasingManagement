package biz.picosoft.demo.config.audit;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;

@Entity
@Table(name = "aud_revision_info", schema = "audit")
@RevisionEntity(AuditRevisionListener.class)
@AttributeOverrides({
        @AttributeOverride(name = "timestamp", column = @Column(name = "rev_timestamp")),
        @AttributeOverride(name = "id", column = @Column(name = "revision_id"))
})
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "usr")
    private String usr;

    @Column(name = "ip_adress", length = 32)
    private String ipAdr;

    @Column(name = "uuid", length = 60) //example : 1df7ffb2-3570-11eb-adc1-0242ac120002
    private String uuid;

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) { this.usr = usr;
    }

    public String getIpAdr() {
        return ipAdr;
    }

    public void setIpAdr(String ipAdr) {
        this.ipAdr = ipAdr;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "{" +
                "usr='" + usr + '\'' +
                ", uuid='" + uuid + '\'' +
                ",id='" + super.getId() + '\'' +
                ",revisionDate='" + super.getRevisionDate() + '\'' +
                '}';
    }
}
