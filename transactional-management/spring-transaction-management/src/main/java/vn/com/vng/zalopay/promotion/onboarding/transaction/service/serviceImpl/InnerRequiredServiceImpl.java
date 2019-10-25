package vn.com.vng.zalopay.promotion.onboarding.transaction.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.vng.zalopay.promotion.onboarding.transaction.entity.Person;
import vn.com.vng.zalopay.promotion.onboarding.transaction.repository.PersonRepository;
import vn.com.vng.zalopay.promotion.onboarding.transaction.service.InnerReadonlyService;
import vn.com.vng.zalopay.promotion.onboarding.transaction.service.InnerRequiredService;

/**
 * @author huyvha
 */
@Service
@Transactional
public class InnerRequiredServiceImpl implements InnerRequiredService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InnerReadonlyService innerReadonlyService;

    @Override
    public void save(boolean raiseError) {
        personRepository.save(new Person(this.getClass().getSimpleName()));

        if (raiseError) {
            throw new RuntimeException("Error occurs");
        }
    }

}
