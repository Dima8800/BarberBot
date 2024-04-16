package ithub.demo.barberbot.Routes.Master.Shedule;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Shedule {
    @Id
    private long sheduleId;

    @Column
    private long masterId;

    @Column
    private LocalDateTime lockedTime;
}
