package takred.weightcontrol;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import takred.weightcontrol.dto.UserAccountDto;
import takred.weightcontrol.dto.YearAndDayDto;
import takred.weightcontrol.service.UserAccountService;
import takred.weightcontrol.service.WeightService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class Sendler {
    private final UserAccountService userAccountService;
    private final WeightService weightService;
    private final Bot bot;

    public Sendler(UserAccountService userAccountService, WeightService weightService, Bot bot) {
        this.userAccountService = userAccountService;
        this.weightService = weightService;
        this.bot = bot;
    }

    @Scheduled(fixedDelay = 3600000)
    public void sendNotifications() {
        List<UserAccountDto> userAccounts = userAccountService.getTrueSendNotificationsUsers();
        if (userAccounts.isEmpty()) {
            return;
        }
        LocalDateTime dateTime = LocalDateTime.now();
        Integer yearNow = dateTime.getYear();
        Integer dayNow = dateTime.getDayOfYear();
        LocalDateTime lastDayOfTheYear = LocalDateTime.of(dateTime.getYear(), 12, 31, 0, 0);
        for (int i = 0; i < userAccounts.size(); i++) {
            UserAccountDto userAccountDto = userAccounts.get(i);
            YearAndDayDto lastRecordingDay = weightService.getLastRecordingDayById(userAccountDto.getTelegramUserId());
            if (lastRecordingDay == null) {
                break;
            }
            if (yearNow - lastRecordingDay.getYear() > 0
                    && dayNow + lastDayOfTheYear.getDayOfYear() - lastRecordingDay.getDay() >= 2
            || yearNow - lastRecordingDay.getYear() == 0
                    && dayNow - lastRecordingDay.getDay() >= 2) {
                sendNotification(userAccountDto);
            }
        }
    }

    public void sendNotification(UserAccountDto dto) {
        String message = "Вы не делали запись уже больше двух дней.";
        bot.sendMessage(dto.getChatId(), message);
    }
}
