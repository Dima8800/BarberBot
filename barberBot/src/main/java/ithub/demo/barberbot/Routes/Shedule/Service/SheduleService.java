package ithub.demo.barberbot.Routes.Shedule.Service;

import ithub.demo.barberbot.Routes.Shedule.Repository.SheduleRepository;
import ithub.demo.barberbot.Routes.Shedule.Shedule;
import org.jvnet.hk2.annotations.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service
public class SheduleService {

  private final SheduleRepository shedukleRepository;
  private final String ERR_TXT;

  public SheduleService(SheduleRepository shedukleRepository) {
    this.shedukleRepository = shedukleRepository;
    ERR_TXT = "извините, произошла ошибка на стороне сервера, пройдите регистрацию еще раз /n/n /barber";
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

  public String deleteShedule(){
    try {
      return null;
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }
}
