@ECHO OFF
SET LogPath=C:\OEM\AcerLogs\%1.log
Echo.>>%LogPath%
ECHO %DATE% %TIME% [Log START]  ================== %~dpnx0 =================== >> %LogPath%
SETLOCAL ENABLEDELAYEDEXPANSION

ECHO %DATE% %TIME% [Log TRACE]  reg query HKLM\Software\OEM\Metadata /v sys >>%LogPath% 2>&1
reg query HKLM\Software\OEM\Metadata /v sys >>%LogPath% 2>&1
FOR /F "tokens=3 delims= " %%T IN ('reg query HKLM\Software\OEM\Metadata /v sys') DO set @sys=%%T
ECHO %DATE% %TIME% [Log TRACE]  call :%1 >>%LogPath%
call :%1


SETLOCAL DISABLEDELAYEDEXPANSION
ECHO %DATE% %TIME% [Log LEAVE]  ================== %~dpnx0 =================== >> %LogPath%
echo. >> %LogPath%
exit /b 0



:FirstBoot
IF /i "%@sys%" EQU "DTP" exit /b 0
pushd .\LMDriver
ECHO %DATE% %TIME% [Log TRACE]  cmd /c %CD%InstallLMDriver.exe >>%LogPath%
cmd /c InstallLMDriver.exe
popd
exit /b 0



:UserAlaunch
ECHO %DATE% %TIME% [Log TRACE]  REG Import DisableSetAPM.REG >>%LogPath% 2>&1
REG Import DisableSetAPM.REG >>%LogPath% 2>&1
ECHO %DATE% %TIME% [Log TRACE]  setup.exe -s and leave QuickAccessWithEPM.tag for DPOP >>%LogPath%
ECHO %DATE% %TIME% [Log TRACE]  %CD%\setup.exe -s >C:\OEM\Preload\QuickAccessWithEPM.tag
setup.exe -s
exit /b 0