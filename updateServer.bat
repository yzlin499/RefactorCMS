@echo off



:start
cls
wmic /node:10.0.138.172 /user:administrator /password:zhbit123456!!! process call create "D:\server\bin\shutdown.bat"

rem �˴�����дcopy����
xcopy /s /Y "D:\49968\Workspaces\InttlliJ IDEA\RefactorCMS\out\production\RefactorCMS" Z:\webapps\ROOT\WEB-INF\classes

wmic /node:10.0.138.172 /user:administrator /password:zhbit123456!!! process call create "D:\server\bin\startup.bat"

echo.�����������ɹ�,��������ٴ�����������
pause
goto :start

