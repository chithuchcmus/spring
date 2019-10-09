package vn.com.vng.zalopay.promotion.onboarding.beanscope;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class ConnectMysql implements ConnectDB {
    public void notifyCation()
    {
        System.out.println("Connect Mysql");
    }
}
