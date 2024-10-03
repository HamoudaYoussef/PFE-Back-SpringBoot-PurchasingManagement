package biz.picosoft.demo.client.kernel.model.common.criteria;


import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;


/**
 * Criteria class for the {@link biz.picosoft.demo.client.kernel.model.common.Keyword} entity. This class is used
 * in {@link biz.picosoft.demo.client.kernel.model.common.Keyword} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /key-words?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class KeyWordCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

//    private StringFilter category;
//
//    private StringFilter alias;
//
//    private StringFilter label;

    public KeyWordCriteria() {}

    public KeyWordCriteria(KeyWordCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
//        this.category = other.category == null ? null : other.category.copy();
//        this.alias = other.alias == null ? null : other.alias.copy();
//        this.label = other.label == null ? null : other.label.copy();
    }

    @Override
    public KeyWordCriteria copy() {
        return new KeyWordCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

//    public StringFilter getCategory() {
//        return category;
//    }
//
//    public StringFilter category() {
//        if (category == null) {
//            category = new StringFilter();
//        }
//        return category;
//    }
//
//    public void setCategory(StringFilter category) {
//        this.category = category;
//    }
//
//    public StringFilter getAlias() {
//        return alias;
//    }
//
//    public StringFilter alias() {
//        if (alias == null) {
//            alias = new StringFilter();
//        }
//        return alias;
//    }
//
//    public void setAlias(StringFilter alias) {
//        this.alias = alias;
//    }
//
//    public StringFilter getLabel() {
//        return label;
//    }
//
//    public StringFilter label() {
//        if (label == null) {
//            label = new StringFilter();
//        }
//        return label;
//    }
//
//    public void setLabel(StringFilter label) {
//        this.label = label;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final KeyWordCriteria that = (KeyWordCriteria) o;
        return (
            Objects.equals(id, that.id)
//                    &&
//            Objects.equals(category, that.category) &&
//            Objects.equals(alias, that.alias) &&
//            Objects.equals(label, that.label)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KeyWordCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
//            (category != null ? "category=" + category + ", " : "") +
//            (alias != null ? "alias=" + alias + ", " : "") +
//            (label != null ? "label=" + label + ", " : "") +
            "}";
    }
}
