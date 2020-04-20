package org.elsysbg.fileshare.dao.verify_account;

import org.elsysbg.fileshare.dao.IOperations;
import org.elsysbg.fileshare.models.VerifyAccount;

import java.util.Optional;

public interface VerifyAccountDao extends IOperations<VerifyAccount> {
    VerifyAccount create(VerifyAccount verifyAccount);
    Optional<VerifyAccount> findByToken(String token);
    Optional<VerifyAccount> findById(Long id);
}
