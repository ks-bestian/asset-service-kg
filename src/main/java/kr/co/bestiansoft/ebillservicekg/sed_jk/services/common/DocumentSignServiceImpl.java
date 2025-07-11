package kr.co.bestiansoft.ebillservicekg.sed_jk.services.common;

import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.eas.approval.service.ApprovalService;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.ApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.approval.vo.UpdateApprovalVo;
import kr.co.bestiansoft.ebillservicekg.eas.documentWorkFlow.enums.EasFileType;
import kr.co.bestiansoft.ebillservicekg.eas.file.service.EasFileService;
import kr.co.bestiansoft.ebillservicekg.eas.file.vo.EasFileVo;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.CdsClient;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.request.AuthRequest;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.request.CheckSignRequest;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.request.SignHashRequest;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response.AuthResponse;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response.CheckSignResponse;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.cds.dto.response.SignHashResponse;
import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.request.SignRequestDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.response.CheckResponseDto;
import kr.co.bestiansoft.ebillservicekg.sed_jk.dto.response.SignResponseDto;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.document.Document;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.document.DocumentAgreement;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.document.DocumentAttachment;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.document.DocumentParty;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.employee.EmployeePositionHistory;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.entities.employee.EmployeeProfile;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.enums.DocumentStatus;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.enums.RelationType;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.exception.ResourceNotFoundException;
import kr.co.bestiansoft.ebillservicekg.sed_jk.exception.ServiceException;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.exception.UnAuthorizedException;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.repository.document.DocumentAgreementRepository;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.repository.document.DocumentRepository;
//import kr.co.bestiansoft.ebillservicekg.sed_jk.services.employee.EmployeeProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentSignServiceImpl implements DocumentSignService{
    private final CdsClient cdsClient;
//    private final EmployeeProfileService employeeService;
//    private final DocumentAgreementRepository documentAgreementRepository;
//    private final DocumentRepository documentRepository;
    private final EasFileService easFileService;
    private final ApprovalService approvalService;
    private final HashingService hashingService;
    private final EDVHelper edv;

    @Transactional
    public SignResponseDto signDocument(SignRequestDto signRequestDto) {
//        EmployeeProfile employeeProfile = employeeService.getCurrentEmployee()
//                .orElseThrow(() -> new UnAuthorizedException("Пользователь не авторизован."));
//
//        EmployeePositionHistory currentEmployeePositionHistory = employeeService.getCurrentEmployeePositionHistory();
//
//        DocumentAgreement agreement = documentAgreementRepository.findById(signRequestDto.agreementId())
//                .orElseThrow(() -> new ResourceNotFoundException("Подписывающий о документе не найден для ID: " + signRequestDto.agreementId()));

//        String fileHash = agreement.getSignedFile().getHash();

    	String fileHash = "55c4075ea0bc208781aa99749100c7ed00bd4ef9fcfa65a251ee207d5ac40a6e";
//        String userToken = authenticateWithCds(employeeProfile, currentEmployeePositionHistory, signRequestDto.pinCode());
    	String userToken = authenticateWithCds(signRequestDto.pinCode());

        String signature = signHashWithCds(fileHash, userToken);
        
        System.out.println("=============================================");
        System.out.println(userToken);
        System.out.println(signature);

//        saveSignatureAndProcessDocument(agreement, signature, signRequestDto.agreementId());

        return new SignResponseDto("Документ успешно подписан.", true);
    }

    @Override
    public SignResponseDto approvalDocument(SignRequestDto signRequestDto) {
        ApprovalVo approvalVo = approvalService.getApproval(signRequestDto.apvlId());
        EasFileVo  fileVo = easFileService.getFileByDocIdAndFileType(approvalVo.getDocId(), EasFileType.DRAFT_DOCUMENT_FILE.getCodeId());

        String fileHash = "";
        try{
            fileHash= hashingService.calculateHash(edv.download(fileVo.getFileId()));
        }catch(Exception e){
            return new SignResponseDto("error hashing", false);
        }

        String userToken = authenticateWithCds(signRequestDto.pinCode());
        String signature = signHashWithCds(fileHash, userToken);


        UpdateApprovalVo updateApprovalVo = UpdateApprovalVo.builder()
                .apvlId(signRequestDto.apvlId())
                .apvlHash(signature)
                .build();

        approvalService.updateApproval(updateApprovalVo);

        return new SignResponseDto("Документ успешно подписан.", true);
    }

    //    private String authenticateWithCds(EmployeeProfile employeeProfile, EmployeePositionHistory employeePositionHistory, String pinCode) {
    private String authenticateWithCds(String pinCode) {
//        String personIdnp = employeeProfile.getPin();
//        String organizationInn = employeePositionHistory.getPosition().getDepartment().getState().getInn();
    	
    	String personIdnp = "22512198900930";
        String organizationInn = "01806199610125";

        if (personIdnp == null || organizationInn == null) {
            throw new ServiceException("Данные пользователя для аутентификации не найдены (PIN или ИНН организации).");
        }

        AuthRequest authRequest = AuthRequest.builder()
                .personIdnp(personIdnp)
                .organizationInn(organizationInn)
                .byPin(pinCode)
                .build();

        AuthResponse authResponse;
        try {
            authResponse = cdsClient.authenticate(authRequest);
        } catch (Exception e) {
            log.error("Authentication failed for user: {}", e.getMessage(), e);
            throw new ServiceException("Authentication failed for user: " + e.getMessage());
        }

        if (authResponse == null || authResponse.getToken() == null || !authResponse.getIsActive()) {
            log.error("Authentication failed for user {}.", personIdnp);
            throw new ServiceException("Authentication failed: ");
        }
        return authResponse.getToken();
    }

    private String signHashWithCds(String fileHash, String userToken) {
        SignHashRequest signHashRequest = SignHashRequest.builder()
                .hash(fileHash)
                .userToken(userToken)
                .build();

        SignHashResponse signHashResponse;
        try {
            signHashResponse = cdsClient.signHash(signHashRequest);
        } catch (Exception e) {
            log.error("Ошибка при подписании хеша: {}", e.getMessage(), e);
            throw new ServiceException("Ошибка при подписании хеша: " + e.getMessage());
        }

        if (signHashResponse == null || !signHashResponse.getIsSuccesfull()) {
            log.error("Hash signing failed. Response was not successful");
            throw new ServiceException("Ошибка подписания хеша: ");
        }
        return signHashResponse.getSign();
    }

