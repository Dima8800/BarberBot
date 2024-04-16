package ithub.demo.barberbot.Routes.Master.Shedule.Service;

import ithub.demo.barberbot.Routes.Master.Shedule.Repository.SheduleRepository;
import ithub.demo.barberbot.Routes.Master.Shedule.Shedule;
import org.jvnet.hk2.annotations.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class SheduleService {

  private final SheduleRepository shedukleRepository;
  private final String ERR_TXT;

  public SheduleService(SheduleRepository shedukleRepository, String errTXT) {
    this.shedukleRepository = shedukleRepository;
    ERR_TXT = errTXT + "\n\n/shedule";
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
      shedukleRepository.save(shedule);
      return "Успешно создано\n\n все записи можете посмотреть по /time";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT + "или не правильный формат даты";
    }
  }

  public String getAllForMasterId(long chatId){
    try {
      return allSheduleForTXT(shedukleRepository.findByMasterId(chatId))
              + "Вы можете удалить запись, просто написав номер этой записи," +
              " если вас все устраивает, напишите \"0\"";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String deleteShedule(long SheduleId){
    try {
      shedukleRepository.deleteById(SheduleId);
      return "Запись успешно удалена";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  private String allSheduleForTXT(List<Shedule> shedules){
    String returnMessage = null;

    for (int i = 0; i < shedules.size(); i++){
      returnMessage += shedules.get(i).toString() + "\n\n";
    }
    return returnMessage;
  }
}
