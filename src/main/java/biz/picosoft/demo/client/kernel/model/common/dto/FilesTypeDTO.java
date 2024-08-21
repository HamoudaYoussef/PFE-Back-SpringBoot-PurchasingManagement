package biz.picosoft.demo.client.kernel.model.common.dto;

import java.io.Serializable;


public class FilesTypeDTO implements Serializable {

    private Long id;

    private String type;

    private String icon;

    private String label;

    private String bgColor;

    private String textColor;


    public FilesTypeDTO() {
    }

    public FilesTypeDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getBgColor() {
        return bgColor;
    }

    public void setBgColor(String bgColor) {
        this.bgColor = bgColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FilesTypeDTO)) {
            return false;
        }

        return id != null && id.equals(((FilesTypeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FilesTypeDTO{" +
                "id=" + getId() +
                ", type='" + getType() + "'" +
                ", icon='" + getIcon() + "'" +
                ", label='" + getLabel() + "'" +
                ", bgColor='" + getBgColor() + "'" +
                ", textColor='" + getTextColor() + "'" +
                "}";
    }
}
