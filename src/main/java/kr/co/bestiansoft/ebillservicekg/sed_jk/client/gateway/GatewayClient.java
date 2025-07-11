package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway;

import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.common.DownloadedFile;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.request.AddDocumentRequest;
import kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway.dto.document.response.AddDocumentResponse;
import kr.co.bestiansoft.ebillservicekg.sed_jk.exception.ClientServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class GatewayClient {

    private final RestClient restClient;

    @Value("${app.sed.url}")
    private String sedUrl;
    @Value("${app.sed.access-key}")
    private String accessKey;
    @Value("${app.sed.secret-key}")
    private String secretKey;


    public <T, R> R sendRequest(GatewayApi api, T request, Class<R> responseType, Map<String, String> filters, Consumer<HttpHeaders> additionalHeaders) {

        Consumer<HttpHeaders> httpHeaders = headers -> {
            headers.add("x-access-key", accessKey);
            headers.add("x-secret-key", secretKey);

            if (additionalHeaders != null) {
                additionalHeaders.accept(headers); // Применяем дополнительные заголовки
            }
        };

        String queryParams = filters.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        URI uri = URI.create(sedUrl + api.address + (queryParams.isEmpty() ? "" : "?" + queryParams));

        log.info("Sed API"+uri);

        return switch (api.httpMethod.name()) {
            case "GET" -> sendGetRequest(uri, responseType, httpHeaders);
            case "POST" -> sendPostRequest(uri, request, responseType, httpHeaders);
            default -> throw new UnsupportedOperationException("HTTP method not supported: " + api.httpMethod.name());
        };
    }

    public <T, R> R sendRequest(GatewayApi api, T request, Class<R> responseType, Map<String, String> filters) {

        Consumer<HttpHeaders> httpHeaders = headers -> {
            headers.add("x-access-key", accessKey);
            headers.add("x-secret-key", secretKey);
            headers.add(HttpHeaders.CONTENT_TYPE, "application/json;charset=utf-8");
        };

        String queryParams = filters.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        URI uri = URI.create(sedUrl + api.address + (queryParams.isEmpty() ? "" : "?" + queryParams));

        return switch (api.httpMethod.name()) {
            case "GET" -> sendGetRequest(uri, responseType, httpHeaders);
            case "POST" -> sendPostRequest(uri, request, responseType, httpHeaders);
            default -> throw new UnsupportedOperationException("HTTP method not supported: " + api.httpMethod.name());
        };
    }

    public <T, R> R sendRequest(
            GatewayApi api,
            T request,
            Class<R> responseType,
            Map<String, String> filters,
            List<String> pathVariables
    ) {

        Consumer<HttpHeaders> httpHeaders = headers -> {
            headers.add("x-access-key", accessKey);
            headers.add("x-secret-key", secretKey);

        };

        String queryParams = filters.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        StringBuilder path = new StringBuilder(sedUrl + api.address);
        for (String pathVariable : pathVariables) {
            path.append("/").append(pathVariable);
        }

        URI uri = URI.create(path + queryParams);

        return switch (api.httpMethod.name()) {
            case "GET" -> sendGetRequest(uri, responseType, httpHeaders);
            case "POST" -> sendPostRequest(uri, request, responseType, httpHeaders);
            default -> throw new UnsupportedOperationException("HTTP method not supported: " + api.httpMethod.name());
        };
    }

    private <R> R sendGetRequest(URI uri, Class<R> responseType, Consumer<HttpHeaders> headers) {
        return restClient.get()
                .uri(uri)
                .headers(headers)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, response) -> {
                    throw new ClientServerException("GET request failed: " + req);
                })
                .body(responseType);
    }

    private <T, R> R sendPostRequest(URI uri, T request, Class<R> responseType, Consumer<HttpHeaders> headers) {
        Object safeRequest = request != null ? request : Collections.emptyMap();

        return restClient.post()
                .uri(uri)
                .body(safeRequest)
                .headers(headers)
                .retrieve().onStatus(HttpStatusCode::is4xxClientError, (req, response) -> {
                    throw new ClientServerException("POST request failed: " + req + response.getStatusCode());
                })
                .body(responseType);
    }

    public <T, R> R sendRequest(
            GatewayApi api,
            T request,
            Class<R> responseType,
            Map<String, String> queryParams,
            Map<String, String> pathVariables
    ) {
        Consumer<HttpHeaders> httpHeaders = headers -> {
            headers.add("x-access-key", accessKey);
            headers.add("x-secret-key", secretKey);
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8");
        };

        String processedAddress = api.address;
        if (pathVariables != null && !pathVariables.isEmpty()) {
            for (Map.Entry<String, String> entry : pathVariables.entrySet()) {
                // Заменяем {ПЛЕЙСХОЛДЕР} на значение
                processedAddress = processedAddress.replace("{" + entry.getKey() + "}", entry.getValue());
            }
        }

        String queryString = queryParams.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        URI uri = URI.create(sedUrl + processedAddress + (queryString.isEmpty() ? "" : "?" + queryString));

        return switch (api.httpMethod.name()) {
            case "GET" -> sendGetRequest(uri, responseType, httpHeaders);
            case "POST" -> sendPostRequest(uri, request, responseType, httpHeaders);
            default -> throw new UnsupportedOperationException("HTTP method not supported: " + api.httpMethod.name());
        };
    }

    public AddDocumentResponse sendDocumentToGateway(AddDocumentRequest request) {

        return sendRequest(
                GatewayApi.ADD_DOCUMENT,
                request,
                AddDocumentResponse.class,
                Collections.emptyMap()
        );
    }

    public <R> R acceptDocument(UUID docUuid, Class<R> responseType) {
        Map<String, String> pathVariables = Collections.singletonMap("DOC_UUID", docUuid.toString());

        return sendRequest(
                GatewayApi.ACCEPT_DOCUMENT,
                null, // Тело запроса не требуется
                responseType, // Тип ответа
                Collections.emptyMap(), // Нет query-параметров
                pathVariables // Передаем path-переменные
        );
    }

    public DownloadedFile downloadFile(String fileUrl) {
        try {
            URI uri = URI.create(fileUrl);
            log.info("Attempting to download file from URL: {}", uri);

            ResponseEntity<byte[]> response = restClient.get()
                    .uri(uri)
                    .headers(headers -> {
                        headers.add("x-access-key", accessKey);
                        headers.add("x-secret-key", secretKey);
                    })
                    .retrieve()
                    .onStatus(HttpStatusCode::isError, (req, res) -> {
                        String errorMessage = String.format("File download failed from %s. Status: %s.",
                                uri, res.getStatusCode());
                        log.error(errorMessage);
                        throw new ClientServerException(errorMessage);
                    })
                    .toEntity(byte[].class);

            byte[] fileContent = response.getBody();
            if (fileContent == null) {
                log.warn("Downloaded file content is null from URL: {}", fileUrl);
                throw new ClientServerException("Downloaded file content is null.");
            }

            HttpHeaders headers = response.getHeaders();
            String fileName = extractFileNameFromContentDisposition(headers.getFirst(HttpHeaders.CONTENT_DISPOSITION));
            String mimeType = headers.getFirst(HttpHeaders.CONTENT_TYPE);

            if (fileName == null || fileName.isEmpty()) {
                log.warn("Could not determine filename from Content-Disposition for URL: {}. Attempting to extract from URL path.", fileUrl);
                // Попытка извлечь имя файла из URL, если Content-Disposition отсутствует
                fileName = uri.getPath();
                int lastSlash = fileName.lastIndexOf('/');
                if (lastSlash != -1 && lastSlash < fileName.length() - 1) {
                    fileName = fileName.substring(lastSlash + 1);
                } else {
                    fileName = "unknown_file"; // Fallback
                }
            }
            log.info("Successfully downloaded file '{}' ({} bytes) from URL: {}", fileName, fileContent.length, fileUrl);

            return new DownloadedFile(fileName, fileContent, mimeType);

        } catch (IllegalArgumentException e) {
            log.error("Invalid file URL: {}", fileUrl, e);
            throw new ClientServerException("Invalid file URL provided: " + fileUrl);
        } catch (Exception e) {
            log.error("Error downloading file from {}: {}", fileUrl, e.getMessage(), e);
            throw new ClientServerException("Error downloading file: " + e.getMessage());
        }
    }

    private String extractFileNameFromContentDisposition(String contentDisposition) {
        if (contentDisposition == null || contentDisposition.isEmpty()) {
            return null;
        }

        Pattern filenamePattern = Pattern.compile("filename\\*?=(?:UTF-8'')?\"?([^\"]+)\"?");
        Matcher matcher = filenamePattern.matcher(contentDisposition);
        if (matcher.find()) {
            try {
                // Декодируем, если имя файла закодировано (например, UTF-8''%D1%84%D0%B0%D0%B9%D0%BB.pdf)
                String encodedFilename = matcher.group(1);
                if (encodedFilename.startsWith("UTF-8''")) {
                    return java.net.URLDecoder.decode(encodedFilename.substring(7), StandardCharsets.UTF_8);
                }
                return encodedFilename;
            } catch (Exception e) {
                log.warn("Failed to decode filename from Content-Disposition: {}", contentDisposition, e);
                return matcher.group(1); // Возвращаем как есть
            }
        }
        return null;
    }

}