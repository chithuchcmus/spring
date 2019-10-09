package vn.com.vng.zalopay.promotion.onboarding.beanscope;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;

/**
 * @author huyvha
 */
@ActiveProfiles("dev")
public class BeanScopeTest {

    @Test
    public void test_newInstanceGiven_whenCallingGetBean_forPrototypeBean() {
        // GIVEN
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SampleBeanScopeConfig.class);

        // WHEN getting bean from ApplicationContext
        PrototypeBean bean1 = ctx.getBean(PrototypeBean.class);
        PrototypeBean bean2 = ctx.getBean(PrototypeBean.class);

        // THEN always new instance is returned
        assertThat(bean1, not(sameInstance(bean2)));
    }

    @Test
    public void test_sameInstanceGiven_whenCallingGetBean_forSingletonBean() {
        // GIVEN
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SampleBeanScopeConfig.class);

        // WHEN getting bean from ApplicationContext
        SingletonBean bean1 = ctx.getBean(SingletonBean.class);
        SingletonBean bean2 = ctx.getBean(SingletonBean.class);

        // THEN always same instance is returned
        assertThat(bean1, sameInstance(bean2));
    }

    @Test
    public void test_preDestroyedCalled_forSingletonBean() {
        // GIVEN
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SampleBeanScopeConfig.class);
        SingletonBean bean = ctx.getBean(SingletonBean.class);

        // WHEN
        ctx.close();

        // THEN
        assertThat(bean.isDestroyed(), is(true));
    }

    @Test
    public void test_preDestroyedNotCalled_forPrototypeBean() {
        // GIVEN
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SampleBeanScopeConfig.class);
        PrototypeBean bean = ctx.getBean(PrototypeBean.class);

        // WHEN
        ctx.close();

        // THEN
        assertThat(bean.isDestroyed(), is(false));
    }

    @Test
    public void testGetBeanFromActiveEnviroment()
    {
        ConfigurableApplicationContext ctx = new AnnotationConfigApplicationContext(SampleBeanScopeConfig.class);
        ConnectDB bean = ctx.getBean(ConnectDB.class);
        assertThat(bean, not(null));

    }


}
