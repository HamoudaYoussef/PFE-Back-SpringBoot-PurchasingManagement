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
import biz.picosoft.demo.domain.BonCommande;
import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.Offre;
import biz.picosoft.demo.domain.ennumeration.StatutBonCommande;
import biz.picosoft.demo.domain.ennumeration.StatutDA;
import biz.picosoft.demo.repository.BonCommandeRepository;
import biz.picosoft.demo.repository.OffreRepository;
import biz.picosoft.demo.service.dto.*;
import biz.picosoft.demo.service.mapper.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Service
@Transactional
public class BonCommandeService {

    private final Logger log = LoggerFactory.getLogger(BonCommandeService.class);

    private final BonCommandeRepository bonCommandeRepository;
    private final OffreRepository offreRepository;


    private final BonCommandeMapper bonCommandeMapper;

    private final BonCommandeOutputMapper bonCommandeOutputMapper;
    private final BonCommandeInputMapper bonCommandeInputMapper;

    private final CurrentUser currentUser;

    private final WorkflowService workflowService;

    private final KernelService kernelService;
    private final KernelInterface kernelInterface;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 30;
    private final SecureRandom random = new SecureRandom();

    public BonCommandeService(BonCommandeRepository bonCommandeRepository, BonCommandeMapper bonCommandeMapper,
                              WorkflowService workflowService,KernelInterface kernelInterface,
                              KernelService kernelService,BonCommandeOutputMapper bonCommandeOutputMapper,
                              BonCommandeInputMapper bonCommandeInputMapper,CurrentUser currentUser,OffreRepository offreRepository) {
        this.bonCommandeRepository = bonCommandeRepository;
        this.bonCommandeMapper = bonCommandeMapper;
        this.workflowService = workflowService;
        this.kernelService = kernelService;
        this.kernelInterface = kernelInterface;
        this.currentUser = currentUser;
        this.bonCommandeInputMapper = bonCommandeInputMapper;
        this.bonCommandeOutputMapper = bonCommandeOutputMapper;
        this.offreRepository = offreRepository;

    }

    /**
     * Save a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeDTO save(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to save BonCommande : {}", bonCommandeDTO);
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return bonCommandeMapper.toDto(bonCommande);
    }

    /**
     * Update a bonCommande.
     *
     * @param bonCommandeDTO the entity to save.
     * @return the persisted entity.
     */
    public BonCommandeDTO update(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to update BonCommande : {}", bonCommandeDTO);
        BonCommande bonCommande = bonCommandeMapper.toEntity(bonCommandeDTO);
        bonCommande = bonCommandeRepository.save(bonCommande);
        return bonCommandeMapper.toDto(bonCommande);
    }

