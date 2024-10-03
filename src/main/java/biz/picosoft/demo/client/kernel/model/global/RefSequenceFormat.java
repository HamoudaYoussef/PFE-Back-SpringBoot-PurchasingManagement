package biz.picosoft.demo.client.kernel.model.global;


import biz.picosoft.demo.config.audit.Auditable;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A RefSequenceFormat.
 */

public class RefSequenceFormat extends Auditable implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String description;

    private String format;

    private Long counter;

    private Long limite;

    private Integer step;

    private ResetInterval resetInterval;

    private ZonedDateTime CounterDate;

    private Boolean sysdeleted = false;

    private Integer checkDigit;

    public Integer getCheckDigit() {

        return checkDigit;
    }


    public void setCheckDigit(Integer checkDigit) {
        this.checkDigit = checkDigit;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RefSequenceFormat name(String name) {
        this.name = name;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public RefSequenceFormat format(String format) {
        this.format = format;
        return this;
    }

    /**
     * @return the limite
     */
    public Long getLimite() {
        return limite;
    }

    /**
     * @param limite the limite to set
     */
    public void setLimite(Long limite) {
        this.limite = limite;
    }

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }

    public RefSequenceFormat counter(Long counter) {
        this.counter = counter;
        return this;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public RefSequenceFormat step(Integer step) {
        this.step = step;
        return this;
    }

    public Boolean isSysdeleted() {
        return sysdeleted;
    }

    public RefSequenceFormat sysdeleted(Boolean sysdeleted) {
        this.sysdeleted = sysdeleted;
        return this;
    }

    public void setSysdeleted(Boolean sysdeleted) {
        this.sysdeleted = sysdeleted;
    }

    public ResetInterval getResetInterval() {
        return resetInterval;
    }

    public void setResetInterval(ResetInterval resetInterval) {
        this.resetInterval = resetInterval;
    }

    public String getdescription() {
        return description;
    }

    public void setdescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getCounterDate() {
        return CounterDate;
    }

    public void setCounterDate(ZonedDateTime counterDate) {
        CounterDate = counterDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RefSequenceFormat)) {
            return false;
        }
        return id != null && id.equals(((RefSequenceFormat) o).id);
    }

    @Override
    public String toString() {
        return "RefSequenceFormat{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", format='" + getFormat() + "'" +
                ", counter=" + getCounter() +
                ", step=" + getStep() +
                ", sysdateCreated='" + getSysdateCreated() + "'" +
                ", sysdateUpdated='" + getSysdateUpdated() + "'" +
                ", syscreatedBy='" + getSyscreatedBy() + "'" +
                ", sysupdatedBy='" + getSysupdatedBy() + "'" +
                ", sysdeleted='" + isSysdeleted() + "'" +
                "}";
    }
}
