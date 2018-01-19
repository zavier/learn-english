package le.zavier.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class User implements Serializable{
    private static final Long serialVersionUID = 1L;

    private Long id;

    private String account;

    private String email;

    private String nickname;

    @NotEmpty
    private String password;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public User(Long id, String account, String email, String nickname, String password,
        Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.account = account;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}