package club.majoy.springbootmajoycache.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private Integer status;

    public User() {
    }

    public User(String username, String password, Integer status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                '}';
    }
}
