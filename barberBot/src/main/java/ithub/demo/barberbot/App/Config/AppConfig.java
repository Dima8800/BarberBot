package ithub.demo.barberbot.App.Config;

import ithub.demo.barberbot.Routes.Client.Repostiroy.ClientRepository;
import ithub.demo.barberbot.Routes.Client.Servicies.ClientService;
import ithub.demo.barberbot.Routes.Master.Repository.MasterRepository;
import ithub.demo.barberbot.Routes.Master.Servicies.MasterService;
import ithub.demo.barberbot.Routes.Master.Shedule.Repository.SheduleRepository;
import ithub.demo.barberbot.Routes.Master.Shedule.Service.SheduleService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.context.annotation.Bean;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    private String errTXT = "извините, произошла ошибка на стороне сервера, пройдите регистрацию еще раз";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedOrigins("*");
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepository) {
        return new ClientService(clientRepository,errTXT);
    }

    @Bean
    public MasterService masterService(MasterRepository masterRepository, SheduleService sheduleService) {
        return new MasterService(masterRepository, sheduleService, errTXT);
    }

    @Bean
    public SheduleService sheduleService(SheduleRepository sheduleRepository){
        return new SheduleService(sheduleRepository, errTXT);
    }
}
