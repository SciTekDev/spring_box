#Requires -Version 5.1
<#
    Checks local developer machine for the tools needed to build/run this project:
    Java 21, Maven 3.9+, Docker (for Consul + ELK stack).
    Run: powershell -ExecutionPolicy Bypass -File scripts/check-prereqs.ps1
#>

$ErrorActionPreference = "Continue"
$failures = @()

function Test-Command {
    param([string]$Name, [string]$Command, [string[]]$VersionArg = @("--version"))
    try {
        # stringify stderr lines so they print as plain text instead of full ErrorRecord dumps
        $lines = & $Command @VersionArg 2>&1 | ForEach-Object { $_.ToString() }
        $output = $lines | Out-String
        if ($LASTEXITCODE -ne 0 -and -not $output) {
            throw "no output"
        }
        Write-Host "[OK] $Name found:" -ForegroundColor Green
        Write-Host ($output.Trim() -split "`n" | Select-Object -First 3) -ForegroundColor DarkGray
        return $output
    } catch {
        Write-Host "[MISSING] $Name not found on PATH ($Command)" -ForegroundColor Red
        $script:failures += $Name
        return $null
    }
}

Write-Host "=== Checking prerequisites for spring_box ===" -ForegroundColor Cyan

# Java 21
$javaOutput = Test-Command -Name "Java" -Command "java" -VersionArg @("-version")
if ($javaOutput -and $javaOutput -notmatch "21\.") {
    Write-Host "[WARN] Java found but does not appear to be version 21. Confirm JAVA_HOME points to JDK 21." -ForegroundColor Yellow
}

# Maven 3.9+
$mvnOutput = Test-Command -Name "Maven" -Command "mvn" -VersionArg @("-version")
if ($mvnOutput -and $mvnOutput -match "Apache Maven (\d+\.\d+)") {
    $mvnVersion = [version]$Matches[1]
    if ($mvnVersion -lt [version]"3.9") {
        Write-Host "[WARN] Maven version $mvnVersion found, 3.9+ recommended." -ForegroundColor Yellow
    }
}

# Docker (for Consul + ELK)
Test-Command -Name "Docker" -Command "docker" -VersionArg @("--version")

# Docker Compose
Test-Command -Name "Docker Compose" -Command "docker" -VersionArg @("compose", "version")

Write-Host ""
if ($failures.Count -gt 0) {
    Write-Host "Missing prerequisites: $($failures -join ', ')" -ForegroundColor Red
    Write-Host "Install the missing tools and re-run this script." -ForegroundColor Red
    exit 1
} else {
    Write-Host "All prerequisites found." -ForegroundColor Green
    exit 0
}
