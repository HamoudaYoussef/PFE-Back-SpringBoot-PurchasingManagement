package biz.picosoft.demo.Workflow.service;



import biz.picosoft.demo.Workflow.domain.BpmJob;
import biz.picosoft.demo.Workflow.repository.BpmJobRepository;
import biz.picosoft.demo.Workflow.service.dto.BpmJobDTO;
import biz.picosoft.demo.Workflow.service.mapper.BpmJobMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
public class BpmJobService {

    private final Logger log = LoggerFactory.getLogger(BpmJobService.class);

    private final BpmJobRepository bpmJobRepository;

    private final BpmJobMapper bpmJobMapper;

    public BpmJobService(BpmJobRepository bpmJobRepository, BpmJobMapper bpmJobMapper) {
        this.bpmJobRepository = bpmJobRepository;
        this.bpmJobMapper = bpmJobMapper;
    }

    /**
     * Save a bpmJob.
     *
     * @param bpmJobDTO the entity to save.
     * @return the persisted entity.
     */
    public BpmJobDTO save(BpmJobDTO bpmJobDTO) {
        log.debug("Request to save BpmJob : {}", bpmJobDTO);
        BpmJob bpmJob = bpmJobMapper.toEntity(bpmJobDTO);
        bpmJob = bpmJobRepository.save(bpmJob);
        return bpmJobMapper.toDto(bpmJob);
    }

    /**
     * Save a bpmJob.
     *
     * @return the persisted entity.
     */
    public BpmJob _persisteBpmJob(JSONObject object, String activityName, String assignee, Boolean endProcess, String processInstanceId, Long objectID, Long classID) {


        BpmJob bpmJob=  new BpmJob();
        Optional<BpmJob> bpmJobOptional = bpmJobRepository.findByObjectIDAndClassID(objectID, classID);
        if(bpmJobOptional.isPresent()){
         bpmJob=  bpmJobOptional.get();
        }
        bpmJob.setEndProcess(endProcess);
        bpmJob.setActivityName(activityName);
        bpmJob.setAssignee(assignee);
        bpmJob.setClassID(classID);
        bpmJob.setData(object.toJSONString());
        bpmJob.setObjectID(objectID);
        bpmJob.setProcessID(processInstanceId);

        log.debug("Request to save BpmJob : {}", bpmJob);
        bpmJob = bpmJobRepository.save(bpmJob);

        return bpmJob;
    }

    /**
     * Get all the bpmJob.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<BpmJobDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BpmJob");
        return bpmJobRepository.findAll(pageable)
                .map(bpmJobMapper::toDto);
    }


    /**
     * Get one bpmJob by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<BpmJobDTO> findOne(Long id) {
        log.debug("Request to get BpmJob : {}", id);
        return bpmJobRepository.findById(id)
                .map(bpmJobMapper::toDto);
    }

  @Transactional(readOnly = true)
  public Optional<BpmJobDTO> findByObjectIdAndClassId(Long objectId, Long classId) {
    log.debug("Request to get BpmJob : {}", objectId, classId);
    return bpmJobRepository.findByObjectIDAndClassID(objectId, classId)
      .map(bpmJobMapper::toDto);
  }

    /**
     * Delete the bpmJob by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete BpmJob : {}", id);
        bpmJobRepository.deleteById(id);
    }
}
