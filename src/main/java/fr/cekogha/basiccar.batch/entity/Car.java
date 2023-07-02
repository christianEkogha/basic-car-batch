package fr.cekogha.basiccar.batch.entity;

import com.datastax.driver.core.utils.UUIDs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Car {
    @PrimaryKey
    private UUID id = UUIDs.timeBased();
    private String brand;
    private String model;
    private BigDecimal milestone = new BigDecimal(0);
    private BigDecimal price = new BigDecimal(0);
    private String year;
    private Map<LocalDateTime, LocalDateTime> activity;
}
