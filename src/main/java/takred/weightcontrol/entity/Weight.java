package takred.weightcontrol.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class Weight {
    private String userName;
    private Double weight;
    private LocalDateTime measurementDate;
    @Id
    private UUID id;

    public Weight() {}

    public Weight(String userName, Double weight, LocalDateTime measurementDate) {
        this.userName = userName;
        this.weight = weight;
        this.measurementDate = measurementDate;
        this.id = UUID.randomUUID();
    }

    public Weight(String userName, Double weight, LocalDateTime measurementDate, UUID id) {
        this.userName = userName;
        this.weight = weight;
        this.measurementDate = measurementDate;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        Weight weight = (Weight) obj;
        if (this.id.equals(weight.id)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}