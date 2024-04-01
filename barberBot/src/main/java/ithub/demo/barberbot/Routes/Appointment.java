package ithub.demo.barberbot.Routes;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer apopointment_id;

    @Column
    private Integer clientId;

    @Column
    private Integer masterId;

    @Column
    private Integer service_ID;

}
