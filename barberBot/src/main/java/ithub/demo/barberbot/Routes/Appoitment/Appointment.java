package ithub.demo.barberbot.Routes.Appoitment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue
    private long apopointment_id;

    @Column
    private Long clientId;

    @Column
    private Long masterId;

    @Column
    private ServiceOffBarber serviceOffBarber;

}
