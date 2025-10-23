package org.filipandrei.systemmonitor.dto;

public record MemoryInfoDto(
    long totalGb,
    long availableGb,
    long usedGb
) { }
