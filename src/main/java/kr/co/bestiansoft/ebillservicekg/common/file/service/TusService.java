package kr.co.bestiansoft.ebillservicekg.common.file.service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface TusService {
	void uploadTus(HttpServletRequest request, HttpServletResponse response);
}
