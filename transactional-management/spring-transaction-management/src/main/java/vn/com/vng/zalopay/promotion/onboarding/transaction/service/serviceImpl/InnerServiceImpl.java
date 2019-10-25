package vn.com.vng.zalopay.promotion.onboarding.transaction.service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.com.vng.zalopay.promotion.onboarding.transaction.entity.Person;
import vn.com.vng.zalopay.promotion.onboarding.transaction.repository.PersonRepository;
import vn.com.vng.zalopay.promotion.onboarding.transaction.repository.PersonRepositoryImpl;
import vn.com.vng.zalopay.promotion.onboarding.transaction.service.InnerRequiredService;
import vn.com.vng.zalopay.promotion.onboarding.transaction.service.InnerRequiresNewService;
import vn.com.vng.zalopay.promotion.onboarding.transaction.service.InnerService;

@Service
public class InnerServiceImpl implements InnerService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InnerRequiresNewService innerRequiresNewService;

    @Autowired
    private InnerRequiredService innerRequiredService;

    @Override
    public void save(boolean raiseError) {
        try {
            innerRequiredService.save(raiseError);
        } catch (RuntimeException e) {

        }
    }
}
