package vn.com.vng.zalopay.promotion.onboarding.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import vn.com.vng.zalopay.promotion.onboarding.transaction.entity.Person;
import vn.com.vng.zalopay.promotion.onboarding.transaction.repository.PersonRepository;

@Service
@Transactional(readOnly = true)
public class InnerReadonlyServiceImpl implements InnerReadonlyService{

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InnerRequiredService innerRequiredService;

    @Override
    public void save() {
        personRepository.save(new Person(this.getClass().getSimpleName()));
    }


}
