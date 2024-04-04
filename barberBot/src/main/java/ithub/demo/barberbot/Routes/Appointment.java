package ithub.demo.barberbot.Routes;

import jakarta.persistence.*;
import lombok.Data;


@Entity
public class Appointment {
    @Id
    private long apopointment_id;

    @Column
    private Integer clientId;

    @Column
    private Integer masterId;

    @Column
    private Integer service_ID;

}
