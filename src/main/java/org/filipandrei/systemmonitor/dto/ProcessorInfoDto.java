package org.filipandrei.systemmonitor.dto;

public record ProcessorInfoDto(
        String name,
        int physicalCores,
        int logicalCores,
        long maxFreqHz,
        double usagePercentage
) {
}
