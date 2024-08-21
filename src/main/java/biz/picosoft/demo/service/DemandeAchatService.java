package biz.picosoft.demo.service;

import biz.picosoft.demo.Workflow.domain.BpmJob;
import biz.picosoft.demo.Workflow.service.WorkflowService;
import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import biz.picosoft.demo.client.kernel.model.RulesDTO;
import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.client.kernel.model.acl.Permission;
import biz.picosoft.demo.client.kernel.model.acl.enumeration.Access;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.client.kernel.model.objects.ObjectDTO;
import biz.picosoft.demo.client.kernel.model.objects.ObjectState;
import biz.picosoft.demo.client.kernel.model.objects.ObjectsDTO;
import biz.picosoft.demo.client.kernel.model.objects.WFDTO;
import biz.picosoft.demo.client.kernel.model.pm.ActivityType;
import biz.picosoft.demo.client.kernel.model.pm.Role;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.controller.errors.RCErrors;
import biz.picosoft.demo.domain.*;
import biz.picosoft.demo.domain.ennumeration.StatutDA;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.repository.DemandeDevisRepository;
import biz.picosoft.demo.repository.OffreRepository;
import biz.picosoft.demo.repository.ProduitDemandeeRepository;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.DemandeAchatInputDTO;
import biz.picosoft.demo.service.dto.DemandeAchatOutputDTO;
import biz.picosoft.demo.service.mapper.DemandeAchatInputMapper;
import biz.picosoft.demo.service.mapper.DemandeAchatMapper;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import biz.picosoft.demo.service.mapper.DemandeAchatOutputMapper;
import biz.picosoft.demo.service.mapper.DemandeAchatOutputMapperImpl;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
@Transactional
public class DemandeAchatService {

    private final Logger log = LoggerFactory.getLogger(DemandeAchatService.class);

    private final DemandeAchatRepository demandeAchatRepository;
    private final ProduitDemandeeRepository produitDemandeeRepository;
    private final DemandeDevisRepository demandeDevisRepository;

    private final OffreRepository offreRepository;


    private final DemandeAchatMapper demandeAchatMapper;

    private final DemandeAchatOutputMapper demandeAchatOutputMapper;
    private final DemandeAchatInputMapper demandeAchatInputMapper;

    private final CurrentUser currentUser;

    private final WorkflowService workflowService;

    private final KernelService kernelService;
    private final KernelInterface kernelInterface;


    public DemandeAchatService(DemandeAchatOutputMapperImpl demandeAchatOutputMapper,CurrentUser currentUser,
                               DemandeAchatRepository demandeAchatRepository,WorkflowService workflowService,KernelInterface kernelInterface,
                               KernelService kernelService, DemandeAchatMapper demandeAchatMapper,
                               DemandeAchatInputMapper demandeAchatInputMapper,DemandeDevisRepository demandeDevisRepository,
                               ProduitDemandeeRepository produitDemandeeRepository, OffreRepository offreRepository) {
        this.demandeAchatRepository = demandeAchatRepository;
        this.demandeAchatMapper = demandeAchatMapper;
        this.kernelService = kernelService;
        this.kernelInterface = kernelInterface;
        this.currentUser = currentUser;
        this.demandeAchatOutputMapper = demandeAchatOutputMapper;
        this.workflowService = workflowService;
        this.demandeAchatInputMapper = demandeAchatInputMapper;
        this.produitDemandeeRepository = produitDemandeeRepository;
this.offreRepository = offreRepository;
this.demandeDevisRepository = demandeDevisRepository;
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
        DemandeAchat demandeAchat = demandeAchatRepository.findById(id).orElse(null);
        if (demandeAchat != null) {
            // Supprimer les offres associées
            for (Offre offre : demandeAchat.getOffres()) {
                offre.setDemandeachat(null);  // Déassocier l'offre
                offreRepository.delete(offre);
            }

            // Supprimer les demandes de devis associées
            for (DemandeDevis demandeDevis : demandeAchat.getDemandeDevis()) {
                demandeDevis.setDemandeAchat(null);  // Déassocier la demande de devis
                demandeDevisRepository.delete(demandeDevis);
            }
            List<ProduitDemandee> produitsDemandes = produitDemandeeRepository.findByDemandeAchatId(demandeAchat.getId());
            for (ProduitDemandee produitDemandee : produitsDemandes) {
                produitDemandee.setDemandeAchat(null);  // Déassociation
                produitDemandeeRepository.delete(produitDemandee);  // Optionnel : Suppression si souhaité
            }

            // Supprimer la demande d'achat
            demandeAchatRepository.delete(demandeAchat);
        }
    }


