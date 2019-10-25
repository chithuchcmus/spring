package vn.com.vng.zalopay.promotion.onboarding.transaction.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import vn.com.vng.zalopay.promotion.onboarding.transaction.entity.Person;

/**
 * @author huyvha
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO PERSON (NAME) VALUES (?)",
                person.getName());
    }

    @Override
    public int countByName(String name) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM PERSON WHERE NAME = ?",
                new Object[]{name},
                Integer.class);
    }

}
