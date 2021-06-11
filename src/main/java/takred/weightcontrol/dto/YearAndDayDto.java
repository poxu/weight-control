package takred.weightcontrol.dto;

import lombok.Value;

@Value
public class YearAndDayDto {
    private final Integer year;
    private final Integer day;

    public YearAndDayDto(Integer year, Integer day) {
        this.year = year;
        this.day = day;
    }
}
