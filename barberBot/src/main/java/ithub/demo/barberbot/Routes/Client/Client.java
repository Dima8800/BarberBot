package ithub.demo.barberbot.Routes.Client;

import ithub.demo.barberbot.Routes.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;


@Data
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long clientId;

    @Column
    private String name;

    @Column
    private Integer chatId;

    @Column
    private String telegramNickname;

    @Column
    @NonNull
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Status status;
}
