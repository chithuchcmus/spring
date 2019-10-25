package vn.com.vng.zalopay.promotion.onboarding.transaction.service;

/**
 * @author huyvha
 */
public interface OuterTransactionalService {
    void executeRequiresNewInsideRequired();
    void executeMixedPropagation();
    void executeSuccessInInnerTransaction();
    void executeTransactionalCallMethodWithoutTransactional();
    void executeReadOnly();

}
