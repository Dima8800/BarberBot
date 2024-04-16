package ithub.demo.barberbot.Routes.Master.Shedule.Repository;

import ithub.demo.barberbot.Routes.Master.Shedule.Shedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SheduleRepository extends JpaRepository<Shedule, Long> {
    List<Shedule> findByMasterId(long masterId);
}
