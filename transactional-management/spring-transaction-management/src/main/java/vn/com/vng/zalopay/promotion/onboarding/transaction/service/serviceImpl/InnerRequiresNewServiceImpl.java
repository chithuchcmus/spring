package vn.com.vng.zalopay.promotion.onboarding.transaction.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.com.vng.zalopay.promotion.onboarding.transaction.entity.Person;
import vn.com.vng.zalopay.promotion.onboarding.transaction.repository.PersonRepository;
import vn.com.vng.zalopay.promotion.onboarding.transaction.service.InnerRequiresNewService;

/**
 * @author huyvha
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class InnerRequiresNewServiceImpl implements InnerRequiresNewService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void save(boolean raiseError) {
        personRepository.save(new Person(this.getClass().getSimpleName()));
        if (raiseError) {
            throw new RuntimeException("Error occurs");
        }
    }
}
