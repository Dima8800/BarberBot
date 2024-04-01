package ithub.demo.barberbot.Routes;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.awt.*;

@Data
public class Shedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer sheduleId;

    @Column
    private String masterId;

    @Column
    private String lockedTime;
}
