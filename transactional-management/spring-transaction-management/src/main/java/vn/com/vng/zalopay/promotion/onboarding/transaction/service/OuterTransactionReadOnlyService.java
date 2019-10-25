package vn.com.vng.zalopay.promotion.onboarding.transaction.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface OuterTransactionReadOnlyService {
    public void executeReadWriteInsideReadOnly();
    public void executeReadOnly();
    public void executeReadWrite();
}
