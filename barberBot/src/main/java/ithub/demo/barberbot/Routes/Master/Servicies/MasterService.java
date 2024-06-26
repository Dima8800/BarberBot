package ithub.demo.barberbot.Routes.Master.Servicies;

import ithub.demo.barberbot.Routes.Master.Master;
import ithub.demo.barberbot.Routes.Master.MasterStatus;
import ithub.demo.barberbot.Routes.Master.Repository.MasterRepository;
import ithub.demo.barberbot.Routes.Master.Shedule.Service.SheduleService;
import org.aspectj.bridge.IMessage;
import org.jvnet.hk2.annotations.Service;

import java.util.List;

@Service
public class MasterService {
  private final MasterRepository masterRepository;

  private final SheduleService sheduleService;

  private final String ERR_TXT;

  private final String masterInfoErr;

  public MasterService(MasterRepository masterRepository, SheduleService sheduleService, String errTXT, String masterInfoErr) {
    this.masterRepository = masterRepository;
    this.sheduleService = sheduleService;
    ERR_TXT  = errTXT + "\n\n /barber или повторите прошлую команду";
      this.masterInfoErr = masterInfoErr;
  }

  public String BecomeBarber(long chatId, String linkTelegram) {
    try {
      Master master = new Master();
      master.setChatId(chatId);

      master.setLink(linkTelegram);
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
        case appoitment:
          System.out.println("appoiment");
          return sheduleService.setShedule(chatId,message);
        case time:
          return getTime(master, message);
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

  public String setAppoitment(long chatId){
    try{
      Master master = masterRepository.findById(chatId).get();
      master.setStatus(MasterStatus.appoitment);

      masterRepository.save(master);
      return "напишите дату и время\n\n " +
        "формат: 2024 02 02 16 30";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String getAllShedule(long chatId){
    try {
      Master master = masterRepository.findById(chatId).get();
      master.setStatus(MasterStatus.time);

      masterRepository.save(master);
      return sheduleService.getAllByMasterId(chatId);
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  private String getTime(Master master, String shedule){
    try{
      if (shedule.equals("0")){
        master.setStatus(MasterStatus.Stopped);
        masterRepository.save(master);
        return "Отлично, что ваc все устривает";
      }
      sheduleService.deleteShedule(Long.parseLong(shedule));
      return sheduleService.getAllByMasterId(master.getChatId());
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String getAllMasters(){
    try {
      String returnMessage = "Напишите @ мастера, который вам понравится\n\n";
      List<Master> masters = masterRepository.findAll();

      for (Master master : masters){
        returnMessage += master.toString() + "\n";
      }

      return returnMessage;
    }catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String getTimeByBarber(String link){
    try {
      Master master = masterRepository.findMasterByLink(link);

      if (master == null){
        return "неправильно выбран мастер";
      }

      return sheduleService.getFreeTimeByMasterIdForUser(master.getChatId());
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }
}