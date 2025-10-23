package org.filipandrei.systemmonitor;

import org.filipandrei.systemmonitor.dto.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;

import static java.lang.Math.floor;

/**
 * Service that queries system hardware and OS information using OSHI and
 * converts it to DTOs consumed by the REST controller.
 */
@Service
public class SystemMonitorService {
    private @NotNull SystemInfo systemInfo = new SystemInfo();

    /**
     * Retrieve processor information.
     *
     * @return ProcessorInfoDto containing processor name, physical and logical core counts,
     *         maximum frequency (Hz) and recent usage percentage
     */
    public ProcessorInfoDto getProcessorInfo() {
        var proc = systemInfo.getHardware().getProcessor();
        String name = proc.getProcessorIdentifier().getName();
        var physicalCores = proc.getPhysicalProcessorCount();
        var logicalCores = proc.getLogicalProcessorCount();
        var maxFreq = proc.getMaxFreq();
        var usagePercentage = proc.getSystemCpuLoad(800);
        return new ProcessorInfoDto(name, physicalCores, logicalCores, maxFreq, usagePercentage);
    }

    /**
     * Retrieve memory information.
     *
     * @return MemoryInfoDto with total, available and used memory (GB)
     */
    public MemoryInfoDto getMemoryInfo() {
        var mem = systemInfo.getHardware().getMemory();
        long total = bytesToGb(mem.getTotal());
        long available = bytesToGb(mem.getAvailable());
        long used = total - available;

        return new MemoryInfoDto(total, available, used);
    }

    /**
     * Retrieve disk stores information.
     *
     * @return array of DiskInfoDto describing each disk store (model and size in GB)
     */
    public DiskInfoDto[] getDiskInfo() {
        var disk = systemInfo.getHardware().getDiskStores();
        return disk.stream()
                .map(d -> new DiskInfoDto(d.getModel(), bytesToGb(d.getSize())))
                .toArray(DiskInfoDto[]::new);
    }

    /**
     * Retrieve graphics card information.
     *
     * @return array of GpuInfoDto describing each graphics card and VRAM (GB)
     */
    public GpuInfoDto[] getGpuInfo() {
        var gpu = systemInfo.getHardware().getGraphicsCards();
        return gpu.stream()
                .map(g -> new GpuInfoDto(g.getName(), bytesToGb(g.getVRam())))
                .toArray(GpuInfoDto[]::new);
    }

    /**
     * Retrieve processes information.
     *
     * @return ProcessesInfoDto containing total process count, details for each process,
     *         and system uptime in seconds
     */
    public ProcessesInfoDto getProcessesInfo() {
        var pi = systemInfo.getOperatingSystem();
        return new ProcessesInfoDto(pi.getProcessCount(), getProcInfo(), pi.getSystemUptime());
    }

    private ProcessInfoDto[] getProcInfo() {
        var proc = systemInfo.getOperatingSystem().getProcesses();
        return proc.stream()
                .map(p -> new ProcessInfoDto(p.getName(), p.getProcessID(), p.getParentProcessID(), p.getState().toString()))
                .toArray(ProcessInfoDto[]::new);
    }

    /**
     * Retrieve operating system information.
     *
     * @return OsInfoDto with OS code name, family and version string
     */
    public OsInfoDto getOsInfo() {
        var osInf = systemInfo.getOperatingSystem();
        return new OsInfoDto(osInf.getVersionInfo().getCodeName(), osInf.getFamily(), osInf.getVersionInfo().getVersion());
    }

    private static double truncate2(double val) {
        return floor(val * 100) / 100;
    }

    private static long bytesToGb(long bytes) {
        return bytes / 1024 / 1024 / 1024;
    }
}
