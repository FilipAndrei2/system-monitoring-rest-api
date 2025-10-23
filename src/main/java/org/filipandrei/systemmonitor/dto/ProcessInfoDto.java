package org.filipandrei.systemmonitor.dto;

public record ProcessInfoDto(
        String name,
        int pid,
        int parrentPid,
        String state
) {
}