    public Boolean checkRole(String profile, String roleName) {
        if (profile == null)
            throw new BadRequestAlertException(
                    RCErrors.ERR_Msg_profile_not_found,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Key_profile_not_found);
        List<Role> roles = kernelService.findAllByProfiles(profile);
        List<String> rolesName = new ArrayList<>();
        for (Role role : roles)
            rolesName.add(role.getName());
        if (!rolesName.contains(roleName))
            throw new BadRequestAlertException(
                    RCErrors.ERR_Msg_not_authorized,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Key_not_authorized);
        return true;
    }
 
    public void updateStatut(long id, StatutDA statutDA) {
        DemandeAchat demandeAchat = demandeAchatRepository.findById(id).get();
        demandeAchat.setStatut(statutDA.termine);
        demandeAchatRepository.save(demandeAchat);
    }

    public DemandeAchatOutputDTO getDADTObyId(Long id) {
        DemandeAchatOutputDTO result = null;
        try {
            result = proceedGetDemandeId(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
        kernelInterface.addUserActivity(ActivityType.READ, id, result.getClassName(), "", "");
        return result;
    }
    public DemandeAchatOutputDTO proceedGetDemandeId(Long id) throws IOException, TemplateException {
        log.debug("Request to get MmInbound : {}", id);

        DemandeAchatOutputDTO demandeOutputDTO = new DemandeAchatOutputDTO();

        // check request case existance
        Optional<DemandeAchat> o = demandeAchatRepository.findById(id);
        if (!o.isPresent())
            throw new BadRequestAlertException(
                    RCErrors.ERR_Key_requestCase_null,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Msg_requestCase_null);

        // check acl class existance
        AclClass aclClass = kernelInterface.getaclClassByClassName(DemandeAchat.class.getName());
        if (aclClass == null)
            throw new BadRequestAlertException(
                    RCErrors.ERR_Msg_requestCase_null,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Key_requestCase_null);


        List<String> authorsSid = new ArrayList<>();
        List<String> readersSid = new ArrayList<>();
        Set<String> authorsSet = kernelInterface.findAllWhriteSids(aclClass.getId(), o.get().getId());
        Set<String> readersSet = kernelInterface.findAllReadSids(aclClass.getId(), o.get().getId());
        Set<String> tempReadersSet = kernelInterface.findAllTempReadSids(aclClass.getId(), o.get().getId());

        authorsSid = new ArrayList<>(authorsSet);
        List<String> readers = new ArrayList<>(readersSet);
        List<String> tempReaders = new ArrayList<>(tempReadersSet);
        readersSid.addAll(readers);
        readersSid.addAll(tempReaders);

        String permission = Permission.NONE.name();
        permission = kernelInterface.checkSecurity(aclClass.getSimpleName(), id, currentUser.getSid());
        Access access = Access.Direct;
        if (permission.equals(Permission.NONE.name()))
            access = kernelInterface.checkAccess(authorsSid, readersSid, o.get().getSecuriteLevel());

        if (permission.equals(Permission.NONE.name()) && access.equals(Access.NoAccess) && o.get().getSecuriteLevel().equals(0)) {
            permission = Permission.READ.name();
        } else if (permission.equals(Permission.NONE.name()) && access.equals(Access.NoAccess) && !o.get().getSecuriteLevel().equals(0)) {
            throw new BadRequestAlertException(
                    RCErrors.ERR_Msg_not_authorized,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Key_not_authorized);
        } else if (permission.equals(Permission.NONE.name()) && access.equals(Access.AuthorIndirect)) {
            permission = Permission.WRITE.name();
        } else if (permission.equals(Permission.NONE.name()) && access.equals(Access.ReaderIndirect)) {
            permission = Permission.READ.name();
        }
        //set file access token
        String datetimeExpiry = LocalDateTime.now().plusHours(1).toString();
        String objectId = o.get().getId().toString();
        String classId = aclClass.getId().toString();
        String userSecurityLevel = currentUser.getProfileSecuriteLevel().toString();
        String strToEncrypt = datetimeExpiry + "," + objectId + "," + classId + "," + userSecurityLevel + "," + permission;
        String accesToken = kernelService.encryptFileAccessToken(strToEncrypt);

        //input for globalObject
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.setObjectId(o.get().getId());
        objectDTO.setObject(o.get());
        objectDTO.setWfProcessId(o.get().getWfProcessID());
        objectDTO.setAccessToken(accesToken);
        if ((aclClass != null)) {
            objectDTO.setClassId(aclClass.getId());
            ObjectsDTO g = kernelInterface.getobjectsDto(objectDTO);

            demandeOutputDTO = demandeAchatOutputMapper.toDto(o.get());
            demandeOutputDTO = this.setObjectInOutPutDTO(g, demandeOutputDTO);
            demandeOutputDTO.setUserPermission(permission);
            if (!permission.equals((Permission.WRITE.name()))) {
                WFDTO wfdtoWithOutDecision = demandeOutputDTO.getWorkflow();
                if (demandeOutputDTO.getWorkflow() != null) {
                    wfdtoWithOutDecision.setDecisionsWF(new ArrayList<>());
                    demandeOutputDTO.setWorkflow(wfdtoWithOutDecision);
                    demandeOutputDTO.setWfProcessName(demandeOutputDTO.getWorkflow().getWfProcessName());
                }
            }

        }
        return demandeOutputDTO;

    }
    public DemandeAchatOutputDTO setObjectInOutPutDTO(ObjectsDTO g, DemandeAchatOutputDTO requestCaseOutputDTO) throws IOException, TemplateException {
        requestCaseOutputDTO.setClassName(g.getClassName());
        requestCaseOutputDTO.setClassId(g.getClassId());
        requestCaseOutputDTO.setLabelClass(g.getLabelClass());
        requestCaseOutputDTO.setSimpleClassName(g.getSimpleClassName());
        requestCaseOutputDTO.setAttachements(g.getAttachements());
        requestCaseOutputDTO.setEvents(g.getEvents());
        requestCaseOutputDTO.setUserActivity(g.getUserActivity());
        requestCaseOutputDTO.setUserPermission(g.getUserPermission());
        requestCaseOutputDTO.setCurrentState(g.getCurrentState());
        requestCaseOutputDTO.setFormSource(g.getFormSource());
        requestCaseOutputDTO.setWorkflow(g.getWorkflow());
        requestCaseOutputDTO.setRemaingRequestFileDefinitions(g.getRemaingRequestFileDefinitions());

        requestCaseOutputDTO.setMandatoryTemplateFileName(g.getMandatoryTemplateFileName());
        requestCaseOutputDTO.setDefaultTemplateFileName(g.getDefaultTemplateFileName());
        requestCaseOutputDTO.setEmailTemplateFileName(g.getEmailTemplateFileName());
        requestCaseOutputDTO.setOfficeTemplateFileName(g.getOfficeTemplateFileName());
        requestCaseOutputDTO.setOptionalTemplateFileName(g.getOptionalTemplateFileName());

        requestCaseOutputDTO.setSecurity(g.getSecurity());
        requestCaseOutputDTO.setComponents(g.getComponents());

        return requestCaseOutputDTO;
    }
    @Transactional
    public DemandeAchatOutputDTO initProcessDemandeAchat(AclClass aclClass) throws Exception {

        // check existance of extracted acl class
        if (aclClass == null)
            throw new BadRequestAlertException(RCErrors.ERR_Msg_AclClass_null, RCErrors.Entity_requestCase, RCErrors.ERR_Key_AclClass_null);

        // initiate new object Courrier
        DemandeAchat demande = new DemandeAchat();

        demande.setDatedemande(LocalDate.now());

        // persite the object
        demande = demandeAchatRepository.save(demande);

        // map entity courrier to output DTO
        DemandeAchatOutputDTO demandeOutputDTO = demandeAchatOutputMapper.toDto(demande);

        demandeOutputDTO.setClassId(aclClass.getId());

        demandeOutputDTO.setClassName(aclClass.getClasse());

        demandeOutputDTO.setLabelClass(aclClass.getLabel());

        demandeOutputDTO.setSimpleClassName(aclClass.getSimpleName());

        // send variables to start new process instance
        BpmJob bpmJob = workflowService.startProcessInstanceWithoutEvent(currentUser.getEmployeSid(), aclClass.getFwProcess(), demandeOutputDTO);

        List<String> authors = bpmJob.getAuthors();

        List<String> readers = bpmJob.getReaders();

        demandeOutputDTO = (DemandeAchatOutputDTO) bpmJob.getDataObject();

        demande = demandeAchatOutputMapper.toEntity(demandeOutputDTO);

        demande.setWfProcessID(bpmJob.getProcessID());
        System.out.println("getProcessID"+bpmJob.getProcessID());
        demande.setActivityName(bpmJob.getActivityName());
        System.out.println("bpmJob.getActivityName()"+bpmJob.getActivityName());
        demande.setEndProcess(bpmJob.getEndProcess());
        System.out.println("bpmJob.getEndProcess()"+bpmJob.getEndProcess());
        demande.setAssignee(bpmJob.getAssignee() != null ? bpmJob.getAssignee() : null);
        System.out.println("bpmJob.getAssignee()"+bpmJob.getAssignee());
        //System.out.println("hkkkkkkkkkkkkkk"+kernelInterface.getSequenceNumberByName(new JSONObject(demande), aclClass.getSequenceNameFM()));
//        demande.setDemandeNumber(kernelInterface.getSequenceNumberByName(new JSONObject(demande), aclClass.getSequenceNameFM()));

        demande = saveDemande(demande, authors, readers, aclClass, true);
        System.out.println("demande object"+demande);
        return demandeAchatOutputMapper.toDto(demande);
    }

    public DemandeAchat saveDemande(DemandeAchat demande) {
        DemandeAchat requestCase1 = demandeAchatRepository.save(demande);
        return requestCase1;
    }
    public DemandeAchat saveDemande(DemandeAchat demande, List<String> authors, List<String> readers, AclClass aclClass, Boolean isCreated) {
        log.debug("Request to save Courrier : {}", demande);

        if (isCreated) {

            if (demande.getSecuriteLevel() == null) demande.setSecuriteLevel(aclClass.getSecuriteLevel());


            demande = setDefaultStateCourrier(demande, aclClass);

            demande = saveRequestCaseAndflush(demande);

            if (authors != null && readers != null && demande.getId() != null)
                kernelService.applySecurity(aclClass.getClasse(), demande.getId(), authors, readers, new ArrayList<>(), null, null, isCreated, false);
        } else {
            DemandeAchat demandeRecent = demandeAchatRepository.findById(demande.getId()).get();

            if (demandeRecent.getWfProcessID() != null)
                demande.setWfProcessID(demandeRecent.getWfProcessID());

            if (demandeRecent.getDemandeNumber() != null)
                demande.setDemandeNumber(demandeRecent.getDemandeNumber());

            if (!demandeRecent.isExcludeFromView())
                demande.setExcludeFromView(demandeRecent.isExcludeFromView());

            if (demande.getActivityName() == null || (demande.getActivityName() != null && demande.getActivityName().equals("null")))
                demande.setActivityName(demandeRecent.getActivityName());

            if (demande.getSecuriteLevel() == null)
                demande.setSecuriteLevel(demandeRecent.getSecuriteLevel());

            else if (!demandeRecent.getSecuriteLevel().equals(demande.getSecuriteLevel()))
                try {
                    kernelService.adjustAttachmentSecurity(aclClass.getId(), demandeRecent.getId(), demande.getSecuriteLevel());
                } catch (Exception e) {
                    log.error("error in adjustAttachmentSecurity " + e.getMessage());
                }

            try {
                Long numberOfAttachments = kernelInterface.countAttachements(demande.getId(), aclClass.getId());
                demande.setNumberOfattachments(numberOfAttachments);
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            //compute zpl
            try {
                demande = setCurrentState(demande, aclClass);

                demande = saveDemande(demande);

                if (authors != null && readers != null && demande.getId() != null)
                    kernelService.applySecurity(aclClass.getClasse(), demande.getId(), authors, readers, new ArrayList<>(), null, null, isCreated, false);

            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }

        return demande;
    }
    public DemandeAchat setDefaultStateCourrier(DemandeAchat demande, AclClass aclClass) {

        ObjectState objectState = new ObjectState();
        objectState.setBusinessClass(aclClass);
        objectState.setObjectId(demande.getId());
        if (aclClass.getDefaultState() == null)
            throw new BadRequestAlertException(
                    RCErrors.ERR_Msg_default_state_null,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Key_default_state_null);
        objectState.setCurrentState(aclClass.getDefaultState());
        kernelService.saveObjectState(objectState);
        // objectStateRepository.save(objectState);
        demande.setStatus(aclClass.getDefaultState().getLabel());
        return demande;
    }

    public DemandeAchat setCurrentState(DemandeAchat demande, AclClass aclClass) {
        Optional<ObjectState> objectState = kernelService.getObjectStateCourrier(demande.getId(), aclClass.getClasse());
        if (objectState.isPresent()) {
            demande.setStatus(objectState.get().getCurrentState().getLabel());
        }
        return demande;
    }
    public DemandeAchat saveRequestCaseAndflush(DemandeAchat requestCase) {
        DemandeAchat demande1 = demandeAchatRepository.saveAndFlush(requestCase);
        return demande1;
    }
    @PersistenceContext(unitName = "externDSEmFactory")
    private EntityManager entityManager;
    // submit methods
    @Transactional
    public DemandeAchatOutputDTO submitProcessDemandeAchat(DemandeAchatInputDTO demandeInputDTO, AclClass aclClass) throws Exception {

        entityManager.clear();
        // extract object by id from data base
        Optional<DemandeAchat> demandeOptional = demandeAchatRepository.findById(demandeInputDTO.getId());

        // extract courrier
        DemandeAchat requestCase = demandeOptional.get();

        // check if the object exist in database
        if (!demandeOptional.isPresent())
            throw new BadRequestAlertException(RCErrors.ERR_Msg_requestCase_null, RCErrors.Entity_requestCase, RCErrors.ERR_Key_requestCase_null);

        // extract permission of the authentifier from kernel
        String permission = kernelService.checkSecurity(aclClass.getSimpleName(), demandeInputDTO.getId(), currentUser.getSid());

        // check if the authentifier has permission WRITE
        if (!permission.equals(Permission.WRITE.name()) && !permission.equals(Permission.INH_WRITE.name()))
            throw new BadRequestAlertException(RCErrors.ERR_Msg_not_authorized, RCErrors.Entity_requestCase, RCErrors.ERR_Key_not_authorized);

        // validate attributes courrier
        this.validateRequestCase(aclClass, demandeInputDTO);

        // extract activity name from courrier
        String activityName = requestCase.getActivityName();

        // fusion the input object and the object from database
        demandeAchatInputMapper.partialUpdate(requestCase, demandeInputDTO);

        // map object courrier to courrier by id DTO
        DemandeAchatOutputDTO requestCaseOutputDTO = demandeAchatOutputMapper.toDto(requestCase);

        requestCaseOutputDTO.setClassId(aclClass.getId());

        requestCaseOutputDTO.setClassName(aclClass.getClasse());

        requestCaseOutputDTO.setLabelClass(aclClass.getLabel());

        requestCaseOutputDTO.setSimpleClassName(aclClass.getSimpleName());

        // initialize workflow information
        WFDTO workflow = new WFDTO();
        workflow.setWfProcessID(requestCase.getWfProcessID());
        workflow.setActivityName(requestCase.getActivityName());
        requestCaseOutputDTO.setWorkflow(workflow);

        BpmJob bpmJob = workflowService._nextTaskWithoutEvent(requestCase.getWfProcessID(), demandeInputDTO.getDecision(), demandeInputDTO.getWfComment(), requestCaseOutputDTO, aclClass);

        List<String> authors = bpmJob.getAuthors();

        List<String> readers = bpmJob.getReaders();

        requestCaseOutputDTO = (DemandeAchatOutputDTO) bpmJob.getDataObject();

        requestCase = demandeAchatOutputMapper.toEntity(requestCaseOutputDTO);

        requestCase.setWfProcessID(bpmJob.getProcessID());

        requestCase.setActivityName(bpmJob.getActivityName());

        requestCase.setEndProcess(bpmJob.getEndProcess());

        requestCase.setAssignee(bpmJob.getAssignee() != null ? bpmJob.getAssignee() : null);

        requestCase = saveDemande(requestCase, authors, readers, aclClass, false);

        return demandeAchatOutputMapper.toDto(requestCase);

    }
    public Boolean validateRequestCase(AclClass aclClass, DemandeAchatInputDTO demandeInputDTO) {

        String nameRule = "save-" + aclClass.getSimpleName();

        RulesDTO rulesDTO = kernelService.rulesByName(nameRule);
        if (rulesDTO != null && rulesDTO.getSrcCode() != null) {
            List<Object> objectList = new ArrayList<>();
            objectList.add(demandeInputDTO);
            try {
                System.out.println("zezezeze");
                //rulesService.executeRules(objectList, rulesDTO.getSrcCode());
            } catch (Exception e) {
                throw new BadRequestAlertException(
                        RCErrors.ERR_Msg_drools + ": " + nameRule,
                        aclClass.getSimpleName(),
                        RCErrors.ERR_Key_drools);
            }

        }

        if (demandeInputDTO.getMsg() == null)
            return true;
        else
            throw new BadRequestAlertException(
                    demandeInputDTO.getMsg(),
                    aclClass.getSimpleName(),
                    demandeInputDTO.getMsg());


    }
    public List<DemandeAchat> getDemandeByStatutTermine() {
        return demandeAchatRepository.findByStatutTermine();
    }

}
