package vn.com.vng.zalopay.promotion.onboarding.transaction.repository;

import vn.com.vng.zalopay.promotion.onboarding.transaction.entity.Person;

/**
 * @author huyvha
 */
public interface PersonRepository {
    void save(Person person);
    int countByName(String name);
}
