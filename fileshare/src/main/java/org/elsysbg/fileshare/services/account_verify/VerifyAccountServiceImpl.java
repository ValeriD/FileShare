package org.elsysbg.fileshare.services.account_verify;

import org.elsysbg.fileshare.models.VerifyAccount;
import org.elsysbg.fileshare.repositories.VerifyAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerifyAccountServiceImpl implements VerifyAccountService {

    @Autowired
    private VerifyAccountRepository verifyAccountRepository;

    @Override
    public VerifyAccount create(VerifyAccount verifyAccount) {
        return verifyAccountRepository.save(verifyAccount);
    }

    @Override
    public Optional<VerifyAccount> findByToken(String token) {
        return verifyAccountRepository.findByToken(token);
    }

    @Override
    public Optional<VerifyAccount> findById(Long id) {
        return verifyAccountRepository.findById(id);
    }
}
