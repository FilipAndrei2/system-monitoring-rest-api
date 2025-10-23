package org.filipandrei.systemmonitor.dto;

public record ProcessesInfoDto(
        int runningProcessCount,
        ProcessInfoDto[] processes,
        long sistemUptime // secunde de la boot
) {
}
