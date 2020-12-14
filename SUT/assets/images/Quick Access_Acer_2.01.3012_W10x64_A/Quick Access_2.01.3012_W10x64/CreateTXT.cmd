@Echo off
SET LogPath=c:\OEM\AcerLogs\UserAlaunch.log
ECHO.>>%LogPath%
ECHO %DATE% %TIME%[Log START]  ============ %~dpnx0 ============ >> %LogPath%


ECHO %DATE% %TIME% [Log TRACE]  reg query HKEY_LOCAL_MACHINE\SOFTWARE\OEM\GCMReadiness\IM /v IM_Entry >>%LogPath% 2>&1
reg query HKEY_LOCAL_MACHINE\SOFTWARE\OEM\GCMReadiness\IM /v IM_Entry >>%LogPath% 2>&1
FOR /F "tokens=3 delims= " %%T IN ('reg query HKEY_LOCAL_MACHINE\SOFTWARE\OEM\GCMReadiness\IM /v IM_Entry') DO set @IMEntry=%%T

for /f "skip=2 tokens=3" %%A in ('reg query HKLM\Software\OEM\Metadata /v Brand') do set Brand=%%A
IF /I "%Brand%" EQU "Packard" SET Brand=Packard Bell
ECHO %DATE% %TIME%[Log TRACE]  Brand is [%Brand%] >>%LogPath%
SET QuickAccess=%ALLUSERSPROFILE%\Microsoft\Windows\Start Menu\Programs\%Brand%\%Brand% Quick Access.lnk
::::	2016/8/1
::::		Entry sku discard to pin Quick Access, due to Entry and Core sku share the image.
IF /I "%@IMEntry%" EQU "TRUE" SET QuickAccess=%ALLUSERSPROFILE%\Microsoft\Windows\Start Menu\Programs\%Brand%\FAKE.lnk
echo %QuickAccess%> MFULINK.txt
echo %QuickAccess%> TASKBARLINK.txt
echo %QuickAccess%> AUMID_QuickAccess.txt
echo NA>> AUMID_QuickAccess.txt
ECHO %DATE% %TIME%[Log TRACE]  Create TASKBARLINK.txt and AUMID_QuickAccess.txt is [%QuickAccess%] >>%LogPath%

:END
ECHO %DATE% %TIME%[Log LEAVE]  ============ %~dpnx0 ============ >> %LogPath%
echo. >> %LogPath%