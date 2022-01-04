package hu.szocialis_etkeztetes.szocialis_etkeztetes.repository;

import hu.szocialis_etkeztetes.szocialis_etkeztetes.model.PersonalMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalMessageRepository extends JpaRepository<PersonalMessage, Long> {
    public PersonalMessage findBySubject(String subject);
}
