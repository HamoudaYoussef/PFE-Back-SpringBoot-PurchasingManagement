package biz.picosoft.demo.service;

import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.client.kernel.model.objects.ObjectState;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.controller.errors.DemandeAchatErrors;
import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.enumeration.StatutDA;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.DemandeAchatOutputDTO;
import biz.picosoft.demo.service.mapper.DemandeAchatMapper;

import java.time.LocalDate;
import java.util.*;

import biz.picosoft.demo.service.mapper.DemandeAchatOutputMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.hibernate.id.IdentifierGenerator.ENTITY_NAME;

/**
 * Service Implementation for managing {@link biz.picosoft.demo.domain.DemandeAchat}.
 */
@Service
@Transactional
public class DemandeAchatService {

    private final Logger log = LoggerFactory.getLogger(DemandeAchatService.class);

    private final DemandeAchatRepository demandeAchatRepository;

    private final DemandeAchatMapper demandeAchatMapper;

    private final DemandeAchatOutputMapper demandeAchatOutputMapper;

    @Autowired
    KernelService kernelService;

    @Autowired
    private KernelInterface kernelInterface;
    @Autowired
    private EntityManager entityManager;


    public DemandeAchatService(DemandeAchatRepository demandeAchatRepository, DemandeAchatMapper demandeAchatMapper, DemandeAchatOutputMapper demandeAchatOutputMapper) {
        this.demandeAchatRepository = demandeAchatRepository;
        this.demandeAchatMapper = demandeAchatMapper;
        this.demandeAchatOutputMapper = demandeAchatOutputMapper;
    }

    public Long initProcess(AclClass aclClass, CurrentUser currentUser) {
        if (aclClass == null) {
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Msg_DemandeAchAT_null,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Key_DemandeAchat_null);
        }
        //Create Object & persisit
        DemandeAchat demandeAchat = new DemandeAchat();
        demandeAchat.setDatedemande(LocalDate.now());
        demandeAchat.setDatebesoin(LocalDate.now());
        demandeAchat.setDescription("description");
        demandeAchat.setStatut(StatutDA.en_attente);
        demandeAchat = this.saveDA(demandeAchat, Collections.singletonList(currentUser.getEmployeSid()), new ArrayList<>(), aclClass, currentUser);

