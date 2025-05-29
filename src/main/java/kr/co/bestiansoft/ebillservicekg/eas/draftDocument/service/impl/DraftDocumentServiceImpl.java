package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.impl;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import kr.co.bestiansoft.ebillservicekg.common.file.service.PdfService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.repository.DraftDocumentRepository;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.DraftDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.HtmlTextSegment;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.SetFileIdVo;
import kr.co.bestiansoft.ebillservicekg.form.service.FormService;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.apache.poi.xwpf.usermodel.UnderlinePatterns;

import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
@Service
public class DraftDocumentServiceImpl implements DraftDocumentService {

    private final DraftDocumentRepository draftDocumentRepository;
    private final FormService formService;
    private final EDVHelper edv;
    private final PdfService pdfService;

    @Override
    public DraftDocumentVo insertDraftDocument(int formId, Map<String, String> map) {
        String loginId = new SecurityInfoUtil().getAccountId();
        DraftDocumentVo vo = new DraftDocumentVo();
        vo.setFormId(formId);
        //set
        vo.setAarsStatusCd("DS01");
        vo.setRegId(loginId);
        vo.setRegDt(LocalDateTime.now());
        try{
            SetFileIdVo fileIdVo = applyWordToPdf(map, vo.getFormId());
            vo.setAarsFileId(fileIdVo.getFileId());
            vo.setAarsPdfFileId(fileIdVo.getPdfFileId());
        }catch (Exception e){
            e.printStackTrace();
        }

        draftDocumentRepository.insertDraftDocument(vo);
        return vo;
    }

    /**
     * Updates the status of a draft document in the repository.
     *
     * @param aarsDocId    the unique identifier of the draft document to be updated
     * @param aarsStatusCd the new status code to update the draft document with
     */
    @Override
    public void updateDraftStatus(String aarsDocId, String aarsStatusCd) {
        draftDocumentRepository.updateDraftStatus(aarsDocId, aarsStatusCd);
    }

    public DraftDocumentVo getDraftDocument(String aarsDocId){
        return draftDocumentRepository.getDraftDocument(aarsDocId);
    }

    public SetFileIdVo applyWordToPdf(Map<String, String> paramMap, int formId) throws Exception {
        FormWithFieldsVo formList = formService.getFormWithFieldsById(formId);
        SetFileIdVo vo = new SetFileIdVo();
        String fileId = StringUtil.getUUUID();
        String pdfFileId = StringUtil.getUUUID();
        String fileName = "test.docx";
        File tmpFile = null;
        File tmpPdfFile = null;

        try {
            InputStream is = edv.download(formList.getFileId());
            tmpFile = File.createTempFile("temp", null);
            tmpFile = updateDocument(is, paramMap, tmpFile.getAbsolutePath());
            edv.save(fileId, new FileInputStream(tmpFile));
            vo.setFileId(fileId);

            // PDF 변환
            tmpPdfFile = File.createTempFile("pdfTemp", null);
            boolean pdfResult = pdfService.convertToPdf(tmpFile.getAbsolutePath(), fileName, tmpPdfFile.getAbsolutePath());

            if (pdfResult && tmpPdfFile.exists() && tmpPdfFile.length() > 0) {
                edv.save(pdfFileId, new FileInputStream(tmpPdfFile));
                vo.setPdfFileId(pdfFileId);
            } else {
                throw new RuntimeException("PDF 변환 실패");
            }

            return vo;

        } catch (Exception e) {
            throw new RuntimeException("파일 처리 중 오류 발생: " + e.getMessage(), e);
        } finally {
            // 임시 파일 삭제 시 null 체크
            if (tmpFile != null && tmpFile.exists()) {
                tmpFile.delete();
            }
            if (tmpPdfFile != null && tmpPdfFile.exists()) {
                tmpPdfFile.delete();
            }
        }
    }

