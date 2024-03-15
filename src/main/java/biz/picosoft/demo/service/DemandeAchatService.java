package biz.picosoft.demo.service;

import biz.picosoft.demo.client.kernel.intercomm.KernelInterface;
import biz.picosoft.demo.client.kernel.intercomm.KernelService;
import biz.picosoft.demo.client.kernel.model.acl.Access;
import biz.picosoft.demo.client.kernel.model.acl.AclClass;
import biz.picosoft.demo.client.kernel.model.acl.Permission;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.client.kernel.model.objects.*;
import biz.picosoft.demo.client.kernel.model.pm.Role;
import biz.picosoft.demo.controller.errors.BadRequestAlertException;
import biz.picosoft.demo.controller.errors.DemandeAchatErrors;
import biz.picosoft.demo.domain.DemandeAchat;
import biz.picosoft.demo.domain.enumeration.StatutDA;
import biz.picosoft.demo.repository.DemandeAchatRepository;
import biz.picosoft.demo.service.dto.DemandeAchatDTO;
import biz.picosoft.demo.service.dto.DemandeAchatInputDTO;
import biz.picosoft.demo.service.dto.DemandeAchatOutputDTO;
import biz.picosoft.demo.service.mapper.DemandeAchatInputMapper;
import biz.picosoft.demo.service.mapper.DemandeAchatMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import biz.picosoft.demo.service.mapper.DemandeAchatOutputMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    private final DemandeAchatInputMapper demandeAchatInputMapper;

    @Autowired
    private  CurrentUser currentUser;




    @Autowired
    KernelService kernelService;

    @Autowired
    private KernelInterface kernelInterface;
    @Autowired
    private EntityManager entityManager;


    public DemandeAchatService(DemandeAchatRepository demandeAchatRepository, DemandeAchatMapper demandeAchatMapper, DemandeAchatOutputMapper demandeAchatOutputMapper, DemandeAchatInputMapper demandeAchatInputMapper) {
        this.demandeAchatRepository = demandeAchatRepository;
        this.demandeAchatMapper = demandeAchatMapper;
        this.demandeAchatOutputMapper = demandeAchatOutputMapper;
        this.demandeAchatInputMapper = demandeAchatInputMapper;
    }

    public DemandeAchatOutputDTO initDemandeAchat() throws JsonProcessingException {

        // check the existance of acl class
        AclClass aclClass = kernelInterface.getaclClassByClassName(DemandeAchat.class.getName());
        if (aclClass == null) {
            throw new BadRequestAlertException("acl class not found", "Demande achat", null);
        }

        // check the existance of the authentifier sid
        String employeSid=currentUser.getEmployeSid();
        if (employeSid == null)
            throw new BadRequestAlertException("employe sid not found", "Demande achat", null);

        // check the existance of authentifier profile
        String profile=currentUser.getProfileName();
        if(profile==null)
            throw new BadRequestAlertException("employe profile not found", "Demande achat", null);


        // check can if authentifier can create Invoice
        checkRole(currentUser.getProfileName(), kernelService.demande_achat_role_canCreateDA);

        Long id = initProcess(aclClass, currentUser);

        DemandeAchatOutputDTO invoiceOutputDTO = findOneById(id, aclClass);

        if (invoiceOutputDTO.getWorkflow() == null)
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Key_wf_null,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Msg_wf_null);
        return invoiceOutputDTO;
    }
    public DemandeAchatOutputDTO findOneById(Long id, AclClass aclClass) {
        log.debug("Request to get Invoice : {}", id);

        entityManager.clear();

        DemandeAchatOutputDTO demandeAchatOutputDTO = new DemandeAchatOutputDTO();

        Optional<DemandeAchat> o = demandeAchatRepository.findById(id);

        if (!o.isPresent())
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Key_DemandeAchat_null,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Msg_DemandeAchAT_null);


        ///veifier
        ObjectDTO objectDTO = new ObjectDTO();
        objectDTO.setObjectId(o.get().getId());
        objectDTO.setObject(o.get());
        objectDTO.setWfProcessId(o.get().getWfProcessID());
        String permission = Permission.NONE.name();
        if ((aclClass != null)) {
            objectDTO.setClassId(aclClass.getId());
            ObjectsDTO g = kernelInterface.getobjectsDto(objectDTO);
            //check access after get security(list authors,readers)
            List<String> authorsSid = new ArrayList<>();
            List<String> readersSid = new ArrayList<>();
            if (g.getSecurity() != null) {
                //new ArrayList<>(sourceSet)
                authorsSid = new ArrayList<>(g.getSecurity().getAuthors());
                List<String> readers = new ArrayList<>(g.getSecurity().getReaders());
                List<String> tempReaders = new ArrayList<>(g.getSecurity().getTempreaders());
                readersSid.addAll(readers);
                readersSid.addAll(tempReaders);
            }


            permission = kernelInterface.checkSecurity(aclClass.getSimpleName(), id, currentUser.getSid());
            Access access = Access.Direct;
            //TODO check acces security level
//            if (permission.equals(Permission.NONE.name()))
//                access = kernelInterface.checkAccess(authorsSid, readersSid, o.get().getSecuriteLevel());


            if (permission.equals(Permission.NONE.name()) && access.equals(Access.NoAccess)) {
                throw new BadRequestAlertException(
                        DemandeAchatErrors.ERR_Msg_not_authorized,
                        DemandeAchatErrors.Entity_DemandeAchat,
                        DemandeAchatErrors.ERR_Key_not_authorized);
            } else if (permission.equals(Permission.NONE.name()) && access.equals(Access.AuthorIndirect)) {
                permission = Permission.WRITE.name();
            } else if (permission.equals(Permission.NONE.name()) && access.equals(Access.ReaderIndirect)) {
                permission = Permission.READ.name();
            }
            demandeAchatOutputDTO = demandeAchatOutputMapper.toDto(o.get());
//            demandeAchatOutputDTO = this.setObjectINInvoiceOutputDTO(g, demandeAchatOutputDTO);
            demandeAchatOutputDTO.setUserPermission(permission);
            if (!permission.equals((Permission.WRITE.name()))) {
                WFDTO wfdtoWithOutDecision = demandeAchatOutputDTO.getWorkflow();
                if (demandeAchatOutputDTO.getWorkflow() != null) {
                    wfdtoWithOutDecision.setDecisionsWF(new ArrayList<>());
                    demandeAchatOutputDTO.setWorkflow(wfdtoWithOutDecision);
                }
            }

        }
        //set file access token
        String datetimeExpiry = LocalDateTime.now().plusHours(1).toString();
        String objectId = o.get().getId().toString();
        String classId = aclClass.getId().toString();
        String userSecurityLevel = currentUser.getProfileSecuriteLevel().toString();
        String strToEncrypt = datetimeExpiry + "," + objectId + "," + classId + "," + userSecurityLevel + "," + permission;
