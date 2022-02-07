package tools.wesley.wpscanner.domain;

import javax.persistence.*;

@Entity
public class Administrator {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 50)
    private String username;
    @Column(nullable = false, length = 200)
    private String password;

    /**
     * Constructor is required for Hibernate.
     */
    protected Administrator() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
