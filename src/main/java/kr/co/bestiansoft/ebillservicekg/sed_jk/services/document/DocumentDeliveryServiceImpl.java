package kr.co.bestiansoft.ebillservicekg.sed_jk.services.document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

//import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.GatewayClient;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.request.AddDocumentRequest;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.request.AttachmentFileDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.response.AddDocumentResponse;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.document.Document;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.document.DocumentAgreement;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.document.DocumentAttachment;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.document.DocumentParty;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.storage.StorageFile;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.enums.DocumentStatus;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.enums.RelationType;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.repository.document.DocumentPartyRepository;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.repository.document.DocumentRepository;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.services.common.StorageFileService;
import kr.co.bestiansoft.ebillservicekg.sed_jk.services.document.dto.GatewaySendResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentDeliveryServiceImpl implements DocumentDeliveryService {
//    private final DocumentRepository documentRepository;
//    private final DocumentPartyRepository documentPartyRepository;
    private final GatewayClient gatewayClient;
//    private final StorageFileService storageFileService;
    private final EDVHelper edv;

    @Override
    @Transactional
    public GatewaySendResponseDto sendDocumentToRecipients(Long documentId) {
//        Document document = documentRepository.findById(documentId)
//                .orElseThrow(() -> new IllegalArgumentException("Document with ID " + documentId + " not found."));
//
//        if(!Objects.equals(document.getStatus(), DocumentStatus.closing.name()))
//            return new GatewaySendResponseDto(false, "Document status is not closing.");
//
//        DocumentAgreement signedAgreement = document.getAgreements().stream()
//                .filter(da -> da.getSignature() != null && da.getSigner())
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException("No signed agreement found for document ID: " + documentId + " with signature and signer=true."));
//
//
//        List<DocumentParty> recipientParties = document.getParties().stream()
//                .filter(p -> p.getRelationType() == RelationType.recipient)
//                .toList();
//
//        if (recipientParties.isEmpty()) {
//            log.warn("No recipients found for document ID: {}", documentId);
//            return new GatewaySendResponseDto(false, "No recipients found for the document.");
//        }
//
//        List<DocumentParty> updatedDocumentParties = new ArrayList<>();

        try {
//            String senderInn = document.getState().getInn();
//            String docNumber = document.getRegistrationNumber();
//            assert document.getRegistrationDate() != null;
//            String docRegistered = document.getRegistrationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            String signatureAlgorithm = "gost3411";
//            String documentSignature = signedAgreement.getSignature();
//            String documentDescription = document.getDescription();
//            String documentCreatorName = document.getEmployee().getEmployee().getFullName();
//            String documentSignerName = signedAgreement.getEmployee().getEmployee().getFullName();
//
//            Set<DocumentAttachment> attachments = document.getAttachments();

        	String senderInn = "01806199610125";
            String docNumber = "test-best/8";
            String docRegistered = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String signatureAlgorithm = "gost3411";
            String documentSignature = "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";
            String documentDescription = "document-send-test-8";
            String documentCreatorName = "Казакбаев Азим Нургалиевич";
            String documentSignerName = "Казакбаев Азим Нургалиевич";

            AttachmentFileDto officialFile = getOfficialFileDto(null, documentSignature);

            List<AttachmentFileDto> attachmentFiles = new ArrayList<>();

//            for (DocumentAttachment attachment : attachments) {
//                if (!attachment.getOfficial()) {
//                    attachmentFiles.add(getAttachmentDto(attachment.getFile()));
//                }
//            }

//            for (DocumentParty party : recipientParties) {
//                String receiverInn = party.getCounterparty().getInn();
                String receiverInn = "00707201010040";
                AddDocumentRequest requestBody = new AddDocumentRequest(
                        senderInn,
                        receiverInn,
                        docNumber,
                        "none",
                        "ru",
                        "none",
                        "LETTER",
                        docRegistered,
                        "",
                        documentSignature,
                        signatureAlgorithm,
                        documentCreatorName,
                        documentSignerName,
                        documentDescription,
                        false,
                        "",
                        "",
                        "",
                        officialFile,
                        attachmentFiles
                        );
//
                AddDocumentResponse gatewayResponse = gatewayClient.sendDocumentToGateway(requestBody);

                System.out.println("requestBody=========================================");
                System.out.println(requestBody);
                System.out.println("gatewayResponse=====================================");
                System.out.println(gatewayResponse);

                if (gatewayResponse.success() && gatewayResponse.result() != null && !gatewayResponse.result().isEmpty()) {

                    System.out.println("Document successfully sent to Gateway!");
                    System.out.println("Received docUuid: " + gatewayResponse.result().getFirst().docUuid());

//                    party.setDocumentUuid(UUID.fromString(gatewayResponse.result().getFirst().docUuid()));
//                    updatedDocumentParties.add(party);
                }
//            }

//            if (!updatedDocumentParties.isEmpty()) {
//                documentPartyRepository.saveAll(updatedDocumentParties);
//            }
//
//            document.setStatus(DocumentStatus.closed.name());
//            documentRepository.save(document);

            return new GatewaySendResponseDto(true, "Document successfully sent to all recipients.");

        } catch (Exception e) {
            log.error("Error sending document ID {} to gateway: {}", documentId, e.getMessage(), e);
            return new GatewaySendResponseDto(false, "Failed to send document: " + e.getMessage());
        }
    }

    private byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        inputStream.transferTo(outputStream);
        return outputStream.toByteArray();
    }

    private AttachmentFileDto getAttachmentDto(EasFileVo easFileVo) throws Exception {
//        byte[] content = storageFileService.loadFileContent(storageFile);
        InputStream is = edv.download(easFileVo.getFileId());
        byte[] content = toByteArray(is);

        String attachmentFileBodyBase64 = Base64.getEncoder().encodeToString(content);

        return new AttachmentFileDto(
        		easFileVo.getOrgFileNm(),
        		easFileVo.getFileExt(),
                attachmentFileBodyBase64,
                ""
        );
    }

    private AttachmentFileDto getOfficialFileDto(EasFileVo easFileVo, String signature) throws Exception {
//        byte[] content = storageFileService.loadFileContent(storageFile);
    	InputStream is = edv.download(easFileVo.getFileId());
        byte[] content = toByteArray(is);

        String attachmentFileBodyBase64 = Base64.getEncoder().encodeToString(content);

        return new AttachmentFileDto(
        		easFileVo.getOrgFileNm(),
        		easFileVo.getFileExt(),
                attachmentFileBodyBase64,
                signature
        );
    }
}
