package takred.weightcontrol.mapper;

import org.springframework.stereotype.Component;
import takred.weightcontrol.builder.WeightDtoBuilder;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.entity.Weight;

@Component
public class WeightMapper {
    public WeightDto map(Weight entity) {
        return new WeightDtoBuilder()
                .withUserName(entity.getUserName())
                .withWeight(entity.getWeight())
                .withDate(entity.getMeasurementDate())
                .withId(entity.getId())
                .build();
    }
}
