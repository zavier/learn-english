package le.zavier.pojo;

import java.io.Serializable;
import java.util.Date;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

@ToString
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

    public User(Long id, String account, String email, String nickname, String password, Integer status, Date createTime, Date updateTime) {
        this.id = id;
        this.account = account;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public User() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}