package tools.wesley.wpscanner.controllers;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tools.wesley.wpscanner.wp.BackgroundScan;
import tools.wesley.wpscanner.wp.CustomHttpClient;
import tools.wesley.wpscanner.wp.WpService;
import tools.wesley.wpscanner.repositories.*;
import tools.wesley.wpscanner.dto.*;
import tools.wesley.wpscanner.domain.Scan;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

@RestController
public class ScanController {
    @Autowired
    private CustomHttpClient httpClient;
    @Autowired
    private ThemeRepository themeRepository;
    @Autowired
    private PluginRepository pluginRepository;
    @Autowired
    private ScanRepository scanRepository;
    @Autowired
    private WpService wpService;

    private final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    @PostMapping("/api/scan")
    public ScanGuidDto startScan(@Valid @RequestBody ScanRequestDto scanRequestDto, HttpServletRequest request) {
        var url = scanRequestDto.url();
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        var scanGuid = UUID.randomUUID().toString().replace("-", "");
        var scan = new Scan(scanGuid, request.getRemoteAddr(), url);
        scanRepository.save(scan);

        Thread t = new Thread(new BackgroundScan(scan, this.httpClient, this.wpService, this.scanRepository));
        t.start();

        return new ScanGuidDto(scanGuid);
    }

    @GetMapping("/api/scan/{guid}")
    public ScanDetailDto getScan(@PathVariable String guid) {
        var scan = scanRepository.findByGuid(guid);
        if (scan.isEmpty())
            return new ScanDetailDto();

        return new ScanDetailDto(scan.get());
    }

    @PostMapping("/api/scan/plugin")
    public void addPlugin(@RequestBody PluginDto pluginDto) {
        var scan = scanRepository.findByGuid(pluginDto.scanGuid());
        if (scan.isPresent()) {
            wpService.addPluginToScan(scan.get(), pluginDto.pluginName(), false);
            scanRepository.save(scan.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/scan/theme")
    public void addTheme(@RequestBody ThemeDto themeDto) {
        var scan = scanRepository.findByGuid(themeDto.scanGuid());
        if (scan.isPresent()) {
            wpService.addThemeToScan(scan.get(), themeDto.themeName(), false);
            scanRepository.save(scan.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}