package guru.springframework.spring7restmvc.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Customer {
    private String customerName;
    private UUID id;
    private Integer version;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
