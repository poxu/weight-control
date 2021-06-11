package takred.weightcontrol.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.dto.UserAccountDto;
import takred.weightcontrol.entity.UserAccount;
import takred.weightcontrol.mapper.UserAccountMapper;
import takred.weightcontrol.repository.UserAccountRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountService {
    private final UserAccountRepository userAccountRepository;
    private final UserAccountMapper userAccountMapper;

    public UserAccountService(UserAccountRepository userAccountRepository, UserAccountMapper userAccountMapper) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountMapper = userAccountMapper;
    }

    public List<UserAccountDto> getTrueSendNotificationsUsers() {
        List<UserAccount> userAccounts = userAccountRepository.findBySendNotifications(true);
        List<UserAccountDto> telegramUsersId = new ArrayList<>();

        for (int i = 0; i < userAccounts.size(); i++) {
            telegramUsersId.add(userAccountMapper.map(userAccounts.get(i)));
        }
        return telegramUsersId;
    }

    public void addUserAccount(Integer telegramUserId, Long chatId) {
        userAccountRepository.save(new UserAccount(telegramUserId, chatId));
    }

    @Transactional
    public void setNotifications(Update update) {
        UserAccount userAccount;
        if (update.hasMessage()) {
            userAccount = userAccountRepository.findById(update.getMessage().getFrom().getId()).get();
        } else if (update.hasCallbackQuery()) {
            userAccount = userAccountRepository.findById(update.getCallbackQuery().getFrom().getId()).get();
        } else {
            return;
        }
        userAccount.setSendNotifications(!userAccount.isSendNotifications());
    }

    public boolean userAccountExist(Integer telegramUserId) {
        return userAccountRepository.existsById(telegramUserId);
    }

    public UserAccount getUserAccount(Integer telegramUserId) {
        return userAccountRepository.findById(telegramUserId).get();
    }

    public String getNotificationsStatus(Integer telegramUserId) {
        if (userAccountRepository.findById(telegramUserId).get().isSendNotifications()) {
            return "Уведомления включены.";
        }
        return "Уведомления отключены.";
    }

    public String getNotificationsNameButton(Integer telegramUserId) {
        if (userAccountRepository.findById(telegramUserId).get().isSendNotifications()) {
            return "Выключить уведомления";
        }
        return "Включить уведомления";
    }
}
