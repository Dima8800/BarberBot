package ithub.demo.barberbot.Routes.Appoitment.Repository;

import ithub.demo.barberbot.Routes.Appoitment.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppoitmentRepository extends JpaRepository<Appointment, Long> {
  Appointment findAppointmentByClientId(long clientId);
}
