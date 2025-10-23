package org.filipandrei.systemmonitor.dto;

public record OsInfoDto(
        String name,
        String family,
        String version
) { }
