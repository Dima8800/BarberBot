package ithub.demo.barberbot.Routes;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;

@Entity
public class Shedule {
    @Id
    private long sheduleId;

    @Column
    private String masterId;

    @Column
    private String lockedTime;
}
