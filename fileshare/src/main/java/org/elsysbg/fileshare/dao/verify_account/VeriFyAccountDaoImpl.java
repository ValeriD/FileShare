package org.elsysbg.fileshare.dao.verify_account;

import org.elsysbg.fileshare.models.VerifyAccount;
import org.elsysbg.fileshare.repositories.VerifyAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public class VeriFyAccountDaoImpl implements VerifyAccountDao {

    @Autowired
    VerifyAccountRepository verifyAccountRepository;

    @Override
    public Optional<VerifyAccount> findById(long id) {
        return verifyAccountRepository.findById(id);
    }

    @Override
    public List<VerifyAccount> findAll() {
        return verifyAccountRepository.findAll();
    }


    @Transactional
    @Override
    public VerifyAccount create(VerifyAccount verifyAccount) {
        return verifyAccountRepository.save(verifyAccount);
    }

    @Transactional
    @Override
    public VerifyAccount update(VerifyAccount entity) {
        return verifyAccountRepository.save(entity);
    }

    @Transactional
    @Override
    public void delete(VerifyAccount entity) {
        verifyAccountRepository.delete(entity);
    }

    @Override
    public void deleteById(long entityId) {
        verifyAccountRepository.deleteById(entityId);
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
