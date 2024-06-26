package ithub.demo.barberbot.Routes.Client.Servicies;

import ithub.demo.barberbot.Routes.Client.Client;
import ithub.demo.barberbot.Routes.Client.Repostiroy.ClientRepository;
import ithub.demo.barberbot.Routes.Client.Status;
import org.jvnet.hk2.annotations.Service;

import java.lang.ref.Cleaner;

@Service
public class ClientService {
  private final ClientRepository clientRepository;
  private final String ERR_TXT;

  public ClientService(ClientRepository clientRepository, String errTXT) {
    this.clientRepository = clientRepository;
    ERR_TXT = errTXT + " \n\n/register";
  }

  public void deleteClient(long chatId) {
    try {
      clientRepository.deleteById(chatId);
    } catch (Exception err) {
      System.out.println(err.getMessage());
    }
  }

  public void startAcquaintance(long chatId) {
    try {
      Client client = new Client();
      client.setChatId(chatId);

      clientRepository.save(client);
    } catch (Exception err) {
      System.out.println(err.getMessage());
    }
  }

  public boolean waitName(long chatId) {
    try {
      Client client = clientRepository.findById(chatId).get();
      client.setStatus(Status.wait_name);

      clientRepository.save(client);
      return true;
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return false;
    }
  }

  public String setNameAndWaitPhone(long chatId, String name) {
    try {
      Client client = clientRepository.findById(chatId).get();

      client.setName(name);
      client.setStatus(Status.wait_phone);

      clientRepository.save(client);
      return "введите номер телефона";
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String setPhone(long chatId, String phone) {
    try {
      Client client = clientRepository.findById(chatId).get();
      client.setPhoneNumber(phone);

      client.setStatus(Status.stopped);
      clientRepository.save(client);

      return "регистрация успешно пройдена. Если вы хотите проверить все ли верно, то воспользуйтесь командой /mydata";
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String chekDegree(long chatId, String message) {
    try {
      switch (clientRepository.findById(chatId).get().getStatus()) {
        case wait_name:
          return setNameAndWaitPhone(chatId, message);
        case wait_phone:
          return setPhone(chatId, message);
        case wait_barber:
          return "barber";
        case wait_time:
          return "timeForBarber";
        case waitService:
          return "Service";
        default:
          return ERR_TXT;
      }
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public void setService(long chatId){
    try {
      Client client = clientRepository.findById(chatId).get();
      client.setStatus(Status.waitService);

      clientRepository.save(client);
    }catch (Exception err){
      System.out.println(err.getMessage());
    }
  }

  public String getDataClient(long chatId) {
    try {
      return clientRepository.findById(chatId).get().toString();
    } catch (Exception err) {
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public String startAppoitment(){
    try {
      return "У вас есть мастер, к которому вы хотите записаться?" +
        "\n\n/master - что бы выбрать из списка" +
        "\n/timeForAppoitment - что бы выбрать свободное время";
    }catch (Exception err){
      System.out.println(err.getMessage());
      return ERR_TXT;
    }
  }

  public void setStatusBarber(long chatId){
    try {
      Client client = clientRepository.findById(chatId).get();
      client.setStatus(Status.wait_barber);

      clientRepository.save(client);
    }catch (Exception err){
      System.out.println(err.getMessage());
    }
  }

  public void setTime(long chatId){
    try {
      Client client = clientRepository.findById(chatId).get();
      client.setStatus(Status.wait_time);

      clientRepository.save(client);
    }catch (Exception err){
      System.out.println(err.getMessage());
    }
  }

  public void setStopped(long chatId){
    try{
      Client client = clientRepository.findById(chatId).get();
      client.setStatus(Status.stopped);

      clientRepository.save(client);
    }catch (Exception err){
      System.out.println(err.getMessage());
    }
  }
}
