package takred.weightcontrol;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.entity.UserAccount;
import takred.weightcontrol.service.UserAccountService;


@Service
public class Recorder {
    private final UserAccountService userAccountService;

    public Recorder(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Transactional
    public void addForNotExists(Update update) {
        if (update.hasMessage()) {
            Integer telegramUserId = update.getMessage().getFrom().getId();
            Long chatId = update.getMessage().getChatId();
            if (!userAccountService.userAccountExist(telegramUserId)) {
                userAccountService.addUserAccount(telegramUserId, chatId);
            } else {
                UserAccount userAccount = userAccountService.getUserAccount(telegramUserId);
                if (userAccount.getChatId() == null) {
                    userAccount.setChatId(chatId);
                }
            }
        }
        if (update.hasCallbackQuery()) {
            Integer telegramUserId = update.getCallbackQuery().getFrom().getId();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();
            if (!userAccountService.userAccountExist(telegramUserId)) {
                userAccountService.addUserAccount(telegramUserId, chatId);
            } else {
                UserAccount userAccount = userAccountService.getUserAccount(telegramUserId);
                if (userAccount.getChatId() == null) {
                    userAccount.setChatId(chatId);
                }
            }
        }
    }
}
