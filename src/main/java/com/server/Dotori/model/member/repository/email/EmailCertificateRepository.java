package com.server.Dotori.model.member.repository.email;

import com.server.Dotori.model.member.EmailCertificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailCertificateRepository extends JpaRepository<EmailCertificate,Long> {
    EmailCertificate findByKey(String key);
    void deleteEmailCertificateByKey(String key);
    boolean existsByKey(String key);
}
