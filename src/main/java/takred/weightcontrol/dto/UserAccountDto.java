package takred.weightcontrol.dto;

import lombok.Value;

@Value
public class UserAccountDto {
    private final Integer telegramUserId;
    private final Long chatId;
    private final boolean sendNotifications;

    public UserAccountDto(Integer telegramUserId,Long chatId, boolean sendNotifications) {
        this.telegramUserId = telegramUserId;
        this.chatId = chatId;
        this.sendNotifications = sendNotifications;
    }
}
