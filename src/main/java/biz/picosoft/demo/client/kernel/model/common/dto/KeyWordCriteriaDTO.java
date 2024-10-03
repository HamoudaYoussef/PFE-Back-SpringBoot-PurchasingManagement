package biz.picosoft.demo.client.kernel.model.common.dto;


import biz.picosoft.demo.client.kernel.model.common.criteria.KeyWordCriteria;
import org.springframework.data.domain.Pageable;

public class KeyWordCriteriaDTO {
    private KeyWordCriteria criteria;
    private Pageable pageable;

    public KeyWordCriteria getCriteria() {
        return criteria;
    }

    public void setCriteria(KeyWordCriteria criteria) {
        this.criteria = criteria;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
