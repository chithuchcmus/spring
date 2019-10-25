package com.example.demo;

import com.example.demo.model.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringJdbcApplicationTests {

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    PersonDao personDao;


    @Test
    public void test_getPeopleByFirstName() {

        //give: config spring jdbc in properties and personalDaoImple
        String firstName = "nguyen";
        //when
        List<Person> personListFromDB = personDao.getPersonByFirstname(firstName);

        System.out.println(personListFromDB.get(0).getLastName());
        //then
        Assert.assertEquals("thuc", personListFromDB.get(0).getLastName());
    }
    @Test
    public void test_updatePerson() {
        //give
        //person in DB, update from thuc -> toai
        Person person = new Person(21, "tran", "toai");
        String lastNameOfPersonUpdate = "dan";
        //when
        personDao.updatePersonByLastName(lastNameOfPersonUpdate, person);
        //then
        Person personUpdated = personDao.getPersonByLastName("toai").get(0);
        Assert.assertEquals("toai", personUpdated.getLastName());
        Assert.assertEquals("tran", personUpdated.getFirstName());
        Assert.assertEquals("21", personUpdated.getAge());
    }

    @Test
    public void test_deletePersonByAge() {
        //give
        //get person from Db
        Person person = personDao.getPersonByLastName("hung dao").get(0);
        //when
        personDao.deletePersonByAge(person.getAge());
        //then
        List<Person> personIsDeleted = personDao.getPersonByLastName("thuc");
        Assert.assertEquals(0, personIsDeleted.size());
    }

    @Test
    public void test_insertNewPerson() {
        //give
        Person person = new Person(18, "tran", "hung dao");
        //when
        personDao.savePerson(person);
        //then
        Person personIsInserted = personDao.getPersonByLastName("hung dao").get(0);
        Assert.assertEquals("18", personIsInserted.getAge().toString());
        Assert.assertEquals("tran", personIsInserted.getFirstName());
        Assert.assertEquals("hung dao", personIsInserted.getLastName());
    }
}
