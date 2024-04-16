package ithub.demo.barberbot.Routes.Appoitment.Service;

import ithub.demo.barberbot.Routes.Appoitment.Appointment;
import ithub.demo.barberbot.Routes.Appoitment.Repository.AppoitmentRepository;
import ithub.demo.barberbot.Routes.Appoitment.ServiceOffBarber;
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

  public String bookTime(long clientId, long masterId) {
    try{
      Appointment appointment = new Appointment();
      appointment.setMasterId(masterId);
      appointment.setClientId(clientId);

      appoitmentRepository.save(appointment);
      return "выберите сервис(напишите цифру)\n\n1.Стрижка\n2.Под ноль\n3.Стрижка бороды";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERRTXT;
    }
  }

  public String setService(long clientId, int service){
    try {
      Appointment appointment = appoitmentRepository.findAppointmentByClientId(clientId);
      switch (service){
        case 1:
          appointment.setServiceOffBarber(ServiceOffBarber.haircut);
          break;
        case 2:
          appointment.setServiceOffBarber(ServiceOffBarber.shaving);
          break;
        case 3:
          appointment.setServiceOffBarber(ServiceOffBarber.beard);
          break;
        default:
          return "не верный ввод, повторите";
      }
      appoitmentRepository.save(appointment);
      return "Ваша запись успешно создана";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERRTXT;
    }
  }
}
