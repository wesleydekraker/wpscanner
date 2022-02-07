package tools.wesley.wpscanner.controllers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tools.wesley.wpscanner.dto.ScanDto;
import tools.wesley.wpscanner.dto.VulnerabilityDto;
import tools.wesley.wpscanner.domain.PluginVulnerability;
import tools.wesley.wpscanner.domain.Scan;
import tools.wesley.wpscanner.domain.ThemeVulnerability;

import java.util.List;

@Mapper
public interface DtoMapper {
    List<VulnerabilityDto> themeVulnerabilitiesToDto(List<ThemeVulnerability> entity);

    List<VulnerabilityDto> pluginVulnerabilitiesToDto(List<PluginVulnerability> entity);

    @Mapping(target="directoryName", source="entity.theme.directoryName")
    @Mapping(target="displayName", source="entity.theme.displayName")
    VulnerabilityDto themeVulnerabilityToDto(ThemeVulnerability entity);

    @Mapping(target="directoryName", source="entity.plugin.directoryName")
    @Mapping(target="displayName", source="entity.plugin.displayName")
    VulnerabilityDto pluginVulnerabilityToDto(PluginVulnerability entity);

    List<ScanDto> scansToDto(List<Scan> entity);

    ScanDto scanToDto(Scan entity);
}