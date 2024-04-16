package ithub.demo.barberbot.Routes.Master.Shedule.Service;

import ithub.demo.barberbot.Routes.Master.Servicies.MasterService;
import ithub.demo.barberbot.Routes.Master.Shedule.Repository.SheduleRepository;
import ithub.demo.barberbot.Routes.Master.Shedule.Shedule;
import ithub.demo.barberbot.Routes.Master.Shedule.SheduleStatus;
import org.jvnet.hk2.annotations.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SheduleService {

  private final SheduleRepository sheduleRepository;
  private final String ERR_TXT;
  private final String ERR_TXT_USER;
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");

  public SheduleService(SheduleRepository shedukleRepository, String errTXT, String errTxtUser) {
    this.sheduleRepository = shedukleRepository;
    ERR_TXT = errTXT + " \n\n/shedule";
      ERR_TXT_USER = errTxtUser;
  }

  public String setShedule(long chatId,String date){
    try {
      Shedule shedule = new Shedule();
      int[] dateInt = Arrays.stream(date.split(" "))
        .mapToInt(Integer::parseInt)
        .toArray();

      LocalDateTime dateTime = LocalDateTime.of(dateInt[0], dateInt[1], dateInt[2], dateInt[3], dateInt[4]);
      shedule.setLockedTime(dateTime);

      shedule.setMasterId(chatId);
      shedule.setSheduleStatus(SheduleStatus.free);

      sheduleRepository.save(shedule);
      return "Успешно создано\n\n все записи можете посмотреть по /time";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT + "или не правильный формат даты";
    }
  }

  public String getAllForMasterId(long chatId){
    try {
      return allSheduleForTXT(sheduleRepository.findByMasterId(chatId))
              + " Вы можете удалить запись, просто написав номер этой записи," +
              " если вас все устраивает, напишите \"0\"";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String deleteShedule(long SheduleId){
    try {
      sheduleRepository.deleteById(SheduleId);
      return "Запись успешно удалена";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  private String allSheduleForTXT(List<Shedule> shedules){
    String returnMessage = "Ваше свободное время:\n\n";

    for (int i = 0; i < shedules.size(); i++){
      returnMessage += shedules.get(i).toString() + "\n\n";
    }
    return returnMessage;
  }

  public String getAllDate(){
    try {
      List<Shedule> shedules = sheduleRepository.findAllBySheduleStatus(SheduleStatus.free);
      return shedules.stream()
              .map(shedule -> shedule.getSheduleId() + " - "
                      + shedule.getLockedTime().format(formatter))
              .collect(Collectors.joining("\n"));
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }
}
