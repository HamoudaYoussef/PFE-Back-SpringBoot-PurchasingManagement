package biz.picosoft.demo.config.logging;


import biz.picosoft.demo.config.logging.domain.AopLogging;
import biz.picosoft.demo.service.mapper.EntityMapper;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AopLogging} and its DTO {@link AopLoggingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AopLoggingMapper extends EntityMapper<AopLoggingDTO, AopLogging> {}
