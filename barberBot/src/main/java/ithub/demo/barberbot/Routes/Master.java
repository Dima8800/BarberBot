package ithub.demo.barberbot.Routes;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class Master {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer masterId;

    @Column
    private String masterName;

    @Column
    private String contact;

    @Column
    private String description;
}