echo off
SET DAY=%date:~0,2%
SET MONTH=%date:~3,2%
SET YEAR=%date:~6,4%
COPY /Y TowerDefense\build\apk\TowerDefense-release.apk "%APK_DIR%\TowerDefense_%YEAR%-%MONTH%-%DAY%.apk"