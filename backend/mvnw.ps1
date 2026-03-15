<#
.SYNOPSIS
Downloads and runs Maven locally without requiring installation.
Also sets JAVA_HOME to JDK 25 if available.

.DESCRIPTION
This script acts like a Maven Wrapper (mvnw) for Windows PowerShell environments.
It ensures that the correct Java version is used and downloads a local copy of Maven if needed.

.EXAMPLE
.\mvnw.ps1 clean install
.\mvnw.ps1 spring-boot:run
#>

$ErrorActionPreference = "Stop"

# --- Configuration ---
$MavenVersion = "3.9.6"
$MavenUrl = "https://archive.apache.org/dist/maven/maven-3/$MavenVersion/binaries/apache-maven-$MavenVersion-bin.zip"
$WrapperDir = Join-Path $PSScriptRoot ".mvn\wrapper"
$MavenHome = Join-Path $WrapperDir "apache-maven-$MavenVersion"
$MavenBin = Join-Path $MavenHome "bin"

# --- Java Setup ---
function Get-JavaVersion {
    param ($javaExePath)
    try {
        # Check if file exists first
        if (-not (Test-Path $javaExePath)) { return $null }

        # Execute and capture all output (stdout + stderr)
        $pinfo = New-Object System.Diagnostics.ProcessStartInfo
        $pinfo.FileName = $javaExePath
        $pinfo.Arguments = "-version"
        $pinfo.RedirectStandardError = $true
        $pinfo.UseShellExecute = $false
        $p = New-Object System.Diagnostics.Process
        $p.StartInfo = $pinfo
        $p.Start() | Out-Null
        $p.WaitForExit()
        $output = $p.StandardError.ReadToEnd()

        if ($output -match 'version "(25(?:\.\d+)*)"') {
            return [version]$matches[1]
        }
    } catch {
        Write-Host "Error checking version for $javaExePath : $($_.Exception.Message)" -ForegroundColor DarkGray
    }
    return $null
}

# 1. Check existing JAVA_HOME
if ($env:JAVA_HOME -and (Test-Path "$env:JAVA_HOME\bin\java.exe")) {
    $ver = Get-JavaVersion "$env:JAVA_HOME\bin\java.exe"
    if ($ver.Major -eq 25) {
        Write-Host "Using Java 25 from JAVA_HOME: $env:JAVA_HOME" -ForegroundColor Cyan
    } else {
        Write-Host "Current JAVA_HOME is version $($ver.Major), but project requires Java 25." -ForegroundColor Yellow
        $env:JAVA_HOME = $null # Reset so we search for 25
    }
}

# 2. Search for Java 25 if not found
if (-not $env:JAVA_HOME) {
    $commonPaths = @(
        "C:\Program Files\Java\jdk-25*",
        "C:\Program Files\Common Files\Oracle\Java\javapath",
        "$env:LOCALAPPDATA\Programs\Common\Oracle\Java\javapath",
        "$env:USERPROFILE\.jdks\openjdk-25*"
    )

    foreach ($pattern in $commonPaths) {
        $candidates = Get-ChildItem -Path $pattern -ErrorAction SilentlyContinue | Sort-Object Name -Descending
        foreach ($candidate in $candidates) {
            $javaExe = Join-Path $candidate.FullName "bin\java.exe"
            if (Test-Path $javaExe) {
                 $ver = Get-JavaVersion $javaExe
                 if ($ver.Major -eq 25) {
                     $env:JAVA_HOME = $candidate.FullName
                     $env:Path = "$($candidate.FullName)\bin;$env:Path"
                     Write-Host "Found and using Java 25 at: $($candidate.FullName)" -ForegroundColor Cyan
                     break
                 }
            }
        }
        if ($env:JAVA_HOME) { break }
    }
}

# 3. Final Check
if (-not $env:JAVA_HOME) {
    Write-Host "ERROR: Java 25 (JDK 25) could not be found." -ForegroundColor Red
    Write-Host "Please install Java 25 and set JAVA_HOME, or install it to the default location." -ForegroundColor White
    Write-Host "Download: https://jdk.java.net/25/" -ForegroundColor White
    exit 1
}

# --- Maven Setup ---
if (-not (Test-Path "$MavenBin\mvn.cmd")) {
    Write-Host "Maven wrapper not found. Installing Maven $MavenVersion..." -ForegroundColor Cyan

    # Create directory
    if (-not (Test-Path $WrapperDir)) {
        New-Item -ItemType Directory -Force -Path $WrapperDir | Out-Null
    }

    # Download
    $ZipPath = Join-Path $WrapperDir "maven.zip"
    Write-Host "Downloading from $MavenUrl..."
    Invoke-WebRequest -Uri $MavenUrl -OutFile $ZipPath

    # Extract
    Write-Host "Extracting..."
    Expand-Archive -Path $ZipPath -DestinationPath $WrapperDir -Force
    Remove-Item $ZipPath -Force

    Write-Host "Maven installed to $MavenHome" -ForegroundColor Green
}

# --- Execution ---
$MvnCmd = Join-Path $MavenBin "mvn.cmd"

# Execute Maven with provided arguments
# Note: Using & operator to invoke the command
# We use $args to pass all arguments to mvn
& $MvnCmd $args
if ($LASTEXITCODE -ne 0) {
    exit $LASTEXITCODE
}
