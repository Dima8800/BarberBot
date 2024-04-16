package ithub.demo.barberbot.Routes.Appoitment;

import jakarta.persistence.*;


@Entity
public class Appointment {
    @Id
    private long apopointment_id;

    @Column
    private Integer clientId;

    @Column
    private Integer masterId;

    @Column
    private ServiceOffBarber serviceOffBarber;

}