    private File updateDocument(InputStream is, Map<String, String> map, String output)
            throws IOException, InvalidFormatException {
        File result = null;
        try (XWPFDocument doc = new XWPFDocument(is)) {
            replaceSimpleText(doc, map);

            // 파일 저장
            try (FileOutputStream out = new FileOutputStream(output)) {
                doc.write(out);
            }
            result = new File(output);
        }
        return result;
    }

    private void replaceSimpleText(XWPFDocument doc, Map<String, String> map) {
        log.debug("변수 치환 시작: {}", map.keySet());

        // 모든 단락 처리
        for (XWPFParagraph paragraph : doc.getParagraphs()) {
            replaceInParagraph(paragraph, map);
        }

        // 모든 테이블 처리
        for (XWPFTable table : doc.getTables()) {
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph paragraph : cell.getParagraphs()) {
                        replaceInParagraph(paragraph, map);
                    }
                }
            }
        }

        log.debug("변수 치환 완료");
    }

    private void replaceInParagraph(XWPFParagraph paragraph, String textToFind, String replacement) {
        String text = paragraph.getText();
        if (text != null && text.contains(textToFind)) {
            List<XWPFRun> runs = paragraph.getRuns();

            // 기존 런을 모두 제거
            for (int i = runs.size() - 1; i >= 0; i--) {
                paragraph.removeRun(i);
            }

            // 변경된 텍스트로 새 런 생성
            String newText = text.replace(textToFind, replacement);
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(newText);
        }
    }

    private void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> map) {
        String text = paragraph.getText();
        if (text == null || text.isEmpty()) {
            return;
        }

        log.debug("현재 문단 텍스트: {}", text);

        boolean containsVariable = false;
        String newText = text;

        // 모든 변수 찾기
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = "${" + entry.getKey() + "}";
            if (newText.contains(key)) {
                String value = entry.getValue();
                if (value != null) {
                    // HTML 태그 분석 후 텍스트 스타일 정보와 함께 처리
                    List<HtmlTextSegment> processedSegments = analyzeHtmlTags(value);

                    // 변수 키를 특별한 마커로 대체
                    newText = newText.replace(key, "[[PROCESSED_HTML]]");
                    containsVariable = true;
                    log.debug("변수 치환: {} -> HTML 콘텐츠", key);

                    // 변수가 포함된 경우 처리
                    if (containsVariable) {
                        // 기존 런 제거
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (int i = runs.size() - 1; i >= 0; i--) {
                            paragraph.removeRun(i);
                        }

                        // 텍스트를 마커 기준으로 분할
                        String[] parts = newText.split("\\[\\[PROCESSED_HTML\\]\\]");

                        // 첫 번째 부분 추가
                        if (parts.length > 0 && !parts[0].isEmpty()) {
                            XWPFRun run = paragraph.createRun();
                            run.setText(parts[0]);
                        }

                        // HTML 콘텐츠 추가 (스타일 정보 포함)
                        for (int i = 0; i < processedSegments.size(); i++) {
                            HtmlTextSegment segment = processedSegments.get(i);
                            XWPFRun run = paragraph.createRun();
                            
                            // 텍스트 설정
                            run.setText(segment.getText());
                            
                            // 기본 글꼴 적용 (항상 적용)
                            run.setFontFamily("Times New Roman");
                            
                            // 폰트 크기 적용
                            if (segment.getFontSize() <= 0) {
                                run.setFontSize(12.0);
                            } else {
                                run.setFontSize(segment.getFontSize());
                            }
                            
                            // 글꼴이 지정된 경우 덮어쓰기
                            if (segment.getFontFamily() != null && !segment.getFontFamily().isEmpty() 
                                    && !segment.getFontFamily().equals("Times New Roman")) {
                                run.setFontFamily(segment.getFontFamily());
                            } else {
                                log.info("기본 글꼴 Times New Roman 적용");
                            }
                            
                            // 굵게 적용
                            boolean isBold = segment.isBold();
                            run.setBold(isBold);
                            if (isBold) {
                                log.info("굵게 스타일 적용됨");
                            }
                            
                            // 기울임 적용
                            boolean isItalic = segment.isItalic();
                            run.setItalic(isItalic);
                            if (isItalic) {
                                log.info("기울임 스타일 적용됨");
                            }

                            
                            // 폰트 색상 적용
                            if (segment.getTextColor() != null) {
                                try {
                                    run.setColor(segment.getTextColor());
                                    log.info("텍스트 색상 {} 적용", segment.getTextColor());
                                } catch (Exception e) {
                                    log.error("색상 적용 중 오류: {}. 색상값: {}", e.getMessage(), segment.getTextColor());
                                }
                            }
                            
                            // 줄바꿈 처리
                            if (segment.isBreakAfter() && i < processedSegments.size() - 1) {
                                run.addBreak();
                                log.info("줄바꿈 추가");
                            }
                        }

                        // 나머지 부분 추가
                        if (parts.length > 1 && !parts[1].isEmpty()) {
                            XWPFRun run = paragraph.createRun();
                            run.setText(parts[1]);
                        }

                        return; // 이미 처리했으므로 종료
                    }
                }
            }
        }

        // 변수가 포함되었지만 위의 특별 처리가 실행되지 않은 경우
        if (containsVariable) {
            // 기존 런 제거
            List<XWPFRun> runs = paragraph.getRuns();
            for (int i = runs.size() - 1; i >= 0; i--) {
                paragraph.removeRun(i);
            }

            // 새 런 생성
            XWPFRun newRun = paragraph.createRun();
            newRun.setText(newText);
            log.debug("새 텍스트: {}", newText);
        }
    }

    /**
     * HTML 태그를 분석하고 텍스트 세그먼트 목록을 반환합니다.
     * 각 세그먼트는 텍스트와 해당 스타일 정보를 포함합니다.
     */
    private List<HtmlTextSegment> analyzeHtmlTags(String htmlContent) {
        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<HtmlTextSegment> segments = new ArrayList<>();

        try {
            Document doc = Jsoup.parse(htmlContent);

            // p 태그를 찾아 처리
            Elements paragraphs = doc.select("p");

            if (!paragraphs.isEmpty()) {
                for (Element paragraph : paragraphs) {
                    processElement(paragraph, segments);

                    // p 태그 후에 줄바꿈 설정
                    if (!segments.isEmpty()) {
                        segments.get(segments.size() - 1).setBreakAfter(true);
                    }
                }
            } else {
                // p 태그가 없는 경우 body를 처리
                processElement(doc.body(), segments);
            }

            return segments;
        } catch (Exception e) {
            log.error("HTML 분석 중 오류 발생: {}", e.getMessage());

            // 오류 발생 시 원본 텍스트를 단일 세그먼트로 반환
            HtmlTextSegment segment = new HtmlTextSegment(htmlContent);
            segments.add(segment);
            return segments;
        }
    }

    /**
     * HTML 요소를 처리하여 텍스트 세그먼트를 생성합니다.
     */
    private void processElement(Element element, List<HtmlTextSegment> segments) {
        // 텍스트 노드만 있는 경우 처리
        if (element.childNodes().size() == 1 && element.childNode(0) instanceof TextNode) {
            HtmlTextSegment segment = createSegmentFromElement(element);
            segments.add(segment);
            return;
        }

        // 자식 요소 처리
        for (Node child : element.childNodes()) {
            if (child instanceof TextNode) {
                String text = ((TextNode) child).text().trim();
                if (!text.isEmpty()) {
                    HtmlTextSegment segment = new HtmlTextSegment(text);
                    applyParentStyles(element, segment);
                    segments.add(segment);
                }
            } else if (child instanceof Element) {
                Element childElement = (Element) child;

                // 특수 태그 처리
                if (childElement.tagName().equals("br")) {
                    if (!segments.isEmpty()) {
                        segments.get(segments.size() - 1).setBreakAfter(true);
                    }
                } else {
                    processElement(childElement, segments);
                }
            }
        }
    }

    /**
     * HTML 요소에서 텍스트 세그먼트를 생성합니다.
     * 기본 폰트는 Times New Roman으로 설정합니다.
     */
    private HtmlTextSegment createSegmentFromElement(Element element) {
        HtmlTextSegment segment = new HtmlTextSegment(element.text());

        // 기본 폰트 설정
        segment.setFontFamily("Times New Roman");

        // 스타일 정보 적용
        applyStyles(element, segment);

        return segment;
    }


    /**
     * 부모 요소의 스타일을 세그먼트에 적용합니다.
     */
    private void applyParentStyles(Element parent, HtmlTextSegment segment) {
        // 부모 스타일 적용
        applyStyles(parent, segment);

        // 조상 요소의 스타일도 적용 (예: 중첩된 스타일)
        Element ancestor = parent.parent();
        while (ancestor != null && !ancestor.tagName().equals("body") && !ancestor.tagName().equals("html")) {
            applyStyles(ancestor, segment);
            ancestor = ancestor.parent();
        }
    }
    /**
     * 요소의 스타일을 세그먼트에 적용합니다.
     * CKEditor에서 생성된 Bold 스타일을 정확히 감지합니다.
     */
    private void applyStyles(Element element, HtmlTextSegment segment) {
        // 1. CKEditor에서 사용하는 태그 기반 스타일 감지
        String tagName = element.tagName().toLowerCase();
        if (tagName.equals("strong") || tagName.equals("b")) {
            segment.setBold(true);
        }

        if (tagName.equals("em") || tagName.equals("i")) {
            segment.setItalic(true);
        }

        // 2. CKEditor에서 생성된 span 태그의 스타일 속성 분석
        String style = element.attr("style");
        if (style != null && !style.isEmpty()) {
            // 굵게 (font-weight)
            if (style.contains("font-weight")) {
                String fontWeight = extractStyleValue(style, "font-weight");
                if (fontWeight != null) {
                    if (fontWeight.equals("bold") || fontWeight.equals("bolder") || 
                        (isNumeric(fontWeight) && Integer.parseInt(fontWeight) >= 700)) {
                        segment.setBold(true);
                    }
                }
            }

            // 기울임 (font-style)
            if (style.contains("font-style")) {
                String fontStyle = extractStyleValue(style, "font-style");
                if (fontStyle != null && (fontStyle.equals("italic") || fontStyle.equals("oblique"))) {
                    segment.setItalic(true);
                }
            }

        }

        // 3. CKEditor 특화 클래스 확인 (클래스 기반 스타일)
        String className = element.attr("class");
        if (className != null && !className.isEmpty()) {
            if (className.contains("Bold") || className.contains("strong") || 
                className.contains("cke_bold")) {
                segment.setBold(true);
            }
            
            if (className.contains("Italic") || className.contains("em") || 
                className.contains("cke_italic")) {
                segment.setItalic(true);
            }
        }
        
        // 4. 상위 요소의 스타일도 확인 (중첩된 요소에서 스타일이 상속되는 경우)
        Element parent = element.parent();
        if (parent != null && !parent.tagName().equals("body")) {
            // 부모 태그가 bold/italic인지 확인
            String parentTag = parent.tagName().toLowerCase();
            if (parentTag.equals("strong") || parentTag.equals("b")) {
                segment.setBold(true);
            }
            
            if (parentTag.equals("em") || parentTag.equals("i")) {
                segment.setItalic(true);
            }
            
            // 부모의 스타일 속성 확인
            String parentStyle = parent.attr("style");
            if (parentStyle != null && !parentStyle.isEmpty()) {
                if (parentStyle.contains("font-weight")) {
                    String fontWeight = extractStyleValue(parentStyle, "font-weight");
                    if (fontWeight != null && (fontWeight.equals("bold") || fontWeight.equals("bolder") ||
                        (isNumeric(fontWeight) && Integer.parseInt(fontWeight) >= 700))) {
                        segment.setBold(true);
                    }
                }
                
                if (parentStyle.contains("font-style")) {
                    String fontStyle = extractStyleValue(parentStyle, "font-style");
                    if (fontStyle != null && (fontStyle.equals("italic") || fontStyle.equals("oblique"))) {
                        segment.setItalic(true);
                    }
                }
            }
        }

        // span 태그에서 색상 추출
        if (element.tagName().equals("span")) {
            Elements colorSpans = element.select("span[style*=color]");
            if (!colorSpans.isEmpty()) {
                String style1 = colorSpans.first().attr("style");
                String color = extractStyleValue(style1, "color");
                if (color != null) {
                    String hexColor = convertToHexColor(color);
                    if (hexColor != null) {
                        segment.setTextColor(hexColor);
                    }
                }
            }
        }
    }

    /**
     * 문자열이 숫자인지 확인합니다.
     */
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * CSS 스타일 값을 추출합니다.
     */
    private String extractStyleValue(String style, String property) {
        Pattern pattern = Pattern.compile(property + "\\s*:\\s*([^;]+)");
        Matcher matcher = pattern.matcher(style);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
    }

    /**
     * 폰트 크기 문자열을 숫자로 파싱합니다.
     */
    private double parseFontSize(String fontSize) {
        try {
            if (fontSize.endsWith("pt")) {
                return Double.parseDouble(fontSize.substring(0, fontSize.length() - 2));
            } else if (fontSize.endsWith("px")) {
                // px를 pt로 대략 변환 (1px ≈ 0.75pt)
                return Double.parseDouble(fontSize.substring(0, fontSize.length() - 2)) * 0.75;
            } else {
                // 단위가 없는 경우 기본값 사용
                return Double.parseDouble(fontSize);
            }
        } catch (NumberFormatException e) {
            log.warn("폰트 크기 파싱 실패: {}", fontSize);
            return -1;
        }
    }

    /**
     * 색상 이름이나 RGB 값을 16진수 색상 코드로 변환합니다.
     * POI에서 사용할 수 있는 형식(# 없이)으로 반환합니다.
     */
    private String convertToHexColor(String colorValue) {
        if (colorValue == null) {
            return null;
        }

        // # 기호로 시작하는 16진수 형식인 경우 (예: #FF0000)
        if (colorValue.startsWith("#")) {
            return colorValue.substring(1).toUpperCase(); // # 제거하고 대문자로 변환
        }

        // RGB 형식인 경우 (예: rgb(255, 0, 0))
        if (colorValue.startsWith("rgb")) {
            Pattern pattern = Pattern.compile("rgb\\((\\d+),\\s*(\\d+),\\s*(\\d+)\\)");
            Matcher matcher = pattern.matcher(colorValue);
            if (matcher.find()) {
                int r = Integer.parseInt(matcher.group(1));
                int g = Integer.parseInt(matcher.group(2));
                int b = Integer.parseInt(matcher.group(3));
                return String.format("%02X%02X%02X", r, g, b);
            }
        }

        // 일반적인 색상 이름인 경우
        switch (colorValue.toLowerCase()) {
            case "red": return "FF0000";
            case "green": return "008000";
            case "blue": return "0000FF";
            case "black": return "000000";
            case "white": return "FFFFFF";
            case "yellow": return "FFFF00";
            // 추가 색상
            case "orange": return "FFA500";
            case "purple": return "800080";
            case "gray": case "grey": return "808080";
            case "pink": return "FFC0CB";
            case "brown": return "A52A2A";
            default:
                log.warn("지원되지 않는 색상 값: {}", colorValue);
                return null;
        }
    }




}