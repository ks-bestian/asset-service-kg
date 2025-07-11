package kr.co.bestiansoft.ebillservicekg.sed_jk.services.document;

import kr.co.bestiansoft.ebillservicekg.sed_jk.services.document.dto.GatewaySendResponseDto;

public interface DocumentDeliveryService {
    GatewaySendResponseDto sendDocumentToRecipients(Long documentId);
}
