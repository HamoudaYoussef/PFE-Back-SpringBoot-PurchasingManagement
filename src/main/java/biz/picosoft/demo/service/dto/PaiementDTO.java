package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaiementDTO implements Serializable {

    private Long id;

    private Long montanttotal;

    private LocalDate datepaiement;

    private String methodepaiement;

    private String statuts;

    private FactureDTO facture;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(Long montanttotal) {
        this.montanttotal = montanttotal;
    }

    public LocalDate getDatepaiement() {
        return datepaiement;
    }

    public void setDatepaiement(LocalDate datepaiement) {
        this.datepaiement = datepaiement;
    }

    public String getMethodepaiement() {
        return methodepaiement;
    }

    public void setMethodepaiement(String methodepaiement) {
        this.methodepaiement = methodepaiement;
    }

    public String getStatuts() {
        return statuts;
    }

    public void setStatuts(String statuts) {
        this.statuts = statuts;
    }

    public FactureDTO getFacture() {
        return facture;
    }

    public void setFacture(FactureDTO facture) {
        this.facture = facture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaiementDTO)) {
            return false;
        }

        PaiementDTO paiementDTO = (PaiementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paiementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaiementDTO{" +
            "id=" + getId() +
            ", montanttotal=" + getMontanttotal() +
            ", datepaiement='" + getDatepaiement() + "'" +
            ", methodepaiement='" + getMethodepaiement() + "'" +
            ", statuts='" + getStatuts() + "'" +
            ", facture=" + getFacture() +
            "}";
    }
}