//    private void saveSignatureAndProcessDocument(DocumentAgreement agreement, String signature, Long agreementId) {
//        try {
//            agreement.setSignature(signature);
//            agreement.setActionDate(LocalDateTime.now());
//            documentAgreementRepository.save(agreement);
//
//            if (agreement.getSigner()) {
//                Document document = agreement.getDocument();
//                document.setRegistrationDate(LocalDateTime.now());
//                document.setRegistrationNumber("number/" + document.getId()); // Consider a more robust registration number generation
//                document.setStatus(DocumentStatus.closing.name()); // Using enum name directly
//                documentRepository.save(document);
//            }
//        } catch (Exception e) {
//            log.error("Ошибка при сохранении подписи для DocumentAgreement ID {} или обновлении документа: {}", agreementId, e.getMessage(), e);
//            throw new ServiceException("Ошибка при сохранении подписи в базу данных или обновлении документа: " + e.getMessage());
//        }
//    }

//    @Transactional
//    public CheckResponseDto checkAgreement(Long agreementId) {
//        DocumentAgreement agreement = documentAgreementRepository.findById(agreementId)
//                .orElseThrow(() -> new ResourceNotFoundException("Подписывающий о документе не найден для ID: " + agreementId));
//
//        String fileHash = agreement.getSignedFile().getHash();
//        String signature = agreement.getSignature();
//
//        return checkSign(fileHash, signature, "проверка подписи соглашения");
//    }
//
//    @Transactional
//    public CheckResponseDto checkDocument(Long documentId) {
//        Document document = documentRepository.findById(documentId)
//                .orElseThrow(() -> new ResourceNotFoundException("Документ не найден для ID: " + documentId));
//
//        DocumentParty documentParty = document.getParties().stream()
//                .filter(dp -> dp.getRelationType() == RelationType.sender)
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Не найдена сторона-отправитель для документа ID: " + documentId));
//
//        DocumentAttachment documentAttachment = document.getAttachments().stream()
//                .filter(DocumentAttachment::getOfficial)
//                .findFirst()
//                .orElseThrow(() -> new ResourceNotFoundException("Не найдено официальное вложение для документа ID: " + documentId));
//
//        String fileHash = documentAttachment.getFile().getHash();
//        String signature = documentParty.getSignature();
//
//        return checkSign(fileHash, signature, "проверка подписи документа");
//    }

    private CheckResponseDto checkSign(String fileHash, String signature, String operationDescription) {
        if (signature == null || signature.isEmpty()) {
            return new CheckResponseDto(false, "Подпись отсутствует. Невозможно проверить.", null);
        }

        CheckSignRequest checkSignRequest = CheckSignRequest.builder()
                .hash(fileHash)
                .signBase64(signature)
                .build();

        CheckSignResponse checkSignResponse;
        try {
            checkSignResponse = cdsClient.checkSign(checkSignRequest);
        } catch (Exception e) {
            log.error("Ошибка при {}: {}", operationDescription, e.getMessage(), e);
            return new CheckResponseDto(false, "Ошибка при " + operationDescription + ": " + e.getMessage(), null);
        }

        if (checkSignResponse == null) {
            log.error("Проверка подписи не вернула ответ.");
            return new CheckResponseDto(false, "Ошибка при проверке подписи: Сервис не вернул ответ.", null);
        }

        return new CheckResponseDto(true, "OK", checkSignResponse);
    }
}
