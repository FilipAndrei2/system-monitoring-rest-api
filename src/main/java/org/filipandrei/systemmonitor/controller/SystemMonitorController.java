package org.filipandrei.systemmonitor.controller;

import org.filipandrei.systemmonitor.SystemMonitorService;
import org.filipandrei.systemmonitor.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes system monitoring endpoints.
 *
 * <p>Available endpoints:
 * <ul>
 *   <li>GET /cpu  - Processor information</li>
 *   <li>GET /ram  - Memory information</li>
 *   <li>GET /disk - Disk stores information</li>
 *   <li>GET /gpu  - Graphics card information</li>
 *   <li>GET /procs - Processes information</li>
 *   <li>GET /os   - Operating system information</li>
 *   <li>GET /doc  - Plain-text API documentation</li>
 * </ul>
 */
@RestController
public class SystemMonitorController {

    private final SystemMonitorService smService;

    /**
     * Create controller with required service dependency.
     *
     * @param smService service providing system monitoring data
     */
    @Autowired
    public SystemMonitorController(SystemMonitorService smService) {
        this.smService = smService;
    }

    /**
     * Returns processor information.
     *
     * Endpoint: GET /cpu
     *
     * @return ProcessorInfoDto containing name, physical/logical core counts,
     *         maximum frequency and usage percentage
     */
    @GetMapping("/cpu")
    ProcessorInfoDto getCpuInfo() {
        return smService.getProcessorInfo();
    }

    /**
     * Returns memory information.
     *
     * Endpoint: GET /ram
     *
     * @return MemoryInfoDto with total, available and used memory (GB)
     */
    @GetMapping("/ram")
    MemoryInfoDto getRamInfo() {
        return smService.getMemoryInfo();
    }

    /**
     * Returns disk stores information.
     *
     * Endpoint: GET /disk
     *
     * @return array of DiskInfoDto describing model and size (GB) for each disk
     */
    @GetMapping("/disk")
    DiskInfoDto[] getDiskInfo() {
        return  smService.getDiskInfo();
    }

    /**
     * Returns graphics card information.
     * <p>
     * Endpoint: GET /gpu
     *
     * @return array of GpuInfoDto describing each GPU and its VRAM (GB)
     */
    @GetMapping("/gpu")
    GpuInfoDto[] getGpuInfo() {
        return smService.getGpuInfo();
    }

    /**
     * Returns processes information.
     * <p>
     * Endpoint: GET /procs
     *
     * @return ProcessesInfoDto containing total process count, an array of processes
     *         and system uptime in seconds
     */
    @GetMapping("/procs")
    ProcessesInfoDto getProcessesInfo() {
        return smService.getProcessesInfo();
    }

    /**
     * Returns operating system information.
     *
     * Endpoint: GET /os
     *
     * @return OsInfoDto with code name, family and version of the OS
     */
    @GetMapping("/os")
    OsInfoDto getOsInfo() {
        return smService.getOsInfo();
    }

    /**
     * Simple plain-text documentation endpoint listing available API routes.
     * Produces a human-readable summary to help clients discover endpoints.
     *
     * Endpoint: GET /doc
     *
     * @return Plain text describing available endpoints and DTO hints
     */
    @GetMapping(value = "/doc", produces = "text/plain")
    String getDocumentation() {
        return String.join("\n",
            "System Monitoring API",
            "",
            "GET /cpu    -> ProcessorInfoDto { name, physicalCores, logicalCores, maxFreq, usagePercentage }",
            "GET /ram    -> MemoryInfoDto { totalMb, availableMb, usedMb }",
            "GET /disk   -> DiskInfoDto[] { model, sizeGb }",
            "GET /gpu    -> GpuInfoDto[] { name, vramGb }",
            "GET /procs  -> ProcessesInfoDto { totalProcesses, processes[], uptimeSec }",
            "    ProcessInfoDto { name, pid, parentPid, state }",
            "GET /os     -> OsInfoDto { codeName, family, version }",
            "",
            "Content types: JSON for data endpoints, plain text for this documentation."
        );
    }
}
