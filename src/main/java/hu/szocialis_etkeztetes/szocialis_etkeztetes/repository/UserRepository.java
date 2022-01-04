package hu.szocialis_etkeztetes.szocialis_etkeztetes.repository;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserName(String userName);
}
