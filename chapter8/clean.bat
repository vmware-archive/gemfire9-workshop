@echo off
@del /s/q server1\*
@del /s/q server2\*
@del /s/q locator\*

if exist target del /s/q target\*
