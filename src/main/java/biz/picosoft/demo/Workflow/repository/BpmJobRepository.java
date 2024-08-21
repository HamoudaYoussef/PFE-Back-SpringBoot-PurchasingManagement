package biz.picosoft.demo.Workflow.repository;

import biz.picosoft.demo.Workflow.domain.BpmJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the FraisMission entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BpmJobRepository extends JpaRepository<BpmJob, Long>, JpaSpecificationExecutor<BpmJob> {

  Optional<BpmJob> findByObjectIDAndClassID(Long objectID, Long classID);

  List<BpmJob> findAllByAssigneeAndClassID(String assigne, Long classID);

  @Query(value = "select DISTINCT b.activityName from BpmJob b where b.classID=:classId")
  List<String> findSteps(@Param("classId") Long classId);

}
