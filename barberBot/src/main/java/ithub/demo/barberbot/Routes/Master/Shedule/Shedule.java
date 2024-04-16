package ithub.demo.barberbot.Routes.Master.Shedule;

import jakarta.persistence.*;
import lombok.Data;

import java.awt.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Shedule {
    @Id
    @GeneratedValue
    private long sheduleId;

    @Column
    private long masterId;

    @Column
    private LocalDateTime lockedTime;

    @Column
    private SheduleStatus sheduleStatus;

    @Override
    public String toString(){
        return sheduleId + ". " + lockedTime.toString();
    }
}
