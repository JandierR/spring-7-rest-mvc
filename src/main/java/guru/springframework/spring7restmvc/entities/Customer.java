package guru.springframework.spring7restmvc.entities;

import guru.springframework.spring7restmvc.model.CustomerDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@JsonDeserialize(builder = CustomerDTO.CustomerDTOBuilder.class)
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private UUID id;

    @Version
    private Integer version;
    private String customerName;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
