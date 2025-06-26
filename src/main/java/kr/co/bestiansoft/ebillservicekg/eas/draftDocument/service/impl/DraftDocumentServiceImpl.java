package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.impl;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import kr.co.bestiansoft.ebillservicekg.common.file.service.PdfService;
import kr.co.bestiansoft.ebillservicekg.common.file.service.impl.EDVHelper;
import kr.co.bestiansoft.ebillservicekg.common.utils.SecurityInfoUtil;
import kr.co.bestiansoft.ebillservicekg.common.utils.StringUtil;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.service.DraftDataService;
import kr.co.bestiansoft.ebillservicekg.eas.draftData.vo.DraftDataVo;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.repository.DraftDocumentRepository;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.service.DraftDocumentService;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.DraftDocumentVo;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.HtmlTextSegment;
import kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo.SetFileIdVo;
import kr.co.bestiansoft.ebillservicekg.form.service.FormService;
import kr.co.bestiansoft.ebillservicekg.form.vo.FormWithFieldsVo;
import kr.co.bestiansoft.ebillservicekg.formField.vo.FormFieldVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class DraftDocumentServiceImpl implements DraftDocumentService {

    private final DraftDocumentRepository draftDocumentRepository;
    private final FormService formService;
    private final EDVHelper edv;
    private final PdfService pdfService;
    private final DraftDataService draftDataService;

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
            draftDocumentRepository.insertDraftDocument(vo);

            List<FormFieldVo> fieldVo = formService.getFormWithFieldsById(vo.getFormId()).getFields();
            fieldVo.forEach(field -> {
               if(map.containsKey(field.getFieldValue())){
                   DraftDataVo dataVo = DraftDataVo.builder()
                           .aarsDocId(vo.getAarsDocId())
                           .formId(field.getFormId())
                           .fieldSeq(field.getFieldSeq())
                           .dataCn(map.get(field.getFieldValue()))
                           .build();

                   draftDataService.insertDraftData(dataVo);
               }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


        return vo;
    }
    
    /**
     * Updates the status of a draft document in the repository.
     *
     * @param aarsDocId    the unique identifier of the draft document to be updated
     * @param aarsStatusCd the new status code to update the draft document with
     */
    @Override
    public void updateDraftStatus(int aarsDocId, String aarsStatusCd) {
        draftDocumentRepository.updateDraftStatus(aarsDocId, aarsStatusCd);
    }

    public DraftDocumentVo getDraftDocument(int aarsDocId){
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
        log.info("변수 치환 시작: {}", map.keySet());

        // 모든 단락 처리 - 문서 순서대로 처리
        List<XWPFParagraph> paragraphs = new ArrayList<>(doc.getParagraphs());
        for (int i = 0; i < paragraphs.size(); i++) {
            XWPFParagraph paragraph = paragraphs.get(i);
            replaceInParagraph(paragraph, map);
        }

        // 모든 테이블 처리 - 문서 순서대로 처리
        List<XWPFTable> tables = new ArrayList<>(doc.getTables());
        for (int tableIndex = 0; tableIndex < tables.size(); tableIndex++) {
            XWPFTable table = tables.get(tableIndex);

            List<XWPFTableRow> rows = new ArrayList<>(table.getRows());
            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                XWPFTableRow row = rows.get(rowIndex);

                List<XWPFTableCell> cells = new ArrayList<>(row.getTableCells());
                for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
                    XWPFTableCell cell = cells.get(cellIndex);

                    List<XWPFParagraph> cellParagraphs = new ArrayList<>(cell.getParagraphs());
                    for (int paraIndex = 0; paraIndex < cellParagraphs.size(); paraIndex++) {
                        XWPFParagraph paragraph = cellParagraphs.get(paraIndex);
                        replaceInParagraph(paragraph, map);
                    }
                }
            }
        }

        log.info("변수 치환 완료");
    }

    
    private void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> map) {
        String text = paragraph.getText();

        if (text == null || text.isEmpty()) {
            return;
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String variable = "${" + entry.getKey() + "}";
            if (text.contains(variable)) {

                // ★ 수정: 변수 위치 찾기
                int varIndex = text.indexOf(variable);
                String beforeText = text.substring(0, varIndex);
                String afterText = text.substring(varIndex + variable.length());

                
                // 단락의 기존 정렬 정보 보존
                ParagraphAlignment originalAlignment = paragraph.getAlignment();

                // 모든 런 제거
                while (paragraph.getRuns().size() > 0) {
                    paragraph.removeRun(0);
                }

                // 정렬 정보 복원
                if (originalAlignment != null) {
                    paragraph.setAlignment(originalAlignment);
                }

                // ★ 핵심: 앞 텍스트 추가
                if (!beforeText.isEmpty()) {
                    XWPFRun beforeRun = paragraph.createRun();
                    beforeRun.setText(beforeText);
                    beforeRun.setFontFamily("Times New Roman");
                    beforeRun.setFontSize(12.0);
                }

                // 변수 값 처리
                if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                    processHtmlContent(paragraph, entry.getValue());
                }

                // ★ 핵심: 뒤 텍스트 추가
                if (!afterText.isEmpty()) {
                    XWPFRun afterRun = paragraph.createRun();
                    afterRun.setText(afterText);
                    afterRun.setFontFamily("Times New Roman");
                    afterRun.setFontSize(12.0);
                }

                break; // 하나씩 처리
            }
        }
    }




    // 일반 HTML 콘텐츠 처리
    private void processHtmlContent(XWPFParagraph paragraph, String htmlValue) {
        if (htmlValue == null || htmlValue.isEmpty()) {
            return;
        }

        Document doc = Jsoup.parse(htmlValue);
        Elements allElements = doc.body().children();

        if (allElements.isEmpty()) {
            processSimpleHtmlContent(paragraph, htmlValue);
            return;
        }


        XWPFParagraph lastInsertedParagraph = paragraph;
        boolean isFirstElement = true;

        for (int i = 0; i < allElements.size(); i++) {
            Element element = allElements.get(i);
            String tagName = element.tagName().toLowerCase();


            if (tagName.equals("p")) {
                XWPFParagraph targetParagraph;

                if (isFirstElement) {
                    targetParagraph = paragraph;
                    isFirstElement = false;
                } else {
                    targetParagraph = insertParagraphAfter(lastInsertedParagraph);
                }

                String pAlignment = extractAlignment(element);
                if (pAlignment != null && !pAlignment.isEmpty()) {
                    setAlignment(targetParagraph, pAlignment);
                }

                processParagraphContent(targetParagraph, element);
                lastInsertedParagraph = targetParagraph;

            } else if (tagName.equals("ul") || tagName.equals("ol")) {
                XWPFParagraph lastListParagraph = processListAfterParagraph(lastInsertedParagraph, element);
                if (lastListParagraph != null) {
                    lastInsertedParagraph = lastListParagraph;
                }
                isFirstElement = false;

            } else if (tagName.equals("table")) {
                processTableAfterParagraph(lastInsertedParagraph, element);
                isFirstElement = false;
            }

        }

    }


    /**
     * 참조 단락 바로 다음에 새로운 단락을 삽입합니다.
     */
    private XWPFParagraph insertParagraphAfter(XWPFParagraph referenceParagraph) {
        try {

            XmlCursor cursor = referenceParagraph.getCTP().newCursor();

            boolean moveResult = cursor.toNextSibling();

            if (!moveResult) {
                cursor.toEndToken();
            }


            // 새 단락 삽입
            XWPFParagraph newParagraph = referenceParagraph.getDocument().insertNewParagraph(cursor);


            // 커서 정리
            cursor.dispose();

            return newParagraph != null ? newParagraph : referenceParagraph.getDocument().createParagraph();

        } catch (Exception e) {
            log.error("insertParagraphAfter 중 오류 발생: {}", e.getMessage(), e);
            return referenceParagraph.getDocument().createParagraph();
        }
    }



    /**
     * 참조 단락 다음에 리스트를 삽입합니다.
     */
    private XWPFParagraph processListAfterParagraph(XWPFParagraph referenceParagraph, Element listElement) {

        boolean isOrdered = listElement.tagName().equalsIgnoreCase("ol");
        BigInteger numId = createWordNumbering(referenceParagraph.getDocument(), isOrdered);

        Elements items = listElement.select("> li");

        XWPFParagraph lastParagraph = referenceParagraph;

        for (int i = 0; i < items.size(); i++) {
            Element item = items.get(i);
            String directText = getDirectTextFromListItem(item);

            // ★ 핵심 수정: 직접 텍스트가 있는 경우에만 리스트 항목 생성
            if (!directText.trim().isEmpty()) {

                try {
                    XWPFParagraph listParagraph = insertParagraphAfter(lastParagraph);

                    listParagraph.setNumID(numId);
                    setListLevel(listParagraph, numId, 0);

                    XWPFRun run = listParagraph.createRun();
                    run.setText(directText);
                    run.setFontFamily("Times New Roman");
                    run.setFontSize(12.0);

                    Elements spans = item.select("span");
                    if (!spans.isEmpty()) {
                        applySpanStyles(run, spans.first());
                    }

                    lastParagraph = listParagraph;

                } catch (Exception itemError) {
                    log.error("리스트 항목 {} 처리 중 오류: {}", i + 1, itemError.getMessage(), itemError);
                }
            }

            // ★ 핵심 수정: 직접 텍스트 여부와 관계없이 중첩 리스트 처리
            Elements nestedLists = item.select("> ul, > ol");
            if (!nestedLists.isEmpty()) {
                for (int j = 0; j < nestedLists.size(); j++) {
                    Element nestedList = nestedLists.get(j);
                    try {
                        lastParagraph = processNestedListAfterParagraph(lastParagraph, nestedList, 1);
                    } catch (Exception nestedError) {
                        log.error("중첩 리스트 처리 중 오류: {}", nestedError.getMessage(), nestedError);
                    }
                }
            }
        }
        return lastParagraph;
    }




    /**
     * 중첩 리스트를 참조 단락 다음에 삽입합니다.
     */
    private XWPFParagraph processNestedListAfterParagraph(XWPFParagraph referenceParagraph, Element nestedList, int level) {

        try {
            boolean isOrdered = nestedList.tagName().equalsIgnoreCase("ol");
            BigInteger numId = createWordNumbering(referenceParagraph.getDocument(), isOrdered);

            Elements items = nestedList.select("> li");

            XWPFParagraph lastParagraph = referenceParagraph;

            for (int i = 0; i < items.size(); i++) {
                Element item = items.get(i);
                String directText = getDirectTextFromListItem(item);


                if (!directText.trim().isEmpty()) {

                    try {
                        XWPFParagraph listParagraph = insertParagraphAfter(lastParagraph);

                        listParagraph.setNumID(numId);
                        setListLevel(listParagraph, numId, level);

                        XWPFRun run = listParagraph.createRun();
                        run.setText(directText);
                        run.setFontFamily("Times New Roman");
                        run.setFontSize(12.0);

                        Elements spans = item.select("span");
                        if (!spans.isEmpty()) {
                            applySpanStyles(run, spans.first());
                        }

                        lastParagraph = listParagraph;

                    } catch (Exception itemError) {
                        log.error("중첩 리스트 항목 {} 처리 중 오류: {}", i + 1, itemError.getMessage());
                    }
                } else {
                    log.warn("중첩 리스트 {}번째 항목에서 텍스트를 추출하지 못함", i + 1);
                }
            }

            return lastParagraph;

        } catch (Exception e) {
            log.error("중첩 리스트 처리 중 전체 오류: {}", e.getMessage(), e);
            return referenceParagraph;
        }
    }


    /**
     * 참조 단락 다음에 테이블을 삽입합니다.
     */
    private void processTableAfterParagraph(XWPFParagraph referenceParagraph, Element tableElement) {
        try {

            XWPFDocument doc = referenceParagraph.getDocument();

            XmlCursor cursor = referenceParagraph.getCTP().newCursor();

            // ★ 수정: toNextSibling() 대신 toEndToken() 사용
            cursor.toEndToken();

            XWPFTable table = doc.insertNewTbl(cursor);

            cursor.dispose();

            table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
            table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
            table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
            table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
            table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
            table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");

            CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
            width.setType(STTblWidth.PCT);
            width.setW(BigInteger.valueOf(5000));

            // 행 처리 (기존 로직 유지)
            Elements rows = tableElement.select("tr");

            for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                Element row = rows.get(rowIndex);
                XWPFTableRow tableRow = rowIndex < table.getNumberOfRows() ?
                        table.getRow(rowIndex) : table.createRow();

                Elements cells = row.select("td, th");
                for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
                    Element cell = cells.get(cellIndex);

                    while (tableRow.getTableCells().size() <= cellIndex) {
                        tableRow.createCell();
                    }

                    XWPFTableCell tableCell = tableRow.getCell(cellIndex);
                    processTableCellMerge(table, tableRow, tableCell, cell, rowIndex, cellIndex);
                    processTableCellBackground(tableCell, cell);
                    processTableCellContent(tableCell, cell);
                }
            }


        } catch (Exception e) {
            log.debug("커서 방식 실패, 대안 사용: {}", e.getMessage());
            processHtmlTable(referenceParagraph.getDocument(), tableElement.outerHtml(), referenceParagraph);
        }
    }

    /** 
     *  HTML 테이블을 처리하는 메소드
     */
    private void processHtmlTable(XWPFDocument doc, String htmlContent, XWPFParagraph currentParagraph) {
        try {
            Document jsoupDoc = Jsoup.parse(htmlContent);
            Elements tables = jsoupDoc.select("table");

            if (tables.isEmpty()) {
                processHtmlContent(currentParagraph, htmlContent);
                return;
            }

            // 현재 단락의 커서 위치에 테이블 삽입
            XmlCursor cursor = currentParagraph.getCTP().newCursor();

            for (Element tableElement : tables) {
                // 현재 위치에 테이블 삽입
                XWPFTable table = doc.insertNewTbl(cursor);

                table.setBottomBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                table.setTopBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                table.setLeftBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                table.setRightBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                table.setInsideHBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");
                table.setInsideVBorder(XWPFTable.XWPFBorderType.SINGLE, 1, 0, "000000");

                CTTblWidth width = table.getCTTbl().addNewTblPr().addNewTblW();
                width.setType(STTblWidth.PCT);
                width.setW(BigInteger.valueOf(5000));

                Elements rows = tableElement.select("tr");
                for (int rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
                    Element row = rows.get(rowIndex);
                    XWPFTableRow tableRow = rowIndex < table.getNumberOfRows() ?
                            table.getRow(rowIndex) : table.createRow();

                    Elements cells = row.select("td, th");
                    for (int cellIndex = 0; cellIndex < cells.size(); cellIndex++) {
                        Element cell = cells.get(cellIndex);
                        while (tableRow.getTableCells().size() <= cellIndex) {
                            tableRow.createCell();
                        }
                        XWPFTableCell tableCell = tableRow.getCell(cellIndex);
                        processTableCellMerge(table, tableRow, tableCell, cell, rowIndex, cellIndex);
                        processTableCellBackground(tableCell, cell);
                        processTableCellContent(tableCell, cell);
                    }
                }

                cursor.toNextToken();
            }
        } catch (Exception e) {
            log.error("HTML 테이블 처리 중 오류: {}", e.getMessage(), e);
            processHtmlContent(currentParagraph, htmlContent);
        }
    }

    // 셀 병합 처리
    private void processTableCellMerge(XWPFTable table, XWPFTableRow tableRow, XWPFTableCell tableCell,
                                       Element cell, int rowIndex, int cellIndex) {
        String rowspan = cell.attr("rowspan");
        String colspan = cell.attr("colspan");

        // 세로 병합 처리
        if (!rowspan.isEmpty() && isNumeric(rowspan)) {
            int rowspanValue = Integer.parseInt(rowspan);
            if (rowspanValue > 1) {
                CTVMerge vmerge = tableCell.getCTTc().addNewTcPr().addNewVMerge();
                vmerge.setVal(STMerge.RESTART);

                // 아래 셀들 병합 처리
                for (int r = 1; r < rowspanValue; r++) {
                    // 필요한 행 생성
                    while (table.getNumberOfRows() <= rowIndex + r) {
                        table.createRow();
                    }

                    XWPFTableRow nextRow = table.getRow(rowIndex + r);

                    // 필요한 셀 생성
                    while (nextRow.getTableCells().size() <= cellIndex) {
                        nextRow.createCell();
                    }

                    XWPFTableCell nextCell = nextRow.getCell(cellIndex);
                    CTVMerge vmergeNext = nextCell.getCTTc().addNewTcPr().addNewVMerge();
                    vmergeNext.setVal(STMerge.CONTINUE);
                }
            }
        }

        // 가로 병합 처리
        if (!colspan.isEmpty() && isNumeric(colspan)) {
            int colspanValue = Integer.parseInt(colspan);
            if (colspanValue > 1) {
                CTHMerge hmerge = tableCell.getCTTc().addNewTcPr().addNewHMerge();
                hmerge.setVal(STMerge.RESTART);

                // 옆 셀들 병합 처리
                for (int c = 1; c < colspanValue; c++) {
                    // 필요한 셀 생성
                    while (tableRow.getTableCells().size() <= cellIndex + c) {
                        tableRow.createCell();
                    }

                    XWPFTableCell nextCell = tableRow.getCell(cellIndex + c);
                    CTHMerge hmergeNext = nextCell.getCTTc().addNewTcPr().addNewHMerge();
                    hmergeNext.setVal(STMerge.CONTINUE);
                }
            }
        }
    }

    // 셀 배경색 처리
    private void processTableCellBackground(XWPFTableCell tableCell, Element cell) {
        String bgColor = cell.attr("bgcolor");
        if (bgColor == null || bgColor.isEmpty()) {
            // style 속성에서 배경색 추출
            String style = cell.attr("style");
            if (style != null && !style.isEmpty()) {
                bgColor = extractStyleValue(style, "background-color");
            }
        }

        if (bgColor != null && !bgColor.isEmpty()) {
            String hexColor = convertToHexColor(bgColor);
            if (hexColor != null) {
                CTTcPr tcPr = tableCell.getCTTc().isSetTcPr() ?
                        tableCell.getCTTc().getTcPr() : tableCell.getCTTc().addNewTcPr();
                CTShd shd = tcPr.isSetShd() ? tcPr.getShd() : tcPr.addNewShd();
                shd.setVal(STShd.CLEAR);
                shd.setColor("auto");
                shd.setFill(hexColor);
            }
        }
    }

    private void processTableCellContent(XWPFTableCell tableCell, Element cell) {
        // 기존 단락들 제거
        while (tableCell.getParagraphs().size() > 0) {
            tableCell.removeParagraph(0);
        }

        XWPFParagraph paragraph = tableCell.addParagraph();

        // 단락 간격 제거
        paragraph.setSpacingAfter(0);
        paragraph.setSpacingBefore(0);
        paragraph.setSpacingBetween(1.0);

        // 셀 자체의 정렬 확인
        String cellAlignment = extractAlignment(cell);

        // 셀 내부 p 태그의 정렬 확인
        Elements pElements = cell.select("p[style*=text-align]");
        String pAlignment = null;
        if (!pElements.isEmpty()) {
            pAlignment = extractAlignment(pElements.first());
        }

        // 우선순위: p 태그 정렬 > 셀 정렬
        String finalAlignment = pAlignment != null ? pAlignment : cellAlignment;

        if (finalAlignment != null && !finalAlignment.isEmpty()) {
            setAlignment(paragraph, finalAlignment);
        }

        // 수직 정렬 처리
        String verticalAlign = extractVerticalAlignment(cell);
        if (verticalAlign != null && !verticalAlign.isEmpty()) {
            applyCellVerticalAlignment(tableCell, verticalAlign);
        }

        // 콘텐츠 처리
        processTableCellTextContent(paragraph, cell);
    }

    private void processTableCellTextContent(XWPFParagraph paragraph, Element cell) {
        // p 태그 내부의 텍스트만 추출하여 처리
        Elements pElements = cell.select("p");

        if (pElements.isEmpty()) {
            // p 태그가 없으면 셀의 직접 텍스트 처리
            String cellText = cell.text().trim();
            if (!cellText.isEmpty()) {
                XWPFRun run = paragraph.createRun();
                run.setText(cellText);
                run.setFontFamily("Times New Roman");
                run.setFontSize(12.0);
            }
        } else {
            // p 태그가 있으면 각 p 태그 처리
            for (Element p : pElements) {
                List<HtmlTextSegment> segments = analyzeHtmlTags(p.html());

                for (HtmlTextSegment segment : segments) {
                    if (segment.getText() == null || segment.getText().isEmpty()) {
                        continue;
                    }

                    XWPFRun run = paragraph.createRun();
                    run.setText(segment.getText());
                    applyTextStyles(run, segment);
                }
            }
        }
    }



    /**
     * 텍스트 스타일을 XWPFRun에 적용합니다.
     */
    private void applyTextStyles(XWPFRun run, HtmlTextSegment segment) {
        // 기본 글꼴 적용
        if (segment.getFontFamily() != null && !segment.getFontFamily().isEmpty()) {
            run.setFontFamily(segment.getFontFamily());
        } else {
            run.setFontFamily("Times New Roman");
        }

        // 폰트 크기 적용
        if (segment.getFontSize() > 0) {
            run.setFontSize(segment.getFontSize());
        } else {
            run.setFontSize(12.0);
        }

        // 굵게 적용
        run.setBold(segment.isBold());

        // 기울임 적용
        run.setItalic(segment.isItalic());

        // 취소선 적용
        if (segment.isStrike()) {
            run.setStrikeThrough(true);
        }

        // 폰트 색상 적용
        if (segment.getTextColor() != null && !segment.getTextColor().isEmpty()) {
            try {
                run.setColor(segment.getTextColor());
            } catch (Exception e) {
                log.error("색상 적용 중 오류: {}", e.getMessage());
            }
        }

        // 줄바꿈 처리
        if (segment.isBreakAfter()) {
            run.addBreak();
        }
    }




    /**
     * HTML 태그를 분석하고 텍스트 세그먼트 목록을 반환합니다.
     * 각 세그먼트는 텍스트와 해당 스타일 정보를 포함합니다.
     */
    private List<HtmlTextSegment> analyzeHtmlTags(String htmlContent) {
        List<HtmlTextSegment> segments = new ArrayList<>();

        if (htmlContent == null || htmlContent.isEmpty()) {
            return segments;
        }

        try {
            // HTML 내용 파싱
            Document doc = Jsoup.parse("<div>" + htmlContent + "</div>");
            Element root = doc.body().child(0); // 최상위 div 요소

            // 전체 정렬 정보 확인 (최상위 요소의 정렬 정보)
            String rootAlignment = extractAlignment(root);
            if (rootAlignment != null && !rootAlignment.isEmpty()) {
                // 정렬 정보가 있으면 첫 번째 세그먼트에 저장
                HtmlTextSegment alignmentSegment = new HtmlTextSegment("");
                alignmentSegment.setAlignment(rootAlignment);
                segments.add(alignmentSegment);
            }

            // 자식 요소들을 순서대로 처리
            for (Node childNode : root.childNodes()) {
                if (childNode instanceof Element) {
                    processElement((Element) childNode, segments);
                } else if (childNode instanceof TextNode) {
                    TextNode textNode = (TextNode) childNode;
                    String text = textNode.text().trim();
                    if (!text.isEmpty()) {
                        // 텍스트 노드의 텍스트 추가
                        HtmlTextSegment segment = new HtmlTextSegment(text);
                        segments.add(segment);
                    }
                }
            }
        } catch (Exception e) {
            log.error("HTML 태그 분석 중 오류: {}", e.getMessage(), e);
            // 오류 발생 시 원본 텍스트를 그대로 반환
            HtmlTextSegment segment = new HtmlTextSegment(htmlContent);
            segments.add(segment);
        }

        return segments;
    }



    /**
     * HTML 요소를 처리하여 텍스트 세그먼트를 생성합니다.
     */
    private void processElement(Element element, List<HtmlTextSegment> segments) {
        // 요소의 정렬 정보 확인
        String elementAlignment = extractAlignment(element);
        if (elementAlignment != null && !elementAlignment.isEmpty()) {
            // 정렬 정보가 있으면 세그먼트에 저장 ← 여기서만 저장하고 실제 적용은 안됨
            HtmlTextSegment alignmentSegment = new HtmlTextSegment("");
            alignmentSegment.setAlignment(elementAlignment);
            segments.add(alignmentSegment);
        }

        // 특수 요소 처리 (테이블, 리스트 등)
        String tagName = element.tagName().toLowerCase();

        if (tagName.equals("table")) {
            // 테이블 처리
            processTable(element, segments);
            return;
        } else if (tagName.equals("ul") || tagName.equals("ol")) {
            // 리스트 처리
            processList(element, segments);
            return;
        }

        // 요소 자체의 텍스트 내용 처리 (자식 요소 제외)
        String ownText = element.ownText().trim();
        if (!ownText.isEmpty()) {
            HtmlTextSegment segment = createSegmentFromElement(element);
            segment.setText(ownText);

            // 부모 요소의 스타일 적용
            applyParentStyles(element.parent(), segment);

            // 요소 자체의 스타일 적용
            applyStyles(element, segment);

            segments.add(segment);
        }

        // 자식 노드들을 순서대로 처리
        for (Node childNode : element.childNodes()) {
            if (childNode instanceof Element) {
                processElement((Element) childNode, segments);
            } else if (childNode instanceof TextNode) {
                TextNode textNode = (TextNode) childNode;
                String text = textNode.text().trim();
                if (!text.isEmpty() && !text.equals(ownText)) {
                    // 텍스트 노드의 텍스트 추가 (요소 자체 텍스트와 중복되지 않도록)
                    HtmlTextSegment segment = createSegmentFromElement(element);
                    segment.setText(text);

                    // 부모 요소의 스타일 적용
                    applyParentStyles(element, segment);

                    segments.add(segment);
                }
            }
        }

        // 줄바꿈이 필요한 블록 요소인 경우 처리
        if (tagName.matches("^(p|div|h[1-6]|li|br)$") && !segments.isEmpty()) {
            HtmlTextSegment lastSegment = segments.get(segments.size() - 1);
            lastSegment.setBreakAfter(true);
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
    private void applyParentStyles(Element element, HtmlTextSegment segment) {
        // 부모 스타일 적용
        applyStyles(element, segment);

        // 부모 요소에서 정렬 정보 확인
        String alignment = extractAlignment(element);
        if (alignment != null && !alignment.isEmpty()) {
            segment.setAlignment(alignment);
        }


        // 조상 요소의 스타일도 적용 (예: 중첩된 스타일)
        Element ancestor = element.parent();
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
            // 폰트 크기 (font-size)
            if (style.contains("font-size")) {
                String fontSize = extractStyleValue(style, "font-size");
                if (fontSize != null) {
                    double size = parseFontSize(fontSize);
                    if (size > 0) {
                        segment.setFontSize(size);
                    }
                }
            }


        }

        // span 태그에서 직접 size 속성 확인 (CKEditor 특화)
        if (element.tagName().equalsIgnoreCase("span")) {
            String size = element.attr("size");
            if (size != null && !size.isEmpty() && isNumeric(size)) {
                // HTML size 속성은 1-7 범위를 가지므로 적절히 변환
                int htmlSize = Integer.parseInt(size);
                double ptSize = convertHtmlSizeToPt(htmlSize);
                segment.setFontSize(ptSize);
            }
        }

        // 3. CKEditor 특화 클래스 확인 (클래스 기반 스타일)
        String className = element.attr("class");
        if (className != null && !className.isEmpty()) {
            // CKEditor의 폰트 크기 클래스 패턴: cke_fontSize_X 또는 font-size-X
            Pattern fontSizePattern = Pattern.compile("(cke_fontSize_|font-size-)(\\d+)");
            Matcher matcher = fontSizePattern.matcher(className);
            if (matcher.find()) {
                String fontSizeStr = matcher.group(2);
                try {
                    double fontSize = Double.parseDouble(fontSizeStr);
                    segment.setFontSize(fontSize);
                } catch (NumberFormatException e) {
                    log.warn("폰트 크기 클래스 파싱 실패: {}", className);
                }
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
    /**
     * HTML size 속성(1-7)을 포인트 크기로 변환합니다.
     */
    private double convertHtmlSizeToPt(int htmlSize) {
        switch (htmlSize) {
            case 1: return 8.0;
            case 2: return 10.0;
            case 3: return 12.0;
            case 4: return 14.0;
            case 5: return 18.0;
            case 6: return 24.0;
            case 7: return 36.0;
            default: return 12.0; // 기본값
        }
    }

    /**
     * 폰트 크기 문자열을 숫자로 파싱합니다.
     * 다양한 단위(pt, px, em 등)를 처리합니다.
     */
    private double parseFontSize(String fontSize) {
        try {
            fontSize = fontSize.trim().toLowerCase();

            if (fontSize.endsWith("pt")) {
                return Double.parseDouble(fontSize.substring(0, fontSize.length() - 2));
            } else if (fontSize.endsWith("px")) {
                // px를 pt로 변환 (1px ≈ 0.75pt)
                return Double.parseDouble(fontSize.substring(0, fontSize.length() - 2)) * 0.75;
            } else if (fontSize.endsWith("em")) {
                // em을 pt로 변환 (1em = 12pt로 가정)
                return Double.parseDouble(fontSize.substring(0, fontSize.length() - 2)) * 12.0;
            } else if (fontSize.endsWith("%")) {
                // 퍼센트를 pt로 변환 (100% = 12pt로 가정)
                return Double.parseDouble(fontSize.substring(0, fontSize.length() - 1)) * 0.12;
            } else if (fontSize.equals("xx-small")) {
                return 8.0;
            } else if (fontSize.equals("x-small")) {
                return 10.0;
            } else if (fontSize.equals("small")) {
                return 12.0;
            } else if (fontSize.equals("medium")) {
                return 14.0;
            } else if (fontSize.equals("large")) {
                return 18.0;
            } else if (fontSize.equals("x-large")) {
                return 24.0;
            } else if (fontSize.equals("xx-large")) {
                return 36.0;
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
     * 테이블 처리를 위한 메소드
     * 테이블 시작/종료를 나타내는 특수 세그먼트를 생성합니다.
     */
    private void processTable(Element tableElement, List<HtmlTextSegment> segments) {
        // 테이블 시작 세그먼트 생성
        HtmlTextSegment tableStartSegment = new HtmlTextSegment("");
        tableStartSegment.setTableStart(true);

        // 테이블 속성 설정
        int rows = tableElement.select("tr").size();
        int cols = 0;

        // 가장 많은 열 수를 찾음
        for (Element row : tableElement.select("tr")) {
            int cellCount = row.select("td, th").size();
            if (cellCount > cols) {
                cols = cellCount;
            }
        }

        tableStartSegment.setTableRows(rows);
        tableStartSegment.setTableCols(cols);

        // 테이블 스타일 추출
        String tableStyle = tableElement.attr("style");
        if (tableStyle != null && !tableStyle.isEmpty()) {
            tableStartSegment.setTableStyle(tableStyle);

            // 테이블 너비 추출
            String width = extractStyleValue(tableStyle, "width");
            if (width != null) {
                tableStartSegment.setTableWidth(parseSize(width));
            }

            // 테이블 테두리 추출
            String border = extractStyleValue(tableStyle, "border");
            if (border != null) {
                tableStartSegment.setTableBorderSize(parseBorderSize(border));
            }
        }

        // 테이블 너비가 지정되지 않은 경우 기본값 설정
        if (tableStartSegment.getTableWidth() <= 0) {
            tableStartSegment.setTableWidth(100.0); // 기본 너비 100%
        }

        segments.add(tableStartSegment);

        // 테이블 행 처리
        processTableRows(tableElement, segments);

        // 테이블 종료 세그먼트 생성
        HtmlTextSegment tableEndSegment = new HtmlTextSegment("");
        tableEndSegment.setTableEnd(true);
        segments.add(tableEndSegment);

        // 테이블 후 줄바꿈 추가
        HtmlTextSegment breakSegment = new HtmlTextSegment("");
        breakSegment.setBreakAfter(true);
        segments.add(breakSegment);
    }

    /**
     * 테이블 행을 처리합니다.
     */
    private void processTableRows(Element tableElement, List<HtmlTextSegment> segments) {
        Elements rows = tableElement.select("tr");
        int rowIndex = 0;

        for (Element row : rows) {
            // 행 시작 세그먼트
            HtmlTextSegment rowStartSegment = new HtmlTextSegment("");
            rowStartSegment.setTableRowStart(true);
            rowStartSegment.setTableRowIndex(rowIndex);

            // 행 스타일 추출
            String rowStyle = row.attr("style");
            if (rowStyle != null && !rowStyle.isEmpty()) {
                rowStartSegment.setTableRowStyle(rowStyle);

                // 행 높이 추출
                String height = extractStyleValue(rowStyle, "height");
                if (height != null) {
                    rowStartSegment.setTableRowHeight(parseSize(height));
                }
            }

            segments.add(rowStartSegment);

            // 행의 셀 처리
            processTableCells(row, segments, rowIndex);

            // 행 종료 세그먼트
            HtmlTextSegment rowEndSegment = new HtmlTextSegment("");
            rowEndSegment.setTableRowEnd(true);
            segments.add(rowEndSegment);

            rowIndex++;
        }
    }

    /**
     * 테이블 셀을 처리합니다.
     */
    private void processTableCells(Element rowElement, List<HtmlTextSegment> segments, int rowIndex) {
        Elements cells = rowElement.select("td, th");
        int colIndex = 0;

        for (Element cell : cells) {
            // 셀 시작 세그먼트
            HtmlTextSegment cellStartSegment = new HtmlTextSegment("");
            cellStartSegment.setTableCellStart(true);
            cellStartSegment.setTableRowIndex(rowIndex);
            cellStartSegment.setTableColIndex(colIndex);

            // 셀 병합 처리
            String rowspan = cell.attr("rowspan");
            String colspan = cell.attr("colspan");

            if (!rowspan.isEmpty() && isNumeric(rowspan)) {
                cellStartSegment.setTableRowSpan(Integer.parseInt(rowspan));
            }

            if (!colspan.isEmpty() && isNumeric(colspan)) {
                cellStartSegment.setTableColSpan(Integer.parseInt(colspan));
            }

            // 셀 스타일 추출
            String cellStyle = cell.attr("style");
            if (cellStyle != null && !cellStyle.isEmpty()) {
                cellStartSegment.setTableCellStyle(cellStyle);

                // 배경색 추출
                String bgColor = extractStyleValue(cellStyle, "background-color");
                if (bgColor != null) {
                    cellStartSegment.setTableCellBgColor(convertToHexColor(bgColor));
                }

                // 가로 정렬 추출
                String textAlign = extractStyleValue(cellStyle, "text-align");
                if (textAlign != null) {
                    cellStartSegment.setAlignment(textAlign);
                }

                // 세로 정렬 추출
                String verticalAlign = extractStyleValue(cellStyle, "vertical-align");
                if (verticalAlign != null) {
                    cellStartSegment.setTableCellVerticalAlign(verticalAlign);
                }
            }

            segments.add(cellStartSegment);

            // 셀 내용 처리
            processCellContent(cell, segments);

            // 셀 종료 세그먼트
            HtmlTextSegment cellEndSegment = new HtmlTextSegment("");
            cellEndSegment.setTableCellEnd(true);
            segments.add(cellEndSegment);

            colIndex++;
        }
    }

    /**
     * 셀 내용을 처리합니다.
     */
    private void processCellContent(Element cellElement, List<HtmlTextSegment> segments) {
        // 셀 내의 모든 자식 요소 처리
        if (cellElement.children().isEmpty()) {
            // 텍스트만 있는 경우
            String cellText = cellElement.text().trim();
            if (!cellText.isEmpty()) {
                HtmlTextSegment textSegment = new HtmlTextSegment(cellText);
                applyParentStyles(cellElement, textSegment);
                segments.add(textSegment);
            }
        } else {
            // 요소가 있는 경우 각 요소 처리
            for (Element child : cellElement.children()) {
                processElement(child, segments);
            }
        }
    }

    /**
     * 크기 문자열을 숫자로 파싱합니다.
     */
    private double parseSize(String sizeStr) {
        try {
            sizeStr = sizeStr.trim().toLowerCase();

            if (sizeStr.endsWith("px")) {
                return Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2));
            } else if (sizeStr.endsWith("pt")) {
                return Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2));
            } else if (sizeStr.endsWith("%")) {
                return Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 1));
            } else if (sizeStr.endsWith("cm")) {
                // cm를 포인트로 변환 (1cm = 28.35pt)
                return Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2)) * 28.35;
            } else if (sizeStr.endsWith("mm")) {
                // mm를 포인트로 변환 (1mm = 2.835pt)
                return Double.parseDouble(sizeStr.substring(0, sizeStr.length() - 2)) * 2.835;
            } else {
                // 단위가 없는 경우
                return Double.parseDouble(sizeStr);
            }
        } catch (NumberFormatException e) {
            log.warn("크기 파싱 실패: {}", sizeStr);
            return 0;
        }
    }

    /**
     * 테두리 크기를 파싱합니다.
     */
    private double parseBorderSize(String borderStr) {
        try {
            // 패턴: 1px solid #000000 또는 1px solid black 등
            Pattern pattern = Pattern.compile("(\\d+(?:\\.\\d+)?)\\s*px");
            Matcher matcher = pattern.matcher(borderStr);

            if (matcher.find()) {
                return Double.parseDouble(matcher.group(1));
            }

            return 1.0; // 기본 테두리 크기
        } catch (Exception e) {
            log.warn("테두리 크기 파싱 실패: {}", borderStr);
            return 1.0;
        }
    }

    /**
     * 리스트를 처리합니다.
     */
    private void processList(Element listElement, List<HtmlTextSegment> segments) {
        boolean isOrdered = listElement.tagName().equalsIgnoreCase("ol");

        // 리스트 시작 세그먼트
        HtmlTextSegment startSegment = new HtmlTextSegment("");
        startSegment.setListStart(true);
        startSegment.setOrderedList(isOrdered);
        segments.add(startSegment);

        // 리스트 항목 처리
        Elements items = listElement.select("> li");
        int itemNumber = 1;

        for (Element item : items) {
            // 리스트 항목 시작
            HtmlTextSegment itemStartSegment = new HtmlTextSegment("");
            itemStartSegment.setListItemStart(true);
            itemStartSegment.setListItemNumber(itemNumber++);
            itemStartSegment.setOrderedList(isOrdered);
            itemStartSegment.setListLevel(0); // 기본 레벨
            segments.add(itemStartSegment);

            // 항목 내용 처리
            if (item.children().isEmpty()) {
                // 텍스트만 있는 경우
                String itemText = item.text().trim();
                if (!itemText.isEmpty()) {
                    HtmlTextSegment textSegment = new HtmlTextSegment(itemText);
                    applyParentStyles(item, textSegment);
                    segments.add(textSegment);
                }
            } else {
                // 요소가 있는 경우 각 요소 처리
                for (Element child : item.children()) {
                    if (child.tagName().equalsIgnoreCase("ul") || child.tagName().equalsIgnoreCase("ol")) {
                        // 중첩된 리스트는 별도 처리
                        processNestedList(child, segments, 1);
                    } else {
                        processElement(child, segments);
                    }
                }
            }

            // 리스트 항목 종료
            HtmlTextSegment itemEndSegment = new HtmlTextSegment("");
            itemEndSegment.setListItemEnd(true);
            segments.add(itemEndSegment);
        }

        // 리스트 종료 세그먼트
        HtmlTextSegment endSegment = new HtmlTextSegment("");
        endSegment.setListEnd(true);
        segments.add(endSegment);
    }

    /**
     * 중첩된 리스트를 처리합니다.
     */
    private void processNestedList(Element listElement, List<HtmlTextSegment> segments, int level) {
        boolean isOrdered = listElement.tagName().equalsIgnoreCase("ol");

        // 리스트 시작 세그먼트
        HtmlTextSegment startSegment = new HtmlTextSegment("");
        startSegment.setListStart(true);
        startSegment.setOrderedList(isOrdered);
        startSegment.setListLevel(level);
        segments.add(startSegment);

        // 리스트 항목 처리
        Elements items = listElement.select("> li");
        int itemNumber = 1;

        for (Element item : items) {
            // 리스트 항목 시작
            HtmlTextSegment itemStartSegment = new HtmlTextSegment("");
            itemStartSegment.setListItemStart(true);
            itemStartSegment.setListItemNumber(itemNumber++);
            itemStartSegment.setOrderedList(isOrdered);
            itemStartSegment.setListLevel(level);
            segments.add(itemStartSegment);

            // 항목 내용 처리
            if (item.children().isEmpty()) {
                // 텍스트만 있는 경우
                String itemText = item.text().trim();
                if (!itemText.isEmpty()) {
                    HtmlTextSegment textSegment = new HtmlTextSegment(itemText);
                    applyParentStyles(item, textSegment);
                    segments.add(textSegment);
                }
            } else {
                // 요소가 있는 경우 각 요소 처리
                for (Element child : item.children()) {
                    if (child.tagName().equalsIgnoreCase("ul") || child.tagName().equalsIgnoreCase("ol")) {
                        // 더 중첩된 리스트 처리
                        processNestedList(child, segments, level + 1);
                    } else {
                        processElement(child, segments);
                    }
                }
            }

            // 리스트 항목 종료
            HtmlTextSegment itemEndSegment = new HtmlTextSegment("");
            itemEndSegment.setListItemEnd(true);
            segments.add(itemEndSegment);
        }

        // 리스트 종료 세그먼트
        HtmlTextSegment endSegment = new HtmlTextSegment("");
        endSegment.setListEnd(true);
        segments.add(endSegment);
    }

    /**
     * 단락에 정렬 설정을 적용합니다.
     */
    private void setAlignment(XWPFParagraph paragraph, String alignment) {
        if (alignment == null || alignment.isEmpty()) {
            log.warn("정렬 값이 null 또는 빈 문자열입니다.");
            return;
        }


        try {
            ParagraphAlignment alignmentEnum = null;

            switch (alignment.toLowerCase().trim()) {
                case "center":
                case "ck-text-center":
                    alignmentEnum = ParagraphAlignment.CENTER;
                    break;
                case "right":
                case "ck-text-right":
                    alignmentEnum = ParagraphAlignment.RIGHT;
                    break;
                case "justify":
                case "both":
                case "ck-text-justify":
                    alignmentEnum = ParagraphAlignment.BOTH;
                    break;
                case "left":
                case "ck-text-left":
                default:
                    alignmentEnum = ParagraphAlignment.LEFT;
                    break;
            }

            // 정렬 적용
            paragraph.setAlignment(alignmentEnum);

            // 단락 간격 설정 (정렬이 제대로 보이도록)
            paragraph.setSpacingAfter(0);
            paragraph.setSpacingBefore(0);


            // 저수준 API로 강제 적용
            CTPPr pPr = paragraph.getCTP().getPPr();
            if (pPr == null) {
                pPr = paragraph.getCTP().addNewPPr();
            }

            CTJc jc = pPr.isSetJc() ? pPr.getJc() : pPr.addNewJc();

            switch (alignment.toLowerCase().trim()) {
                case "center":
                    jc.setVal(STJc.CENTER);
                    break;
                case "right":
                    jc.setVal(STJc.RIGHT);
                    break;
                case "justify":
                case "both":
                    jc.setVal(STJc.BOTH);
                    break;
                case "left":
                default:
                    jc.setVal(STJc.LEFT);
                    break;
            }


        } catch (Exception e) {
            log.error("정렬 설정 중 오류 발생: {}", e.getMessage(), e);
        }
    }


    private String extractAlignment(Element element) {
        if (element == null) {
            return null;
        }

        // 1. style 속성에서 text-align 직접 확인 (최우선)
        String style = element.attr("style");
        if (style != null && !style.isEmpty()) {
            String textAlign = extractStyleValue(style, "text-align");
            if (textAlign != null && !textAlign.isEmpty()) {

                return textAlign.toLowerCase().trim(); // ← 이 부분이 실행되도록 수정
            }
        }

        // 2. CKEditor 클래스 확인
        String className = element.className().toLowerCase();
        if (className.contains("ck-align-left") || className.contains("text-left")) {
            return "left";
        } else if (className.contains("ck-align-center") || className.contains("text-center")) {
            return "center";
        } else if (className.contains("ck-align-right") || className.contains("text-right")) {
            return "right";
        } else if (className.contains("ck-align-justify") || className.contains("text-justify")) {
            return "justify";
        }

        // 3. align 속성 확인 (레거시 HTML)
        String alignAttr = element.attr("align");
        if (alignAttr != null && !alignAttr.isEmpty()) {
            return alignAttr.toLowerCase().trim();
        }

        return null;
    }



    /**
     * 요소에서 수직 정렬 정보를 추출합니다.
     */
    private String extractVerticalAlignment(Element element) {
        if (element == null) {
            return null;
        }

        // 1. valign 속성 확인 (HTML 표준)
        String valignAttr = element.attr("valign");
        if (valignAttr != null && !valignAttr.isEmpty()) {
            return normalizeVerticalAlignment(valignAttr);
        }

        // 2. style 속성에서 vertical-align 확인
        String style = element.attr("style");
        if (style != null && !style.isEmpty()) {
            String verticalAlign = extractStyleValue(style, "vertical-align");
            if (verticalAlign != null && !verticalAlign.isEmpty()) {
                return normalizeVerticalAlignment(verticalAlign);
            }
        }

        // 3. 클래스명에서 수직 정렬 정보 확인
        String className = element.className().toLowerCase();
        if (className.contains("valign-top") || className.contains("v-align-top")) {
            return "top";
        } else if (className.contains("valign-middle") || className.contains("v-align-middle") ||
                className.contains("valign-center") || className.contains("v-align-center")) {
            return "center";
        } else if (className.contains("valign-bottom") || className.contains("v-align-bottom")) {
            return "bottom";
        }

        // 4. 부모 요소에서 상속된 수직 정렬 확인
        Element parent = element.parent();
        if (parent != null && !parent.tagName().equals("body") && !parent.tagName().equals("html")) {
            return extractVerticalAlignment(parent);
        }

        return null;
    }

    private String normalizeVerticalAlignment(String align) {
        if (align == null) return null;

        align = align.toLowerCase().trim();
        switch (align) {
            case "top":
            case "text-top":
                return "top";
            case "middle":
            case "center":
            case "baseline":
                return "center";
            case "bottom":
            case "text-bottom":
                return "bottom";
            default:
                return align;
        }
    }
    private void applyCellVerticalAlignment(XWPFTableCell tableCell, String verticalAlign) {
        try {
            // CKEditor 수직 정렬 값 정규화
            String normalizedAlign = normalizeVerticalAlignment(verticalAlign);

            switch (normalizedAlign.toLowerCase().trim()) {
                case "top":
                    tableCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.TOP);
                    break;
                case "center":
                case "middle":
                    tableCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                    break;
                case "bottom":
                    tableCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.BOTTOM);
                    break;
                default:
                    tableCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.TOP);
            }

            // 저수준 API 추가 설정
            CTTcPr tcPr = tableCell.getCTTc().getTcPr();
            if (tcPr == null) {
                tcPr = tableCell.getCTTc().addNewTcPr();
            }

            CTVerticalJc vAlign = tcPr.isSetVAlign() ? tcPr.getVAlign() : tcPr.addNewVAlign();

            switch (normalizedAlign.toLowerCase().trim()) {
                case "top":
                    vAlign.setVal(STVerticalJc.TOP);
                    break;
                case "center":
                case "middle":
                    vAlign.setVal(STVerticalJc.CENTER);
                    break;
                case "bottom":
                    vAlign.setVal(STVerticalJc.BOTTOM);
                    break;
                default:
                    vAlign.setVal(STVerticalJc.TOP);
            }

        } catch (Exception e) {
            log.error("CKEditor 테이블 셀 수직 정렬 설정 중 오류: {}", e.getMessage(), e);
        }
    }
    private void processParagraphContent(XWPFParagraph paragraph, Element pElement) {
        // P 태그 내부의 HTML 콘텐츠만 추출
        String pContent = pElement.html();

        // HTML 콘텐츠 분석 및 처리
        List<HtmlTextSegment> processedSegments = analyzeHtmlTags(pContent);

        for (HtmlTextSegment segment : processedSegments) {
            if (segment.getText() == null || segment.getText().isEmpty()) {
                continue;
            }

            if (segment.isListStart() || segment.isListEnd() ||
                    segment.isListItemStart() || segment.isListItemEnd() ||
                    segment.isTableStart() || segment.isTableEnd() ||
                    segment.isTableRowStart() || segment.isTableRowEnd() ||
                    segment.isTableCellStart() || segment.isTableCellEnd()) {
                continue;
            }

            XWPFRun run = paragraph.createRun();
            run.setText(segment.getText());
            applyTextStyles(run, segment);
        }
    }
    private void processSimpleHtmlContent(XWPFParagraph paragraph, String htmlValue) {
        Document doc = Jsoup.parse(htmlValue);

        // 전체 HTML에서 정렬 확인
        String detectedAlignment = null;

        // body나 최상위 요소에서 정렬 확인
        Element body = doc.body();
        if (body != null) {
            detectedAlignment = extractAlignment(body);
            if (detectedAlignment != null) {
            }
        }

        // 직접 스타일 속성이 있는 모든 요소 확인
        if (detectedAlignment == null) {
            Elements styledElements = doc.select("[style*=text-align]");
            if (!styledElements.isEmpty()) {
                detectedAlignment = extractAlignment(styledElements.first());
                if (detectedAlignment != null) {
                }
            }
        }

        // 정렬 적용
        if (detectedAlignment != null && !detectedAlignment.isEmpty()) {
            setAlignment(paragraph, detectedAlignment);
        }

        // 기존 콘텐츠 처리 로직
        List<HtmlTextSegment> processedSegments = analyzeHtmlTags(htmlValue);

        for (HtmlTextSegment segment : processedSegments) {
            if (segment.getText() == null || segment.getText().isEmpty()) {
                continue;
            }

            if (segment.isListStart() || segment.isListEnd() ||
                    segment.isListItemStart() || segment.isListItemEnd() ||
                    segment.isTableStart() || segment.isTableEnd() ||
                    segment.isTableRowStart() || segment.isTableRowEnd() ||
                    segment.isTableCellStart() || segment.isTableCellEnd()) {
                continue;
            }

            XWPFRun run = paragraph.createRun();
            run.setText(segment.getText());
            applyTextStyles(run, segment);
        }
    }


    private void setListLevel(XWPFParagraph paragraph, BigInteger numId, int level) {
        try {
            CTPPr pPr = paragraph.getCTP().getPPr();
            if (pPr == null) {
                pPr = paragraph.getCTP().addNewPPr();
            }

            CTNumPr numPr = pPr.getNumPr();
            if (numPr == null) {
                numPr = pPr.addNewNumPr();
            }

            // 번호 매기기 ID 설정
            CTDecimalNumber numIdNode = numPr.getNumId();
            if (numIdNode == null) {
                numIdNode = numPr.addNewNumId();
            }
            numIdNode.setVal(numId);

            // 레벨 설정
            CTDecimalNumber ilvl = numPr.getIlvl();
            if (ilvl == null) {
                ilvl = numPr.addNewIlvl();
            }
            ilvl.setVal(BigInteger.valueOf(level));

            log.debug("리스트 레벨 {} 설정 완료: numId={}", level, numId);

        } catch (Exception e) {
            log.error("리스트 레벨 설정 중 오류: {}", e.getMessage(), e);
        }
    }




    private void applySpanStyles(XWPFRun run, Element span) {
        String style = span.attr("style");
        if (style != null && !style.isEmpty()) {
            // 폰트 크기 적용
            String fontSize = extractStyleValue(style, "font-size");
            if (fontSize != null) {
                double size = parseFontSize(fontSize);
                if (size > 0) {
                    run.setFontSize(size);
                }
            }

            // 폰트 색상 적용
            String color = extractStyleValue(style, "color");
            if (color != null) {
                String hexColor = convertToHexColor(color);
                if (hexColor != null) {
                    run.setColor(hexColor);
                }
            }
        }
    }
    /**
     * Word 문서에 번호 매기기를 생성합니다.
     */
    private BigInteger createWordNumbering(XWPFDocument doc, boolean isOrdered) {
        try {
            XWPFNumbering numbering = doc.createNumbering();
            if (numbering == null) {
                numbering = doc.getNumbering();
            }

            CTAbstractNum cTAbstractNum = CTAbstractNum.Factory.newInstance();
            long abstractNumId = System.currentTimeMillis() % 100000;
            cTAbstractNum.setAbstractNumId(BigInteger.valueOf(abstractNumId));

            // ★ 핵심: 여러 레벨의 들여쓰기를 더 크게 설정
            for (int level = 0; level < 9; level++) {
                CTLvl cTLvl = cTAbstractNum.addNewLvl();
                cTLvl.setIlvl(BigInteger.valueOf(level));

                if (isOrdered) {
                    cTLvl.addNewNumFmt().setVal(STNumberFormat.DECIMAL);
                    cTLvl.addNewLvlText().setVal("%" + (level + 1) + ".");
                    cTLvl.addNewStart().setVal(BigInteger.valueOf(1));
                } else {
                    cTLvl.addNewNumFmt().setVal(STNumberFormat.BULLET);
                    cTLvl.addNewLvlText().setVal("•");
                }

                // ★ 핵심: 들여쓰기 값을 더 크게 설정 (검색 결과[3] 참조)
                try {
                    CTPPrGeneral pPr = cTLvl.addNewPPr();
                    CTInd ind = pPr.addNewInd();

                    // 레벨별 들여쓰기를 더 크게 설정
                    // 레벨 0: 1440 twips (1.0 inch)
                    // 레벨 1: 2160 twips (1.5 inch)
                    // 레벨 2: 2880 twips (2.0 inch)
                    int leftIndent = 1440 + (720 * level); // 기본 1인치 + 레벨당 0.5인치
                    int hangingIndent = 720; // 0.5 inch로 증가

                    ind.setLeft(BigInteger.valueOf(leftIndent));
                    ind.setHanging(BigInteger.valueOf(hangingIndent));


                } catch (Exception indentError) {
                    log.error("번호 매기기 레벨 {} 들여쓰기 설정 중 오류: {}", level, indentError.getMessage());
                }
            }

            XWPFAbstractNum abstractNum = new XWPFAbstractNum(cTAbstractNum);
            BigInteger abstractNumID = numbering.addAbstractNum(abstractNum);
            BigInteger numID = numbering.addNum(abstractNumID);

            return numID;

        } catch (Exception e) {
            log.error("Word 번호 매기기 생성 중 오류: {}", e.getMessage(), e);
            return BigInteger.valueOf(1);
        }
    }


    /**
     * 리스트 항목에서 직접 텍스트만 추출합니다 (중첩 리스트 제외).
     */
    private String getDirectTextFromListItem(Element item) {
        StringBuilder directText = new StringBuilder();

        for (Node child : item.childNodes()) {
            if (child instanceof TextNode) {
                String text = ((TextNode) child).text().trim();
                if (!text.isEmpty()) {
                    directText.append(text);
                }
            } else if (child instanceof Element) {
                Element childElement = (Element) child;
                //  ul, ol이 아닌 요소만 텍스트로 처리
                if (!childElement.tagName().matches("ul|ol")) {
                    // span, strong 등의 인라인 요소는 텍스트로 포함
                    if (childElement.tagName().matches("span|strong|em|b|i")) {
                        directText.append(childElement.text());
                    }
                }
            }
        }

        String result = directText.toString().trim();

        // 직접 텍스트가 없으면 빈 문자열 반환 (중첩 리스트만 있는 경우)
        if (result.isEmpty()) {
            log.debug("리스트 항목에 직접 텍스트가 없음 - 중첩 리스트만 존재");
        }

        return result;
    }



}

