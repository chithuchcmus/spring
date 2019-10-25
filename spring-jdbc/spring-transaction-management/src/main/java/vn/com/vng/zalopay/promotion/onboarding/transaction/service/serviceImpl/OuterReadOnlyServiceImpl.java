package vn.com.vng.zalopay.promotion.onboarding.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.vng.zalopay.promotion.onboarding.transaction.entity.Person;
import vn.com.vng.zalopay.promotion.onboarding.transaction.repository.PersonRepository;

@Service
@Transactional (readOnly = true)
public class OuterReadOnlyServiceImpl  implements  OuterTransactionReadOnlyService{

    @Autowired
    private InnerRequiredService innerRequiredService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InnerReadonlyService innerReadonlyService;

    @Override
    public void executeReadWriteInsideReadOnly() {
        personRepository.save(new Person(this.getClass().getSimpleName()));
        innerRequiredService.save(false);
    }

    @Override
    public void executeReadOnly()
    {
        innerReadonlyService.save();
    }

    @Override
    public void executeReadWrite()
    {
        personRepository.save(new Person(this.getClass().getSimpleName()));
        innerRequiredService.save(false);
    }

}
