package tools.wesley.wpscanner.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class InstalledTheme {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Scan scan;
    @ManyToOne(fetch = FetchType.EAGER)
    private Theme theme;
    @Type(type = "tools.wesley.wpscanner.repositories.VersionStringType")
    @Column(nullable = false)
    private Version version;
    @Column(nullable = false)
    private boolean active;

    /**
     * Constructor is required for Hibernate.
     */
    public InstalledTheme() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Scan getScan() {
        return scan;
    }

    public void setScan(Scan scan) {
        this.scan = scan;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
