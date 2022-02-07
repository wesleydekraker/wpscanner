package tools.wesley.wpscanner.domain;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
public class Scan {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 32)
    private String guid;
    @Column(nullable = false, length = 50)
    private String userIp;
    @Column(nullable = false, length = 250)
    private String url;
    private Boolean validUrl;
    private Boolean isWP;
    @Column(length = 50)
    private String ipAddress;
    @Type(type = "tools.wesley.wpscanner.repositories.VersionStringType")
    private Version wordPressVersion;
    @Column(length = 250)
    private String installationDirectory;
    private Boolean bruteForceProtection;
    private Boolean readmeAccessible;
    @Column(nullable = false)
    private Date startTime;
    private Date endTime;
    @OneToMany(mappedBy = "scan", cascade = CascadeType.ALL)
    private Set<InstalledTheme> installedThemes = new HashSet<>();
    @OneToMany(mappedBy = "scan", cascade = CascadeType.ALL)
    private Set<InstalledPlugin> installedPlugins = new HashSet<>();

    /**
     * Constructor is required for Hibernate.
     */
    protected Scan() {
    }

    public Scan(String guid, String userIp, String url) {
        this.guid = guid;
        this.userIp = userIp;
        this.url = url;
        this.startTime = new Date(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserIp() {
        return userIp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getValidUrl() {
        return validUrl;
    }

    public void setValidUrl(Boolean validUrl) {
        this.validUrl = validUrl;
    }

    public Boolean getWP() {
        return isWP;
    }

    public void setWP(Boolean WP) {
        isWP = WP;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setWordPressVersion(Version wordPressVersion) {
        this.wordPressVersion = wordPressVersion;
    }

    public String getInstallationDirectory() {
        return installationDirectory;
    }

    public void setInstallationDirectory(String installationDirectory) {
        this.installationDirectory = installationDirectory;
    }

    public Boolean getBruteForceProtection() {
        return bruteForceProtection;
    }

    public void setBruteForceProtection(Boolean bruteForceProtection) {
        this.bruteForceProtection = bruteForceProtection;
    }

    public Boolean getReadmeAccessible() {
        return readmeAccessible;
    }

    public void setReadmeAccessible(Boolean readmeAccessible) {
        this.readmeAccessible = readmeAccessible;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime() {
        this.endTime = new Date(System.currentTimeMillis());
    }

    public Set<InstalledTheme> getInstalledThemes() {
        return installedThemes;
    }

    public void addInstalledTheme(InstalledTheme installedTheme) {
        installedThemes.add(installedTheme);
        installedTheme.setScan(this);
    }

    public Set<InstalledPlugin> getInstalledPlugins() {
        return installedPlugins;
    }

    public void addInstalledPlugin(InstalledPlugin installedPlugin) {
        installedPlugins.add(installedPlugin);
        installedPlugin.setScan(this);
    }
}
