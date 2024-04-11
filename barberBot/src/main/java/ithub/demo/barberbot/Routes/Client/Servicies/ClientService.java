package ithub.demo.barberbot.Routes.Client.Servicies;

import ithub.demo.barberbot.Routes.Client.Client;
import ithub.demo.barberbot.Routes.Client.Repostiroy.ClientRepository;
import ithub.demo.barberbot.Routes.Client.Status;
import org.jvnet.hk2.annotations.Service;

@Service
public class ClientService {
    private final ClientRepository clientRepository;
    private final String ERR_TXT;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        ERR_TXT = "извините, произошла ошибка на стороне сервера, пройдите регистрацию еще раз";
    }

    public void startAcquaintance(long chatId){
        try {
            Client client = new Client();
            client.setChatId(chatId);

            clientRepository.save(client);
        }catch (Exception err){
            System.out.println(err.getMessage());
        }
    }

    public Status GetStatus(long chatId){
        try{
            return clientRepository.findById(chatId).get().getStatus();
        }catch (Exception err){
            System.out.println(err.getMessage());
            return Status.NOT_FOUND;
        }
    }

    public boolean waitName(long chatId){
        try{
            Client client = clientRepository.findById(chatId).get();
            client.setStatus(Status.wait_name);

            clientRepository.save(client);
            return true;
        }catch (Exception err){
            System.out.println(err.getMessage());
            return false;
        }
    }

    public String setNameAndWaitPhone(long chatId, String name){
        try{
            Client client = clientRepository.findById(chatId).get();

            client.setName(name);
            client.setStatus(Status.wait_phone);

            clientRepository.save(client);
            return "введите номер телефона";
        }catch (Exception err) {
            System.out.println(err.getMessage());
            return ERR_TXT;
        }
    }

    public String setPhone(long chatId, String  phone){
        try {
            Client client = clientRepository.findById(chatId).get();
            client.setPhoneNumber(phone);

            client.setStatus(Status.stopped);
            clientRepository.save(client);

            return "регистрация успешно пройдена. Если вы хотите проверить все ли верно, то воспользуйтесь командой /mydata";
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ERR_TXT;
        }
    }

    public String chekDegree(long chatId, String message){
        try{
            switch (clientRepository.findById(chatId).get().getStatus()){
                case wait_name :
                    return setNameAndWaitPhone(chatId, message);
                case wait_phone :
                    return setPhone(chatId, message);
                default:
                    return ERR_TXT;
            }
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ERR_TXT;
        }
    }

    public String getDataClient(long chatId){
        try {
            return clientRepository.findById(chatId).get().toString();
        }catch (Exception err){
            System.out.println(err.getMessage());
            return ERR_TXT;
        }
    }

}
