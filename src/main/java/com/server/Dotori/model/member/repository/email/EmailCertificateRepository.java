package com.server.Dotori.model.member.repository.email;

import com.server.Dotori.model.member.EmailCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface EmailCertificateRepository extends JpaRepository<EmailCertificate,Long> {
    Optional<EmailCertificate> findByKey(String key);
    @Transactional
    void deleteEmailCertificateByKey(String key);
    @Transactional
    void deleteEmailCertificateByEmail(String email);
}
