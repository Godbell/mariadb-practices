package bookmall.vo;

public class UserVo {
    private Long no;
    private String name;
    private String email;
    private String password;
    private String tel;

    public UserVo(Long no, String name, String email, String password, String tel) {
        this.no = no;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public UserVo(String name, String email, String password, String tel) {
        this.no = null;
        this.name = name;
        this.email = email;
        this.password = password;
        this.tel = tel;
    }

    public Long getNo() {
        return no;
    }

    public void setNo(Long no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }
}
