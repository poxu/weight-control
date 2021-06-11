package takred.weightcontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import takred.weightcontrol.entity.UserAccount;

import java.util.List;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {

    List<UserAccount> findBySendNotifications(boolean sendNotifications);
}
