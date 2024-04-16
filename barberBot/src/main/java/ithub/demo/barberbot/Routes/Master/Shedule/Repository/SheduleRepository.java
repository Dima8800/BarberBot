package ithub.demo.barberbot.Routes.Master.Shedule.Repository;

import ithub.demo.barberbot.Routes.Master.Shedule.Shedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheduleRepository extends JpaRepository<Shedule, Long> {
}
