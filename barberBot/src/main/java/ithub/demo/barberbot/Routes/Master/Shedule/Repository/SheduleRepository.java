package ithub.demo.barberbot.Routes.Master.Shedule.Repository;

import ithub.demo.barberbot.Routes.Master.Shedule.Shedule;
import ithub.demo.barberbot.Routes.Master.Shedule.SheduleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public interface SheduleRepository extends JpaRepository<Shedule, Long> {
    List<Shedule> findByMasterId(long masterId);
    List<Shedule> findAllBySheduleStatusAndMasterId(SheduleStatus sheduleStatus, long masterId);
    Shedule findSheduleBySheduleId(long sheduleId);
}
