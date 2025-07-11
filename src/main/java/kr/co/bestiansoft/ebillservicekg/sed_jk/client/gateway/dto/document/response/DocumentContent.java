package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.response;

import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.GatewaySystem;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.Attachment;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.DocStatus;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.DocType;
import kr.co.bestiansoft.ebillservicekg.sed_jk.endpoint.organization.dto.OrganizationAddDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.services.document.dto.OrganizationDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DocumentContent {
    private String docNumber;
    private UUID docUuid;
    private String fileName;
    private String fileLink;

    private DocType docType;
    private DocType docLangKy;
    private DocType docLangRu;
    private DocType docLangFl;
    private DocStatus docStatus;

    @JsonProperty("docRegistered")
    private String docRegistered; // Используем String для парсинга, затем преобразуем в LocalDateTime
    @JsonProperty("docDeadline")
    private String docDeadline; // Используем String для парсинга, затем преобразуем в LocalDateTime
    @JsonProperty("docSignature")
    private String docSignature;
    private String signatureAlgorithm;
    private GatewaySystem sysSender;
    private GatewaySystem sysReceiver;
    private OrganizationDto orgSender;
    private OrganizationDto orgReceiver;
    private String docUuidRelated;
    private String docNumberRelated;
    @JsonProperty("docRegisteredRelated")
    private String docRegisteredRelated;
    private List<Attachment> attachments;
    private String accepted;
    private String created;
    private String docDescription;
    private String docCreaterName;
    private String docSignerName;
}
