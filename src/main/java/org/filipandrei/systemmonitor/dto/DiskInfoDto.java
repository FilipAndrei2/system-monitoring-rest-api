package org.filipandrei.systemmonitor.dto;

public record DiskInfoDto(
        String model,
        long sizeGb
) {
}
