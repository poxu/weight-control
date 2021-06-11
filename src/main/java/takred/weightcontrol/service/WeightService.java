package takred.weightcontrol.service;

import org.springframework.stereotype.Service;
import takred.weightcontrol.Converter;
import takred.weightcontrol.dto.YearAndDayDto;
import takred.weightcontrol.dto.UserNameAndWeightDto;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.entity.Weight;
import takred.weightcontrol.mapper.WeightMapper;
import takred.weightcontrol.repository.WeightRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeightService {

    private final WeightRepository weightRepository;
    private final WeightMapper weightMapper;
    private final Converter converter;

    public WeightService(WeightRepository weightRepository, WeightMapper weightMapper, Converter converter) {
        this.weightRepository = weightRepository;
        this.weightMapper = weightMapper;
        this.converter = converter;
    }

    public void addWeight(UserNameAndWeightDto dto) {
        LocalDateTime date = LocalDateTime.now();
        weightRepository.save(new Weight(dto.getUserName(), dto.getWeight(), date));
    }

    public void setWeight(WeightDto dto, Double weight) {
        weightRepository.save(new Weight(dto.getUserName(), weight, dto.getDate(), dto.getId()));
    }

    public List<WeightDto> getMyWeight(String userName) {
        List<Weight> weights = weightRepository.findByUserNameOrderByMeasurementDate(userName);
        List<WeightDto> weightDtos = new ArrayList<>();

        for (int i = 0; i < weights.size(); i++) {
            weightDtos.add(weightMapper.map(weights.get(i)));
        }
        return weightDtos;
    }

    public YearAndDayDto getLastRecordingDayById(Integer telegramUserId) {
        List<WeightDto> dtos = getMyWeight(telegramUserId.toString());
        if (dtos.isEmpty()) {
            return null;
        }
        WeightDto weightDto = dtos.get(dtos.size() - 1);
        Integer year = weightDto.getDate().getYear();
        Integer day = weightDto.getDate().getDayOfYear();
        return new YearAndDayDto(year, day);
    }

    public List<String> getMyWeightCsv(String userName) {
        return converter.convert(getMyWeight(userName));
    }
}
