package org.elsysbg.fileshare.services.account_verify;

import org.elsysbg.fileshare.dao.verify_account.VerifyAccountDao;
import org.elsysbg.fileshare.models.VerifyAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerifyAccountServiceImpl implements VerifyAccountService {

    @Autowired
    private VerifyAccountDao verifyAccountDao;

    @Override
    public VerifyAccount create(VerifyAccount verifyAccount) {
        return verifyAccountDao.create(verifyAccount);
    }

    @Override
    public Optional<VerifyAccount> findByToken(String token) {
        return verifyAccountDao.findByToken(token);
    }

    @Override
    public Optional<VerifyAccount> findById(Long id) {
        return verifyAccountDao.findById(id);
    }
}
