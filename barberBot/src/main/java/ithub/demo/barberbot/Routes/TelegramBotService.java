package ithub.demo.barberbot.Routes;

import ithub.demo.barberbot.App.Config.BotConfig;
import ithub.demo.barberbot.Routes.Appoitment.Service.AppoitmentService;
import ithub.demo.barberbot.Routes.Client.Servicies.ClientService;
import ithub.demo.barberbot.Routes.Master.Servicies.MasterService;
import ithub.demo.barberbot.Routes.Master.Shedule.Service.SheduleService;
import ithub.demo.barberbot.Routes.Master.Shedule.Shedule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class TelegramBotService extends TelegramLongPollingBot {

  private final BotConfig config;

  private final String HELP_TEXT;

  private final ClientService clientService;

  private final MasterService masterService;

  private final AppoitmentService appoitmentService;

  private final SheduleService sheduleService;

  public TelegramBotService(BotConfig config, ClientService clientService, MasterService masterService, AppoitmentService appoitmentService, SheduleService sheduleService) {
    this.config = config;
    this.clientService = clientService;
    this.masterService = masterService;
    this.appoitmentService = appoitmentService;
    this.sheduleService = sheduleService;
    HELP_TEXT = "\nЭтот бот создан для бронирования в барбер шоп\n\n" +
      "Для использование бота необходима регистраиция. Ее можно сделать по команде " +
      "/register или в меню по этой же команде" +
      "\n\n Команды для использования бота:" +
      "\n/start - начать общение с ботом" +
      "\n/register - Регистрация" +
      "\n /appoiment - записаться к барберу" +
      "\n/help - как пользоваться ботом бота" +
      "\n\n Проект колледжа IT-hub Екатеринбурга";


    List<BotCommand> listOfCommands = new ArrayList();

    listOfCommands.add(new BotCommand("/register", "Регистрация"));
    listOfCommands.add(new BotCommand("/help", "как пользоваться ботом бота"));
    listOfCommands.add(new BotCommand("/appoiment", "записаться к барберу"));
    listOfCommands.add(new BotCommand("/master", "записаться к любимому барберу"));
    listOfCommands.add(new BotCommand("/timeForAppoitment", "записаться к на свободное время"));

    try {
      this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
    } catch (TelegramApiException err) {
      log.error("Error set command: " + err.getMessage());
    }
  }

  @Override
  public void onUpdateReceived(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String message = update.getMessage().getText();
      long chatId = update.getMessage().getChatId();

      switch (message) {
        case "/start":
          clientService.startAcquaintance(chatId);
          StartCommandReceived(chatId, update.getMessage().getChat().getFirstName());
          break;
        case "/help":
          SendMessage(chatId, HELP_TEXT);
          break;
        case "/register":
          if (clientService.waitName(chatId)) {
            SendMessage(chatId, "Введите ваше имя");
            break;
          }
          SendMessage(chatId, "повторите команду \n\n/register");
          break;
        case "/mydata":
          if (masterService.chekBarberorNot(chatId)) {
            SendMessage(chatId, masterService.BarberData(chatId));
            break;
          }
          SendMessage(chatId, clientService.getDataClient(chatId));
          break;
        case "/barber":
          clientService.deleteClient(chatId);
          SendMessage(chatId, masterService.BecomeBarber(chatId, "@" + update.getMessage().getFrom().getUserName()));
          break;
        case "/appoiment":
          SendMessage(chatId, clientService.startAppoitment());
          break;
        case "/shedule":
          if (masterService.chekBarberorNot(chatId)) {
            SendMessage(chatId, "вы не можете выставлять время для записи\n\n" +
              "вы можете записаться по команде /appoitment");
            break;
          }
          SendMessage(chatId, masterService.setAppoitment(chatId));
          break;
        case "/time":
          SendMessage(chatId, masterService.getAllShedule(chatId));
          break;
        case "/master":
          clientService.setStatusBarber(chatId);
          SendMessage(chatId, masterService.getAllMasters());
          break;
        case "/timeForAppoitment":
          SendMessage(chatId, "отстань, не работает ничего");
          break;
        default:
          if (masterService.chekBarberorNot(chatId)) {
            SendMessage(chatId, masterService.chekStatus(chatId, message));
            break;
          }

          String service = clientService.chekDegree(chatId, message);
          if (service.equals("barber")) {
            clientService.setTime(chatId);
            SendMessage(chatId, masterService.getTimeByBarber(message));
            break;
          }

          if (service.equals("timeForBarber")) {
            Shedule shedule = sheduleService.book(Long.parseLong(message));
            clientService.setService(chatId);

            SendMessage(chatId, appoitmentService.bookTime(chatId, shedule.getMasterId()));
            break;
          }

          if (service.equals("Service")){
            String answer = appoitmentService.setService(chatId, Integer.parseInt(message));
            if (answer.equals("не верный ввод, повторите")){
              SendMessage(chatId, answer);
              break;
            }
            clientService.setStopped(chatId);
            SendMessage(chatId, answer);
            break;
          }

          SendMessage(chatId, service);
          break;
      }
    }
  }

  private void StartCommandReceived(long chatId, String name) {
    String answer = "Привет " + name + ", давай знакомиться." + HELP_TEXT;
    SendMessage(chatId, answer);

  }

  private void SendMessage(long chatId, String textToSend) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText(textToSend);

    try {
      execute(message);
    } catch (TelegramApiException err) {
//            log.error("Error occurred: " + err.getMessage());
    }
  }

  @Override
  public String getBotToken() {
    return config.getToken();
  }

  @Override
  public String getBotUsername() {
    return config.getBotName();
  }
}
