package guru.springframework.spring7restmvc.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = CustomerDTO.CustomerDTOBuilder.class)

public class CustomerDTO {

    @NotNull
    @NotBlank
    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("id")
    private UUID id;

    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