        DemandeAchatOutputDTO result = demandeAchatOutputMapper.toDto(demandeAchat);
        result.setClassName(aclClass.getClasse());
        result.setClassId(aclClass.getId());
        result.setLabelClass(aclClass.getLabel());
        result.setSimpleClassName(aclClass.getSimpleName());
        Map<String, Object> variables = new HashMap<>();
        variables.put("initiator", currentUser.getEmployeSid());
        variables.put("processKey", aclClass.getFwProcess());
        variables.put("data", result);
        kernelService.startProcessInstance(variables);
        entityManager.clear();
        //reload record
        return demandeAchat.getId();
    }

    public DemandeAchat saveDA(DemandeAchat demandeAchat,
                               List<String> authors,
                               List<String> readers,
                               AclClass aclClass, CurrentUser currentUser) {
        log.debug("Request to save Invoice : {}", demandeAchat);


        Boolean isCreated = false;
        if (demandeAchat.getId() == null) {
            isCreated = true;
        }
        if (isCreated == true) {

            demandeAchat.setSecuriteLevel(aclClass.getSecuriteLevel());
            demandeAchat.setDraft(true);
            demandeAchat = demandeAchatRepository.saveAndFlush(demandeAchat);

            this.setDefaultStateDA(demandeAchat, aclClass);
        } else {
            DemandeAchat invoiceRecent = demandeAchatRepository.findById(demandeAchat.getId()).get();
            invoiceRecent.setDraft(false);
            if (invoiceRecent.getWfProcessID() != null)
                demandeAchat.setWfProcessID(invoiceRecent.getWfProcessID());
            if (invoiceRecent.getIdentifiant() != null)
                demandeAchat.setIdentifiant(invoiceRecent.getIdentifiant());
            if (demandeAchat.getActivityName() == null || demandeAchat.getActivityName().equals("null")) {
                demandeAchat.setActivityName(invoiceRecent.getActivityName());
            }
            // TODO security level
            if (demandeAchat.getSecuriteLevel() == null) {
                demandeAchat.setSecuriteLevel(invoiceRecent.getSecuriteLevel());
                if (!invoiceRecent.getSecuriteLevel().equals(demandeAchat.getSecuriteLevel())) {
                    try {
                        kernelService.adjustAttachmentSecurity(aclClass.getId(), invoiceRecent.getId(), demandeAchat.getSecuriteLevel());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
            try {
                Long numberOfAttachments = kernelInterface.countAttachements(demandeAchat.getId(), aclClass.getId());
                demandeAchat.setNumberOfattachments(numberOfAttachments);
            } catch (Exception e) {
            }

        }
        this.setCurrentState(demandeAchat, aclClass);

        if (authors != null && readers != null && demandeAchat.getId() != null)
            kernelService.applySecurity(aclClass.getClasse(), demandeAchat.getId(), authors, readers, new ArrayList<>(), null, null, isCreated, false);

        return demandeAchat;
    }
    public DemandeAchat setDefaultStateDA(DemandeAchat demandeAchat, AclClass aclClass) {
        if (demandeAchat.getId() == null)
            throw new BadRequestAlertException("A new Invoice must be have an ID to set the default state", ENTITY_NAME, "idexists");


        ObjectState objectState = new ObjectState();
        objectState.setBusinessClass(aclClass);
        objectState.setObjectId(demandeAchat.getId());
        if (aclClass.getDefaultState() == null)
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Msg_default_state_null,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Key_default_state_null);
        objectState.setCurrentState(aclClass.getDefaultState());
        kernelService.saveObjectState(objectState);
        // objectStateRepository.save(objectState);

        return demandeAchat;
    }

    public void setCurrentState(DemandeAchat demandeAchat, AclClass aclClass) {
        Optional<ObjectState> objectState = kernelService.getObjectState(demandeAchat.getId(), aclClass.getClasse());
        if (objectState.isPresent()) {
            demandeAchat.setStatus(objectState.get().getCurrentState().getLabel());
        }
        demandeAchatRepository.save(demandeAchat);
    }

    /**
     * Save a demandeAchat.
     *
     * @param demandeAchatDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeAchatDTO save(DemandeAchatDTO demandeAchatDTO) {
        log.debug("Request to save DemandeAchat : {}", demandeAchatDTO);
        DemandeAchat demandeAchat = demandeAchatMapper.toEntity(demandeAchatDTO);
        demandeAchat = demandeAchatRepository.save(demandeAchat);
        return demandeAchatMapper.toDto(demandeAchat);
    }

    /**
     * Update a demandeAchat.
     *
     * @param demandeAchatDTO the entity to save.
     * @return the persisted entity.
     */
    public DemandeAchatDTO update(DemandeAchatDTO demandeAchatDTO) {
        log.debug("Request to update DemandeAchat : {}", demandeAchatDTO);
        DemandeAchat demandeAchat = demandeAchatMapper.toEntity(demandeAchatDTO);
        demandeAchat = demandeAchatRepository.save(demandeAchat);
        return demandeAchatMapper.toDto(demandeAchat);
    }

    /**
     * Partially update a demandeAchat.
     *
     * @param demandeAchatDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DemandeAchatDTO> partialUpdate(DemandeAchatDTO demandeAchatDTO) {
        log.debug("Request to partially update DemandeAchat : {}", demandeAchatDTO);

        return demandeAchatRepository
            .findById(demandeAchatDTO.getId())
            .map(existingDemandeAchat -> {
                demandeAchatMapper.partialUpdate(existingDemandeAchat, demandeAchatDTO);

                return existingDemandeAchat;
            })
            .map(demandeAchatRepository::save)
            .map(demandeAchatMapper::toDto);
    }

    /**
     * Get all the demandeAchats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DemandeAchatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DemandeAchats");
        return demandeAchatRepository.findAll(pageable).map(demandeAchatMapper::toDto);
    }

    /**
     * Get one demandeAchat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DemandeAchatDTO> findOne(Long id) {
        log.debug("Request to get DemandeAchat : {}", id);
        return demandeAchatRepository.findById(id).map(demandeAchatMapper::toDto);
    }

    /**
     * Delete the demandeAchat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DemandeAchat : {}", id);
        demandeAchatRepository.deleteById(id);
    }
}
