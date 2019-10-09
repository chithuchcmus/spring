package vn.com.vng.zalopay.promotion.onboarding.beanscope;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class ConnectPostgreSql implements ConnectDB {
    @Override
    public void notifyCation() {
        System.out.println("Connect Postgre");
    }
}
