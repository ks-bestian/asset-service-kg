package kr.co.bestiansoft.ebillservicekg.eas.draftDocument.vo;


import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@Data
public class HtmlTextSegment {
    private String text;
    private String fontFamily;
    private double fontSize;
    private boolean bold;
    private boolean italic;
    private boolean strike;
    private String textColor;
    private boolean breakAfter;

    private String alignment;

    // 리스트 관련 필드 추가
    private boolean listStart;
    private boolean listEnd;
    private boolean listItemStart;
    private boolean listItemEnd;
    private int listLevel;
    private int listItemNumber;
    private boolean orderedList;

    // 테이블 관련 필드 추가
    private boolean tableStart;
    private boolean tableEnd;
    private boolean tableRowStart;
    private boolean tableRowEnd;
    private boolean tableCellStart;
    private boolean tableCellEnd;
    private int tableRows;
    private int tableCols;
    private double tableWidth;
    private double tableBorderSize;
    private String tableStyle;
    private String tableRowStyle;
    private double tableRowHeight;
    private int tableRowIndex;
    private int tableColIndex;
    private int tableRowSpan = 1;
    private int tableColSpan = 1;
    private String tableCellStyle;
    private String tableCellBgColor;
    private String tableCellVerticalAlign;


    public HtmlTextSegment(String text) {
        this.text = text;
    }

}