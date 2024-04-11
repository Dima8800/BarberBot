package ithub.demo.barberbot.Routes.Master.Repository;

import ithub.demo.barberbot.Routes.Master.Master;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository extends JpaRepository<Master, Long> {
}
