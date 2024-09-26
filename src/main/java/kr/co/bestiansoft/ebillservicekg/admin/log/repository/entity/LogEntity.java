package kr.co.bestiansoft.ebillservicekg.admin.log.repository.entity;

import kr.co.bestiansoft.ebillservicekg.admin.log.domain.LogCreate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;


@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "com_acs_hist")
@Entity
public class LogEntity {

    @Column(name = "hist_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long histId;
    private String userId;
    @Column(name = "acs_ip")
    private String accessIp;
    private String reqUrl;
    private String reqMethod;

    @Column(name = "reg_dt")
    @CreationTimestamp
    private LocalDateTime regDt;

    @Builder
	public LogEntity(String userId, String accessIp, String reqUrl, String reqMethod, LocalDateTime regDt) {
		this.userId = userId;
		this.accessIp = accessIp;
		this.reqUrl = reqUrl;
		this.reqMethod = reqMethod;
		this.regDt = regDt;
	}

	public static LogEntity from(LogCreate create) {
		return LogEntity.builder().userId(create.getUserId()).accessIp(create.getAccessIp()).reqUrl(create.getReqUrl()).reqMethod(create.getReqMethod()).build();
	}

}
