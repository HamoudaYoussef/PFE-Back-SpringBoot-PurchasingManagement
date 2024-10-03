package biz.picosoft.demo.Workflow.service.mapper;


import biz.picosoft.demo.Workflow.domain.BpmJob;
import biz.picosoft.demo.Workflow.service.dto.BpmJobDTO;
import biz.picosoft.demo.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link BpmJob} and its DTO {@link BpmJobDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public abstract class BpmJobMapper implements EntityMapper<BpmJobDTO, BpmJob> {

    public abstract BpmJobDTO toDto(BpmJob bpmJob);


    BpmJob fromId(Long id) {
        if (id == null) {
            return null;
        }
        BpmJob bpmJob = new BpmJob();
        bpmJob.setId(id);
        return bpmJob;
    }
}
