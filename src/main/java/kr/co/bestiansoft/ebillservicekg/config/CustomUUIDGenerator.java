package kr.co.bestiansoft.ebillservicekg.config;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

public class CustomUUIDGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", ""); // 필요한 길이로 조정
    }

}