//        String fileAccesToken = kernelService.encryptFileAccessToken(strToEncrypt);
//
        // TODO  setFileAccessToken
//        demandeAchatOutputDTO.setFileAccessToken(fileAccesToken);
//
        return demandeAchatOutputDTO;

    }

    public Boolean checkRole(String profile, String roleName) {
        if (profile == null)
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Msg_profile_not_found,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Key_profile_not_found);
        List<Role> roles = kernelService.findAllByProfiles(profile);
        List<String> rolesName = new ArrayList<>();
        for (Role role : roles)
            rolesName.add(role.getName());
        if (!rolesName.contains(roleName))
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Msg_not_authorized,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Key_not_authorized);
        return true;
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
    public Long submitDemandeAchat(DemandeAchatInputDTO demandeAchatInputDTO, AclClass aclClass) throws Exception {

        if (aclClass == null)
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Msg_DemandeAchAT_null,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Key_DemandeAchat_null);

        String permission = kernelService.checkSecurity(aclClass.getSimpleName(), demandeAchatInputDTO.getId(), currentUser.getSid());
        if (!permission.equals(Permission.WRITE.name()) && !permission.equals(Permission.INH_WRITE.name()))
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Msg_not_authorized,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Key_not_authorized);



        Optional<DemandeAchat> invoiceOptional = demandeAchatRepository.findById(demandeAchatInputDTO.getId());
        if (!invoiceOptional.isPresent()) {
            throw new BadRequestAlertException(
                    DemandeAchatErrors.ERR_Msg_DemandeAchAT_null,
                    DemandeAchatErrors.Entity_DemandeAchat,
                    DemandeAchatErrors.ERR_Key_DemandeAchat_null);
        }
        String activityName = invoiceOptional.get().getActivityName();
        String wfprocessID = invoiceOptional.get().getWfProcessID();

        DemandeAchat demandeAchat = null;
        Optional<DemandeAchat> optionalInvoice=demandeAchatRepository.findById(demandeAchatInputDTO.getId());
        if(optionalInvoice.isPresent()) {
            demandeAchatInputMapper.partialUpdate(optionalInvoice.get(),demandeAchatInputDTO);
            demandeAchat=optionalInvoice.get();
        } else {
            demandeAchat = demandeAchatInputMapper.toEntity(demandeAchatInputDTO);
        }
        demandeAchat.setDraft(false);
        demandeAchat.setWfProcessID(wfprocessID);
        demandeAchat = this.saveDA(demandeAchat, currentUser.getSid(), null, aclClass, currentUser);

        // TODO tags & tpref
