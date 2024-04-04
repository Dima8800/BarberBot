package ithub.demo.barberbot.Routes.Client.Repostiroy;

import ithub.demo.barberbot.Routes.Client.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
