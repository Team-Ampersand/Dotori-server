package com.server.Dotori.model.member.repository.email;

import com.server.Dotori.model.member.EmailCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailCertificateRepository extends JpaRepository<EmailCertificate,Long> {
    Optional<EmailCertificate> findByKey(String key);
    void deleteEmailCertificateByKey(String key);
    void deleteEmailCertificateByEmail(String email);
}