//        if (invoiceInputDTO.getTags() != null)
//            this.setCourrierTags(aclClass, courrierCreateDto.getTags(), courrier, currentUser);
//        if (courrierCreateDto.getTpRefDtoList() != null)
//            this.setNatureCourrier(courrierCreateDto.getTpRefDtoList(), courrier);

        //set Desion, wfComment,authentifier and data(InboundByID inclus TaskByID)
        Map<String, Object> variables = new HashMap<>();
        variables.put("Decision", demandeAchatInputDTO.getDecision());
        variables.put("description", demandeAchatInputDTO.getWfCurrentComment());
        variables.put("autentifier", currentUser.getDisplayName());

        DemandeAchatOutputDTO demandeAchatOutputDTO = demandeAchatOutputMapper.toDto(demandeAchat);
        demandeAchatOutputDTO.setClassName(aclClass.getClasse());
        demandeAchatOutputDTO.setClassId(aclClass.getId());
        demandeAchatOutputDTO.setLabelClass(aclClass.getLabel());
        demandeAchatOutputDTO.setSimpleClassName(aclClass.getSimpleName());
        WFDTO workflow = new WFDTO();
        workflow.setWfProcessID(demandeAchat.getWfProcessID());
        demandeAchatOutputDTO.setWorkflow(workflow);

        variables.put("data", demandeAchatOutputDTO);

        //execuste nextTask
        //exexute drools preNextTask
        //get drools by

        String nameprocess = aclClass.getFwProcess();
        String decision = demandeAchatInputDTO.getDecision();
//        String ruleName = "name-rule-bpm-" + nameprocess + "-" + activityName + "-" + decision;
//        System.out.println("ruleName//" + ruleName);
//        ruleName = ruleName.replaceAll(" ", "");
        //get name rule
//        try {
//            Object nameRuleInput = kernelService.getInput(demandeAchat.getWfProcessID(), ruleName, "string");
//            String nameRule;
//            if (nameRuleInput == null) {
//                nameRule = "bpm-" + nameprocess + "-" + activityName + "-" + decision;
//                nameRule = nameRule.replaceAll(" ", "");
//                kernelService.rulesByName(nameRule);
//
//            } else {
//                nameRule = (String) nameRuleInput;
//                RulesDTO rulesDTO = kernelService.rulesByName(nameRule);
//                if (rulesDTO != null && rulesDTO.getSrcCode() != null) {
//                    List<Object> objectList = new ArrayList<>();
//                    objectList.add(demandeAchatInputDTO);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        kernelService._nextTask(variables);

        return demandeAchat.getId();
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
