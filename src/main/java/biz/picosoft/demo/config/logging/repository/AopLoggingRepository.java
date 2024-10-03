package biz.picosoft.demo.config.logging.repository;

import biz.picosoft.demo.config.logging.domain.AopLogging;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the AopLogging entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AopLoggingRepository extends JpaRepository<AopLogging, Long>, JpaSpecificationExecutor<AopLogging> {}
