package tools.wesley.wpscanner.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Theme {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String directoryName;
    @Column(length = 100)
    private String displayName;
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private Set<ThemeVulnerability> themeVulnerabilities = new HashSet<>();

    /**
     * Constructor is required for Hibernate.
     */
    protected Theme() {

    }

    public Theme(String directoryName) {
        this.directoryName = directoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirectoryName() {
        return directoryName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<ThemeVulnerability> getThemeVulnerabilities() {
        return themeVulnerabilities;
    }

    public void addThemeVulnerability(ThemeVulnerability themeVulnerability) {
        themeVulnerabilities.add(themeVulnerability);
        themeVulnerability.setTheme(this);
    }

    public List<ThemeVulnerability> getVulnerabilities(Version version) {
        var vulnerabilities = new ArrayList<ThemeVulnerability>();
        var versionAsLong = version.toLong();

        for (var vulnerability : themeVulnerabilities) {
            var firstVersion = vulnerability.getFirstVersion().toLong();
            var lastVersion = vulnerability.getLastVersion().toLong();

            if (versionAsLong >= firstVersion && versionAsLong <= lastVersion)
                vulnerabilities.add(vulnerability);
        }

        return vulnerabilities;
    }
}
