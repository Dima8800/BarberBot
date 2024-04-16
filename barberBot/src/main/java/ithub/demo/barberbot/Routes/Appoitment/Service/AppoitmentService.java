package ithub.demo.barberbot.Routes.Appoitment.Service;

import ithub.demo.barberbot.Routes.Appoitment.Repository.AppoitmentRepository;
import ithub.demo.barberbot.Routes.Master.Shedule.Service.SheduleService;
import org.jvnet.hk2.annotations.Service;

@Service
public class AppoitmentService {

  private final AppoitmentRepository appoitmentRepository;
  private final SheduleService sheduleService;

  private final String ERRTXT;

  public AppoitmentService(AppoitmentRepository appoitmentRepository, SheduleService sheduleService, String errtxt) {
    this.appoitmentRepository = appoitmentRepository;
      this.sheduleService = sheduleService;
      ERRTXT = errtxt;
  }

  public String startAppoiment(){
    try {
      return sheduleService.getAllDate();
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERRTXT;
    }
  }
}
