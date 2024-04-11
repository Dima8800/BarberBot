package ithub.demo.barberbot.Routes.Master.Servicies;

import ithub.demo.barberbot.Routes.Master.Master;
import ithub.demo.barberbot.Routes.Master.MasterStatus;
import ithub.demo.barberbot.Routes.Master.Repository.MasterRepository;
import org.jvnet.hk2.annotations.Service;

@Service
public class MasterService {
  private final MasterRepository masterRepository;
  private final String ERR_TXT;

  public MasterService(MasterRepository masterRepository) {
    this.masterRepository = masterRepository;
    ERR_TXT = "извините, произошла ошибка на стороне сервера, пройдите регистрацию еще раз /n/n /barber";
  }

  public String BecomeBarber(long chatId, String linkTelegram) {
    try {
      Master master = new Master();
      master.setChatId(chatId);

      master.setContact(linkTelegram);
      master.setStatus(MasterStatus.wait_name);

      masterRepository.save(master);
      return "напишите ваше имя";
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String chekStatus(long chatId, String message) {
    try {
      Master master = masterRepository.findById(chatId).get();
      switch (master.getStatus()) {
        case wait_name:
          return setName(message, master);
        case wait_description:
          return setDescription(message, master);
        case wait_contact:
          return setContact(message, master);
        default:
          return ERR_TXT;
      }
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String setName(String name, Master master) {
    try {
      master.setMasterName(name);
      master.setStatus(MasterStatus.wait_description);

      masterRepository.save(master);
      return "напишите ваше описание";
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String setDescription(String description, Master master) {
    try {
      master.setDescription(description);
      master.setStatus(MasterStatus.wait_contact);

      masterRepository.save(master);
      return "введите ваш номер телефона";
    } catch (Exception err) {
      return ERR_TXT;
    }
  }

  public String BarberData(long chayId) {
    try {
      return masterRepository.findById(chayId).get().toString();
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String setContact(String phone, Master master) {
    try {
      master.setContact(phone);
      master.setStatus(MasterStatus.Stopped);

      masterRepository.save(master);
      return "Ваш профиль успешно создан\n\n"
        + master.toString()
        + "\n\n если что то хотите исправить, то пройдите создание заново /barber";
    } catch (Exception err) {
      return ERR_TXT;
    }
  }

  public boolean chekBarberorNot(long chatId) {
    try {
      if (masterRepository.findById(chatId).get().getStatus() == MasterStatus.Stopped) {
        return false;
      }
      return true;
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return false;
    }
  }
}
