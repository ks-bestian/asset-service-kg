package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private String fileName;
    private String fileSignature;
    private String fileLink;
    private String created;
}
