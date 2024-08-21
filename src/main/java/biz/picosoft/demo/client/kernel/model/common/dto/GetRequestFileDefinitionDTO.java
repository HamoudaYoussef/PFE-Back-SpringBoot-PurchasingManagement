package biz.picosoft.demo.client.kernel.model.common.dto;


import biz.picosoft.demo.client.kernel.model.objects.RequestFileOption;

public class GetRequestFileDefinitionDTO {
    private Long id;
    private String name;
    private String label;
    private Boolean labelEditable;
    private String bsIcon;
    private String description;
    private Boolean fileRequired ;
    private Integer minCopies;
    private Integer maxCopies;
        private RequestFileOption hasExpirationDate;
    private RequestFileOption hasIssueDate  ;
    private RequestFileOption hasIssueAdress  ;
    private RequestFileOption  hasIdRegex  ;
    private RequestFileOption hasCopiesNbr ;
    private String idRegex;
    private Boolean defaultFile;
    private String fileOption;


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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getLabelEditable() {
        return labelEditable;
    }

    public void setLabelEditable(Boolean labelEditable) {
        this.labelEditable = labelEditable;
    }

    public String getBsIcon() {
        return bsIcon;
    }

    public void setBsIcon(String bsIcon) {
        this.bsIcon = bsIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFileRequired() {
        return fileRequired;
    }

    public void setFileRequired(Boolean fileRequired) {
        this.fileRequired = fileRequired;
    }

    public Integer getMinCopies() {
        return minCopies;
    }

    public void setMinCopies(Integer minCopies) {
        this.minCopies = minCopies;
    }

    public Integer getMaxCopies() {
        return maxCopies;
    }

    public void setMaxCopies(Integer maxCopies) {
        this.maxCopies = maxCopies;
    }

    public RequestFileOption getHasExpirationDate() {
        return hasExpirationDate;
    }

    public void setHasExpirationDate(RequestFileOption hasExpirationDate) {
        this.hasExpirationDate = hasExpirationDate;
    }

    public RequestFileOption getHasIssueDate() {
        return hasIssueDate;
    }

    public void setHasIssueDate(RequestFileOption hasIssueDate) {
        this.hasIssueDate = hasIssueDate;
    }

    public RequestFileOption getHasIssueAdress() {
        return hasIssueAdress;
    }

    public void setHasIssueAdress(RequestFileOption hasIssueAdress) {
        this.hasIssueAdress = hasIssueAdress;
    }

    public RequestFileOption getHasIdRegex() {
        return hasIdRegex;
    }

    public void setHasIdRegex(RequestFileOption hasIdRegex) {
        this.hasIdRegex = hasIdRegex;
    }

    public RequestFileOption getHasCopiesNbr() {
        return hasCopiesNbr;
    }

    public void setHasCopiesNbr(RequestFileOption hasCopiesNbr) {
        this.hasCopiesNbr = hasCopiesNbr;
    }

    public String getIdRegex() {
        return idRegex;
    }

    public void setIdRegex(String idRegex) {
        this.idRegex = idRegex;
    }

    public Boolean getDefaultFile() {
        return defaultFile;
    }

    public void setDefaultFile(Boolean defaultFile) {
        this.defaultFile = defaultFile;
    }

    public String getFileOption() {
        return fileOption;
    }

    public void setFileOption(String fileOption) {
        this.fileOption = fileOption;
    }
}
