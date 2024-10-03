package biz.picosoft.demo.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


@SuppressWarnings("common-java:DuplicatedBlocks")
public class BonLivraisonDTO implements Serializable {

    private Long id;

    private Long numerobonlivraison;

    private LocalDate datelivraion;

    private BonCommandeDTO boncommande;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumerobonlivraison() {
        return numerobonlivraison;
    }

    public void setNumerobonlivraison(Long numerobonlivraison) {
        this.numerobonlivraison = numerobonlivraison;
    }

    public LocalDate getDatelivraion() {
        return datelivraion;
    }

    public void setDatelivraion(LocalDate datelivraion) {
        this.datelivraion = datelivraion;
    }

    public BonCommandeDTO getBoncommande() {
        return boncommande;
    }

    public void setBoncommande(BonCommandeDTO boncommande) {
        this.boncommande = boncommande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BonLivraisonDTO)) {
            return false;
        }

        BonLivraisonDTO bonLivraisonDTO = (BonLivraisonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, bonLivraisonDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BonLivraisonDTO{" +
            "id=" + getId() +
            ", numerobonlivraison=" + getNumerobonlivraison() +
            ", datelivraion='" + getDatelivraion() + "'" +
            ", boncommande=" + getBoncommande() +
            "}";
    }
}