    /**
     * Partially update a bonCommande.
     *
     * @param bonCommandeDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<BonCommandeDTO> partialUpdate(BonCommandeDTO bonCommandeDTO) {
        log.debug("Request to partially update BonCommande : {}", bonCommandeDTO);

        return bonCommandeRepository
            .findById(bonCommandeDTO.getId())
            .map(existingBonCommande -> {
                bonCommandeMapper.partialUpdate(existingBonCommande, bonCommandeDTO);

                return existingBonCommande;
            })
            .map(bonCommandeRepository::save)
            .map(bonCommandeMapper::toDto);
    }

    /**
     * Get all the bonCommandes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BonCommandeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BonCommandes");
        return bonCommandeRepository.findAll(pageable).map(bonCommandeMapper::toDto);
    }

    /**
     * Get one bonCommande by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BonCommandeDTO> findOne(Long id) {
        log.debug("Request to get BonCommande : {}", id);
        return bonCommandeRepository.findById(id).map(bonCommandeMapper::toDto);
    }

    /**
     * Delete the bonCommande by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BonCommande : {}", id);
        bonCommandeRepository.deleteById(id);
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


    public BonCommandeOutputDTO getbcDTObyId(Long id) {
        BonCommandeOutputDTO result = null;
        try {
            result = proceedGetBonCommandeId(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
        kernelInterface.addUserActivity(ActivityType.READ, id, result.getClassName(), "", "");
        return result;
    }
    public BonCommandeOutputDTO proceedGetBonCommandeId(Long id) throws IOException, TemplateException {
        log.debug("Request to get MmInbound : {}", id);

        BonCommandeOutputDTO bonCommandeOutputDTO = new BonCommandeOutputDTO();

        // check request case existance
        Optional<BonCommande> o = bonCommandeRepository.findById(id);
        if (!o.isPresent())
            throw new BadRequestAlertException(
                    RCErrors.ERR_Key_requestCase_null,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Msg_requestCase_null);

        // check acl class existance
        AclClass aclClass = kernelInterface.getaclClassByClassName(BonCommande.class.getName());
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

            bonCommandeOutputDTO = bonCommandeOutputMapper.toDto(o.get());
            bonCommandeOutputDTO = this.setObjectInOutPutDTO(g, bonCommandeOutputDTO);
            bonCommandeOutputDTO.setUserPermission(permission);
            if (!permission.equals((Permission.WRITE.name()))) {
                WFDTO wfdtoWithOutDecision = bonCommandeOutputDTO.getWorkflow();
                if (bonCommandeOutputDTO.getWorkflow() != null) {
                    wfdtoWithOutDecision.setDecisionsWF(new ArrayList<>());
                    bonCommandeOutputDTO.setWorkflow(wfdtoWithOutDecision);
                    bonCommandeOutputDTO.setWfProcessName(bonCommandeOutputDTO.getWorkflow().getWfProcessName());
                }
            }

        }
        return bonCommandeOutputDTO;

    }    public BonCommandeOutputDTO setObjectInOutPutDTO(ObjectsDTO g, BonCommandeOutputDTO requestCaseOutputDTO) throws IOException, TemplateException {
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
    public BonCommandeOutputDTO initProcessBonCommande(AclClass aclClass, Long offreId) throws Exception {

        // check existence of extracted acl class
        if (aclClass == null) {
            throw new BadRequestAlertException(RCErrors.ERR_Msg_AclClass_null, RCErrors.Entity_requestCase, RCErrors.ERR_Key_AclClass_null);
        }

        // retrieve the offer by its ID
        Offre offre = offreRepository.findById(offreId)
                .orElseThrow(() -> new BadRequestAlertException("Offer not found", "Offre", "offreNotFound"));

        // initiate new object Courrier
        BonCommande bonCommande = new BonCommande();
        bonCommande.setDateboncommande(LocalDate.now());
        bonCommande.setStatutbc(StatutBonCommande.EN_COURS);


        // generate the code
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        String generatedCode = code.toString();
        bonCommande.setReference(generatedCode);

        // associate the offer
        bonCommande.setOffre(offre);

        // persist the object
        bonCommande = bonCommandeRepository.save(bonCommande);

        // map entity courrier to output DTO
        BonCommandeOutputDTO bcOutputDTO = bonCommandeOutputMapper.toDto(bonCommande);
        bcOutputDTO.setClassId(aclClass.getId());
        bcOutputDTO.setClassName(aclClass.getClasse());
        bcOutputDTO.setLabelClass(aclClass.getLabel());
        bcOutputDTO.setSimpleClassName(aclClass.getSimpleName());

        // send variables to start new process instance
        BpmJob bpmJob = workflowService.startProcessInstanceWithoutEvent(currentUser.getEmployeSid(), aclClass.getFwProcess(), bcOutputDTO);

        List<String> authors = bpmJob.getAuthors();
        List<String> readers = bpmJob.getReaders();
        bcOutputDTO = (BonCommandeOutputDTO) bpmJob.getDataObject();

        bonCommande = bonCommandeOutputMapper.toEntity(bcOutputDTO);
        bonCommande.setWfProcessID(bpmJob.getProcessID());
        bonCommande.setActivityName(bpmJob.getActivityName());
        bonCommande.setEndProcess(bpmJob.getEndProcess());
        bonCommande.setAssignee(bpmJob.getAssignee() != null ? bpmJob.getAssignee() : null);

        bonCommande = saveBonCommande(bonCommande, authors, readers, aclClass, true);
        return bonCommandeOutputMapper.toDto(bonCommande);
    }
    public BonCommande saveBonCommande(BonCommande bonCommande) {
        BonCommande requestCase1 = bonCommandeRepository.save(bonCommande);
        return requestCase1;
    }
    public void updateStatutBC(long id, StatutBonCommande statutBonCommande) {
        BonCommande bonCommande = bonCommandeRepository.findById(id).get();
        bonCommande.setStatutbc(statutBonCommande.VALIDE);
        bonCommandeRepository.save(bonCommande);
    }
    public BonCommande saveBonCommande(BonCommande bonCommande, List<String> authors, List<String> readers, AclClass aclClass, Boolean isCreated) {
        log.debug("Request to save Courrier : {}", bonCommande);

        if (isCreated) {

            if (bonCommande.getSecuriteLevel() == null) bonCommande.setSecuriteLevel(aclClass.getSecuriteLevel());

            bonCommande = setDefaultStateCourrier(bonCommande, aclClass);

            bonCommande = saveRequestCaseAndflush(bonCommande);

            if (authors != null && readers != null && bonCommande.getId() != null)
                kernelService.applySecurity(aclClass.getClasse(), bonCommande.getId(), authors, readers, new ArrayList<>(), null, null, isCreated, false);
        } else {
            BonCommande bcRecent = bonCommandeRepository.findById(bonCommande.getId()).get();

            if (bcRecent.getWfProcessID() != null)
                bonCommande.setWfProcessID(bcRecent.getWfProcessID());

            if (bcRecent.getDemandeNumber() != null)
                bonCommande.setDemandeNumber(bcRecent.getDemandeNumber());

            if (!bcRecent.isExcludeFromView())
                bonCommande.setExcludeFromView(bcRecent.isExcludeFromView());

            if (bonCommande.getActivityName() == null || (bonCommande.getActivityName() != null && bonCommande.getActivityName().equals("null")))
                bonCommande.setActivityName(bcRecent.getActivityName());

            if (bonCommande.getSecuriteLevel() == null)
                bonCommande.setSecuriteLevel(bcRecent.getSecuriteLevel());

            else if (!bcRecent.getSecuriteLevel().equals(bonCommande.getSecuriteLevel()))
                try {
                    kernelService.adjustAttachmentSecurity(aclClass.getId(), bcRecent.getId(), bonCommande.getSecuriteLevel());
                } catch (Exception e) {
                    log.error("error in adjustAttachmentSecurity " + e.getMessage());
                }

            try {
                Long numberOfAttachments = kernelInterface.countAttachements(bonCommande.getId(), aclClass.getId());
                bonCommande.setNumberOfattachments(numberOfAttachments);
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            //compute zpl
            try {
                bonCommande = setCurrentState(bonCommande, aclClass);

                bonCommande = saveBonCommande(bonCommande);

                if (authors != null && readers != null && bonCommande.getId() != null)
                    kernelService.applySecurity(aclClass.getClasse(), bonCommande.getId(), authors, readers, new ArrayList<>(), null, null, isCreated, false);

            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }

        return bonCommande;
    }
    public BonCommande setDefaultStateCourrier(BonCommande bonCommande, AclClass aclClass) {

        ObjectState objectState = new ObjectState();
        objectState.setBusinessClass(aclClass);
        objectState.setObjectId(bonCommande.getId());
        if (aclClass.getDefaultState() == null)
            throw new BadRequestAlertException(
                    RCErrors.ERR_Msg_default_state_null,
                    RCErrors.Entity_requestCase,
                    RCErrors.ERR_Key_default_state_null);
        objectState.setCurrentState(aclClass.getDefaultState());
        kernelService.saveObjectState(objectState);
        // objectStateRepository.save(objectState);
        bonCommande.setStatus(aclClass.getDefaultState().getLabel());
        return bonCommande;
    }

    public BonCommande setCurrentState(BonCommande bonCommande, AclClass aclClass) {
        Optional<ObjectState> objectState = kernelService.getObjectStateCourrier(bonCommande.getId(), aclClass.getClasse());
        if (objectState.isPresent()) {
            bonCommande.setStatus(objectState.get().getCurrentState().getLabel());
        }
        return bonCommande;
    }
    public BonCommande saveRequestCaseAndflush(BonCommande requestCase) {
        BonCommande demande1 = bonCommandeRepository.saveAndFlush(requestCase);
        return demande1;
    }
    @PersistenceContext(unitName = "externDSEmFactory")
    private EntityManager entityManager;
    // submit methods
    @Transactional
    public BonCommandeOutputDTO submitProcessBonCommande(BonCommandeInputDTO bcInputDTO, AclClass aclClass) throws Exception {

        entityManager.clear();
        // extract object by id from data base
        Optional<BonCommande> demandeOptional = bonCommandeRepository.findById(bcInputDTO.getId());

        // extract courrier
        BonCommande requestCase = demandeOptional.get();

        // check if the object exist in database
        if (!demandeOptional.isPresent())
            throw new BadRequestAlertException(RCErrors.ERR_Msg_requestCase_null, RCErrors.Entity_requestCase, RCErrors.ERR_Key_requestCase_null);

        // extract permission of the authentifier from kernel
        String permission = kernelService.checkSecurity(aclClass.getSimpleName(), bcInputDTO.getId(), currentUser.getSid());

        // check if the authentifier has permission WRITE
        if (!permission.equals(Permission.WRITE.name()) && !permission.equals(Permission.INH_WRITE.name()))
            throw new BadRequestAlertException(RCErrors.ERR_Msg_not_authorized, RCErrors.Entity_requestCase, RCErrors.ERR_Key_not_authorized);

        // validate attributes courrier
        this.validateRequestCase(aclClass, bcInputDTO);

        // extract activity name from courrier
        String activityName = requestCase.getActivityName();

        // fusion the input object and the object from database
        bonCommandeInputMapper.partialUpdate(requestCase, bcInputDTO);

        // map object courrier to courrier by id DTO
        BonCommandeOutputDTO requestCaseOutputDTO = bonCommandeOutputMapper.toDto(requestCase);

        requestCaseOutputDTO.setClassId(aclClass.getId());

        requestCaseOutputDTO.setClassName(aclClass.getClasse());

        requestCaseOutputDTO.setLabelClass(aclClass.getLabel());

        requestCaseOutputDTO.setSimpleClassName(aclClass.getSimpleName());

        // initialize workflow information
        WFDTO workflow = new WFDTO();
        workflow.setWfProcessID(requestCase.getWfProcessID());
        workflow.setActivityName(requestCase.getActivityName());
        requestCaseOutputDTO.setWorkflow(workflow);

        BpmJob bpmJob = workflowService._nextTaskWithoutEvent(requestCase.getWfProcessID(), bcInputDTO.getDecision(), bcInputDTO.getWfComment(), requestCaseOutputDTO, aclClass);

        List<String> authors = bpmJob.getAuthors();

        List<String> readers = bpmJob.getReaders();

        requestCaseOutputDTO = (BonCommandeOutputDTO) bpmJob.getDataObject();

        requestCase = bonCommandeOutputMapper.toEntity(requestCaseOutputDTO);

        requestCase.setWfProcessID(bpmJob.getProcessID());

        requestCase.setActivityName(bpmJob.getActivityName());

        requestCase.setEndProcess(bpmJob.getEndProcess());

        requestCase.setAssignee(bpmJob.getAssignee() != null ? bpmJob.getAssignee() : null);

        requestCase = saveBonCommande(requestCase, authors, readers, aclClass, false);

        return bonCommandeOutputMapper.toDto(requestCase);

    }
    public Boolean validateRequestCase(AclClass aclClass, BonCommandeInputDTO bcInputDTO) {

        String nameRule = "save-" + aclClass.getSimpleName();

        RulesDTO rulesDTO = kernelService.rulesByName(nameRule);
        if (rulesDTO != null && rulesDTO.getSrcCode() != null) {
            List<Object> objectList = new ArrayList<>();
            objectList.add(bcInputDTO);
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

        if (bcInputDTO.getMsg() == null)
            return true;
        else
            throw new BadRequestAlertException(
                    bcInputDTO.getMsg(),
                    aclClass.getSimpleName(),
                    bcInputDTO.getMsg());


    }
    public String generateCode() {
        StringBuilder code = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            code.append(CHARACTERS.charAt(randomIndex));
        }
        String generatedCode = code.toString();

        BonCommande codeEntity = new BonCommande();
        codeEntity.setReference(generatedCode);
        bonCommandeRepository.save(codeEntity);

        return generatedCode;
    }
    public Offre getOffreByBonCommandeId(Long bonCommandeId) {
        return bonCommandeRepository.findOffreById(bonCommandeId);
    }
    public String getReferenceOffreByBonCommandeId(Long bonCommandeId) {
        BonCommande bonCommande = bonCommandeRepository.findById(bonCommandeId)
                .orElseThrow(() -> new RuntimeException("Bon de commande non trouvé avec l'ID : " + bonCommandeId));

        return bonCommande.getOffre().getReferenceoffre();
    }
    public Float getPrixOffreByBonCommandeId(Long bonCommandeId) {
        BonCommande bonCommande = bonCommandeRepository.findById(bonCommandeId)
                .orElseThrow(() -> new RuntimeException("Bon de commande non trouvé avec l'ID : " + bonCommandeId));

        return bonCommande.getOffre().getPrix();
    }


}
