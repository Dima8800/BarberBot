package ithub.demo.barberbot.Routes.Master;

import com.fasterxml.jackson.databind.jsontype.impl.StdTypeResolverBuilder;
import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Master {
    @Id
    private long chatId;

    @Column
    private String masterName;

    @Column
    private String contact;

    @Column
    private String description;
}
