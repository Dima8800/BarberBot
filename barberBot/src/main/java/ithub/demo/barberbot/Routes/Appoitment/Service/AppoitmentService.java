package ithub.demo.barberbot.Routes.Appoitment.Service;

import ithub.demo.barberbot.Routes.Appoitment.Repository.AppoitmentRepository;
import org.jvnet.hk2.annotations.Service;

@Service
public class AppoitmentService {

  private final AppoitmentRepository appoitmentRepository;

  public AppoitmentService(AppoitmentRepository appoitmentRepository) {
    this.appoitmentRepository = appoitmentRepository;
  }
}
