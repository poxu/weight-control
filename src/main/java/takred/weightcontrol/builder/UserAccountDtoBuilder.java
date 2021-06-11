package takred.weightcontrol.builder;

import takred.weightcontrol.dto.UserAccountDto;

public class UserAccountDtoBuilder {
    private Integer telegramUserId;
    private Long chatId;
    private boolean sendNotifications;

    public UserAccountDtoBuilder withTelegramUserId(Integer telegramUserId) {
        this.telegramUserId = telegramUserId;
        return this;
    }

    public UserAccountDtoBuilder withChatId(Long chatId) {
        this.chatId = chatId;
        return this;
    }

    public UserAccountDtoBuilder withSendNotifications(boolean sendNotifications) {
        this.sendNotifications = sendNotifications;
        return this;
    }

    public UserAccountDto build() {
        return new UserAccountDto(telegramUserId, chatId, sendNotifications);
    }

}
