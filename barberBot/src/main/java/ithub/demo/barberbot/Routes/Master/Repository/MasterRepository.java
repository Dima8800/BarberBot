package ithub.demo.barberbot.Routes.Master.Repository;

import ithub.demo.barberbot.Routes.Master.Master;
import ithub.demo.barberbot.Routes.Master.Servicies.MasterService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterRepository extends JpaRepository<Master, Long> {
  Master findMasterByLink(String link);
}
