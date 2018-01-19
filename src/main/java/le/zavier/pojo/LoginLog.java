package le.zavier.pojo;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginLog {
    private Long id;

    private Long userId;

    private String loginIp;

    private Date loginTime;

    private Date createTime;

    private Date updateTime;

    public LoginLog(Long userId, String loginIp, Date loginTime) {
        this.userId = userId;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
    }
}