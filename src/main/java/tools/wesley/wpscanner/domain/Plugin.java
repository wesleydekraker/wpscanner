package tools.wesley.wpscanner.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Plugin {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String directoryName;
    @Column(length = 100)
    private String displayName;
    @OneToMany(mappedBy = "plugin", cascade = CascadeType.ALL)
    private Set<PluginVulnerability> pluginVulnerabilities = new HashSet<>();

    /**
     * Constructor is required for Hibernate.
     */
    protected Plugin() {

    }

    public Plugin(String directoryName) {
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

    public Set<PluginVulnerability> getPluginVulnerabilities() {
        return pluginVulnerabilities;
    }

    public void addPluginVulnerability(PluginVulnerability pluginVulnerability) {
        pluginVulnerabilities.add(pluginVulnerability);
        pluginVulnerability.setPlugin(this);
    }

    public List<PluginVulnerability> getVulnerabilities(Version version) {
        var vulnerabilities = new ArrayList<PluginVulnerability>();
        var versionAsLong = version.toLong();

        for (var vulnerability : pluginVulnerabilities) {
            var firstVersion = vulnerability.getFirstVersion().toLong();
            var lastVersion = vulnerability.getLastVersion().toLong();
            if (versionAsLong >= firstVersion && versionAsLong <= lastVersion)
                vulnerabilities.add(vulnerability);
        }

        return vulnerabilities;
    }
}
