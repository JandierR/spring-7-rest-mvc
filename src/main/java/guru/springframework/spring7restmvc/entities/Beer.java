package guru.springframework.spring7restmvc.entities;

import guru.springframework.spring7restmvc.model.BeerDTO;
import guru.springframework.spring7restmvc.model.BeerStyle;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonDeserialize(builder = BeerDTO.BeerDTOBuilder.class)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Beer {

    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;


    @Version
    private Integer version;
    private String beerName;
    private BeerStyle beerStyle;
    private String upc;
    private Integer quantityOnHand;
    private BigDecimal price;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
}
