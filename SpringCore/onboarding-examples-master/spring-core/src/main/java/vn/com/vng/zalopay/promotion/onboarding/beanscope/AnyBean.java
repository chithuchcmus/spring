package vn.com.vng.zalopay.promotion.onboarding.beanscope;
import javax.annotation.PreDestroy;


/**
 * @author huyvha
 */
public class AnyBean {

    private boolean destroyed;

    @PreDestroy
    public void onDestroyed() {
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}
