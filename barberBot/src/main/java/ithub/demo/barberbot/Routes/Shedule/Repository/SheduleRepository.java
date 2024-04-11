package ithub.demo.barberbot.Routes.Shedule.Repository;

import ithub.demo.barberbot.Routes.Shedule.Shedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheduleRepository extends JpaRepository<Shedule, Long> {
}
