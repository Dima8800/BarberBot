package ithub.demo.barberbot.App.Bot;

import ithub.demo.barberbot.App.Config.BotConfig;
import ithub.demo.barberbot.Routes.Client.Client;
import ithub.demo.barberbot.Routes.Client.Servicies.ClientService;
import ithub.demo.barberbot.Routes.Client.Status;
import ithub.demo.barberbot.Routes.Master.Servicies.MasterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig config;

    private final String HELP_TEXT;

    private final ClientService clientService;

    private final MasterService masterService;

    public TelegramBot(BotConfig config, ClientService clientService, MasterService masterService) {
        this.config = config;
        this.clientService = clientService;
        this.masterService = masterService;
        HELP_TEXT = "\nЭтот бот создан для бронирования в барбер шоп\n\nДля использование бота необходима регистраиция. Ее можно сделать по команде /register или в меню по этой же команде" +
                "\n\n Команды для использования бота:" +
                "\n/start - начать общение с ботом" +
                "\n/register - Регистрация" +
                "\n/mydata - получить данные о себе" +
                "\n/help - как пользоваться ботом бота" +
                "\n\n Проект колледжа IT-hub Екатеринбурга";


        List<BotCommand> listOfCommands = new ArrayList();

        listOfCommands.add(new BotCommand("/start", "начать общение с ботом"));
        listOfCommands.add(new BotCommand("/register", "Регистрация"));
        listOfCommands.add(new BotCommand("/mydata", "получить данные о себе"));
        listOfCommands.add(new BotCommand("/help", "как пользоваться ботом бота"));
        listOfCommands.add(new BotCommand("/settigs", "настройки бота"));
        listOfCommands.add(new BotCommand("/barber", "Стать барбером"));
        listOfCommands.add(new BotCommand("/appoiment", "записаться к барберу"));


        try{
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        }catch (TelegramApiException err){
            log.error("Error set command: " + err.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()){
            String message = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (message){
                case "/start":
                    clientService.startAcquaintance(chatId);
                    StartCommandReceived(chatId,update.getMessage().getChat().getFirstName());
                    break;
                case "/help":
                    SendMessage(chatId,HELP_TEXT);
                    break;
                case "/register":
                    if (clientService.waitName(chatId)){
                        SendMessage(chatId,"Введите ваше имя");
                        break;
                    }
                    SendMessage(chatId,"повторите команду \n\n/register");
                    break;
                case "/mydata":
                    if (masterService.chekBarberorNot(chatId)){
                        SendMessage(chatId,clientService.getDataClient(chatId));
                        break;
                    }
                    SendMessage(chatId, masterService.BarberData(chatId));
                    break;
                case "/barber":
                    SendMessage(chatId, masterService.BecomeBarber(chatId, "@" + update.getMessage().getFrom().getUserName()));
                    break;
                case "/appoitment":
                    break;
                case "/shedule":
                default:
                    if (masterService.chekBarberorNot(chatId)){
                        SendMessage(chatId,masterService.chekStatus(chatId, message));
                        break;
                    }
                    SendMessage(chatId, clientService.chekDegree(chatId, message));
                    break;
            }
        }
    }

    private void StartCommandReceived(long chatId,String name){
        String answer = "Привет " + name + ", давай знакомиться." + HELP_TEXT;
        SendMessage(chatId, answer);

    }

    private void SendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
        }catch (TelegramApiException err){
//            log.error("Error occurred: " + err.getMessage());
        }
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
}
