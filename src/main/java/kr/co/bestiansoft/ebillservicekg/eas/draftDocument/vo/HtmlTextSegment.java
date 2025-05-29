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

    public HtmlTextSegment(String text) {
        this.text = text;
    }

}