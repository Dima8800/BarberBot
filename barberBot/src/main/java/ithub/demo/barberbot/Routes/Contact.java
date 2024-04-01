package ithub.demo.barberbot.Routes;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String contact;

    @Column
    private String telegramLink;

    @Column
    private String vkLink;

    @Column
    private String whatsUpLink;

    @Column
    private Integer ownerId;
}
