package guru.springframework.spring7restmvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = BeerDTO.BeerDTOBuilder.class)
public class BeerDTO {
    @JsonProperty("id")
    private UUID id;

    @JsonProperty("Version")
    private Integer version;

    @JsonProperty("beerName")
    private String beerName;

    @JsonProperty("beerStyle")
    private BeerStyle beerStyle;

    @JsonProperty("upc")
    private String upc;

    @JsonProperty("quantityOnHand")
    private Integer quantityOnHand;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("createdDate")
    private LocalDateTime createdDate;

    @JsonProperty("updateDate")
    private LocalDateTime updateDate;


}
