package tools.wesley.wpscanner.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tools.wesley.wpscanner.repositories.*;
import tools.wesley.wpscanner.dto.*;
import tools.wesley.wpscanner.domain.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class AdminController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private PluginRepository pluginRepository;
    @Autowired
    private ScanRepository scanRepository;

    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    @PostMapping("/api/login")
    public void login(@RequestBody LoginDto loginDto) {
        loginService.login(loginDto.username(), loginDto.password());
    }

    @PostMapping("/api/pluginVulnerability")
    public void addPluginVulnerability(@RequestBody VulnerabilityDto vulnerabilityDto, HttpServletRequest request) {
        loginService.login(request);

        var plugin = pluginRepository.findByDirectoryName(vulnerabilityDto.directoryName())
                .orElseGet(() -> new Plugin(vulnerabilityDto.directoryName()));

        if (plugin.getDisplayName() == null)
            plugin.setDisplayName(vulnerabilityDto.displayName());

        plugin.addPluginVulnerability(new PluginVulnerability(vulnerabilityDto.firstVersion(),
                vulnerabilityDto.lastVersion(), vulnerabilityDto.vulnerabilityType()));

        pluginRepository.save(plugin);
    }

    @PostMapping("/api/themeVulnerability")
    public void addThemeVulnerability(@RequestBody VulnerabilityDto vulnerabilityDto, HttpServletRequest request) {
        loginService.login(request);

        var theme = themeRepository.findByDirectoryName(vulnerabilityDto.directoryName())
                .orElseGet(() -> new Theme(vulnerabilityDto.directoryName()));

        if (theme.getDisplayName() == null)
            theme.setDisplayName(vulnerabilityDto.displayName());

        theme.addThemeVulnerability(new ThemeVulnerability(vulnerabilityDto.firstVersion(),
                vulnerabilityDto.lastVersion(), vulnerabilityDto.vulnerabilityType()));

        themeRepository.save(theme);
    }

    @PutMapping("/api/password")
    public void changePassword(@RequestBody ChangePasswordDto changePasswordDto, HttpServletRequest request) {
        loginService.login(request);

        loginService.changePassword(changePasswordDto.username(), changePasswordDto.newPassword());
    }

    @GetMapping("/api/scan")
    public List<ScanDto> getScans(HttpServletRequest request) {
        loginService.login(request);

        var scans = StreamSupport.stream(scanRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        return this.dtoMapper.scansToDto(scans);
    }

    @GetMapping("/api/themeVulnerability")
    public List<VulnerabilityDto> getThemeVulnerabilities(HttpServletRequest request) {
        loginService.login(request);

        var themeVulnerabilities = StreamSupport.stream(themeRepository.findAll().spliterator(), false)
                .flatMap(theme -> theme.getThemeVulnerabilities().stream()).collect(Collectors.toList());

        return this.dtoMapper.themeVulnerabilitiesToDto(themeVulnerabilities);
    }

    @GetMapping("/api/pluginVulnerability")
    public List<VulnerabilityDto> getPluginVulnerabilities(HttpServletRequest request) {
        loginService.login(request);

        var pluginVulnerabilities = StreamSupport.stream(pluginRepository.findAll().spliterator(), false)
                .flatMap(plugin -> plugin.getPluginVulnerabilities().stream()).collect(Collectors.toList());

        return this.dtoMapper.pluginVulnerabilitiesToDto(pluginVulnerabilities);
    }
}