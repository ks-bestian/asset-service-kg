package kr.co.bestiansoft.ebillservicekg.sed_jk.client.gateway;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

public enum GatewayApi {
    V1("/integra-gateway-docs/api/v1", HttpMethod.GET),
    LIST_OTHER_ORGANIZATIONS(V1.address + "/edm-systems/list-others", HttpMethod.GET),
    LIST_ALL_ORGANIZATIONS(V1.address + "/edm-systems/list", HttpMethod.GET),
    LIST_MY_ORGANIZATIONS(V1.address + "/organizations/list", HttpMethod.GET),
    ADD_ORGANIZATION(V1.address + "/organizations/add", HttpMethod.POST),
    EDIT_ORGANIZATION(V1.address + "/organizations/edit", HttpMethod.POST),
    ENABLE_ORGANIZATION(V1.address + "/organizations/enable", HttpMethod.POST),
    DISABLE_ORGANIZATION(V1.address + "/organizations/disable", HttpMethod.POST),
    SEARCH_ORGANIZATION(V1.address + "/organizations/search", HttpMethod.GET),
    NEW_DOCUMENTS(V1.address + "/documents/list/incoming/new", HttpMethod.GET),
    ADD_DOCUMENT(V1.address + "/documents/add", HttpMethod.POST),
    ACCEPT_DOCUMENT(V1.address + "/documents/accept/{DOC_UUID}", HttpMethod.POST),
    DOWNLOAD_DOCUMENT(V1.address + "/documents/download/{DOC_UUID}", HttpMethod.POST),
    GET_ALL_INCOMING_DOCUMENTS(V1.address + "/documents/list/incoming", HttpMethod.GET),
    GET_OUTGOING_DOCUMENTS(V1.address + "/documents/list/outgoing", HttpMethod.GET),
    GET_ALL_DOCUMENTS(V1.address + "/documents/list/all", HttpMethod.GET),
    SEARCH_BY_DOC_NUMBER(V1.address + "/documents/search/doc-number", HttpMethod.GET),
    ;

    private static final Map<String, GatewayApi> BY_ADDRESS = new HashMap<>();
    private static final Map<HttpMethod, GatewayApi> BY_HTTP_METHOD = new HashMap<>();

    static {
        for (GatewayApi e : values()) {
            BY_ADDRESS.put(e.address, e);
            BY_HTTP_METHOD.put(e.httpMethod, e);
        }
    }

    public final String address;
    public final HttpMethod httpMethod;

    GatewayApi(String label, HttpMethod httpMethod) {
        this.address = label;
        this.httpMethod = httpMethod;
    }

    public String getAddress() {
        return address;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }
}
