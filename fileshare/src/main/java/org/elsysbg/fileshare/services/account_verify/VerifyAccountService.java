package org.elsysbg.fileshare.services.account_verify;

import org.elsysbg.fileshare.models.VerifyAccount;

import java.util.Optional;

public interface VerifyAccountService {
    VerifyAccount create(VerifyAccount verifyAccount);
    Optional<VerifyAccount> findByToken(String token);
    Optional<VerifyAccount> findById(Long id);
}
