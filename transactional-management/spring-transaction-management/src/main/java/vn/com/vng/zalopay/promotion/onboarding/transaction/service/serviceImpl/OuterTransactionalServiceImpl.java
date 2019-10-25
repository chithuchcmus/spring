package vn.com.vng.zalopay.promotion.onboarding.transaction.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.vng.zalopay.promotion.onboarding.transaction.entity.Person;
import vn.com.vng.zalopay.promotion.onboarding.transaction.repository.PersonRepository;
import vn.com.vng.zalopay.promotion.onboarding.transaction.service.*;

/**
 * @author huyvha
 */
@Service
@Transactional
public class OuterTransactionalServiceImpl implements OuterTransactionalService {

    @Autowired
    private InnerRequiredService innerRequiredService;
    @Autowired
    private InnerRequiresNewService innerRequiresNewService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InnerService innerService;

    @Autowired
    private InnerReadonlyService innerReadonlyService;

    @Override
    public void executeRequiresNewInsideRequired() {
        personRepository.save(new Person(this.getClass().getSimpleName()));
        innerRequiresNewService.save(true);
    }

    @Override
    public void executeMixedPropagation() {
        innerRequiredService.save(false);
        innerRequiresNewService.save(true);
    }

    @Override
    public void executeSuccessInInnerTransaction() {
        innerRequiresNewService.save(false);
        innerRequiredService.save(true);
    }
    @Override
    public void executeTransactionalCallMethodWithoutTransactional()
    {
        innerService.save(true);
        innerRequiredService.save(true);
    }

    @Override
    public void executeReadOnly() {
        personRepository.save(new Person(this.getClass().getSimpleName()));
        innerReadonlyService.save();
    }



}
