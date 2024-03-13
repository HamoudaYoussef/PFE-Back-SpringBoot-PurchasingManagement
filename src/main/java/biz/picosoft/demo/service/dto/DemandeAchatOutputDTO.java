package biz.picosoft.demo.service.dto;

import biz.picosoft.demo.client.kernel.model.objects.ObjectsDTO;
import biz.picosoft.demo.domain.enumeration.StatutDA;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;


/**
 * A DTO representing a user, with his authorities.
 */

public class DemandeAchatOutputDTO extends ObjectsDTO {

    private Long id;


    private LocalDate datedemande;
    private LocalDate datebesoin;

    private StatutDA statut;


    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public LocalDate getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(LocalDate datedemande) {
        this.datedemande = datedemande;
    }

    public LocalDate getDatebesoin() {
        return datebesoin;
    }

    public void setDatebesoin(LocalDate datebesoin) {
        this.datebesoin = datebesoin;
    }
    public StatutDA getStatut() {
        return statut;
    }

    public void setStatut(StatutDA statut) {
        this.statut = statut;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
