package ithub.demo.barberbot.Routes.Client;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    private Long chatId;

    @Column
    private String name;

    @Column
    private String telegramNickname;

    @Column
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString(){
        return getName() + ", " +
                getPhoneNumber();
    }
}
