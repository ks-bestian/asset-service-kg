package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DownloadedFile {
    private String fileName;
    private byte[] content;
    private String mimeType;
}
