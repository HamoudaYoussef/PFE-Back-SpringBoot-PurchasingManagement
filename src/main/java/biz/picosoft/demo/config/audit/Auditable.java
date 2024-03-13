package biz.picosoft.demo.config.audit;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.ZonedDateTime;

@MappedSuperclass
@Table(schema = "audit")
public class Auditable {


    @Column(name = "sysdate_created",updatable =  false)
    @CreationTimestamp
    private ZonedDateTime sysdateCreated;

    @Size(min = 0, max = 50)
    @Column(name = "syscreated_by", length =50 ,updatable =  false)
    @CreatedBy
    private String syscreatedBy ;

    @Column(name = "sysdate_updated")
       @UpdateTimestamp
    private ZonedDateTime sysdateUpdated;

    @Size(min = 0, max = 50)
    @Column(name = "sysupdated_by", length = 50)
    @LastModifiedBy
    private String sysupdatedBy;


    public ZonedDateTime getSysdateCreated() {
        return sysdateCreated;
    }
    public void setSysdateCreated(ZonedDateTime sysdateCreated) {
        this.sysdateCreated = sysdateCreated;
    }


    public String getSyscreatedBy() {
        return syscreatedBy;
    }

    public void setSyscreatedBy(String syscreatedBy) {
        this.syscreatedBy = syscreatedBy;
    }

    public String getSysupdatedBy() {
        return sysupdatedBy;
    }

    public void setSysupdatedBy(String sysupdatedBy) {
        this.sysupdatedBy = sysupdatedBy;
    }

    public ZonedDateTime getSysdateUpdated() {
        return sysdateUpdated;
    }
    public void setSysdateUpdated(ZonedDateTime sysdateUpdated) {
        this.sysdateUpdated = sysdateUpdated;
    }


}
