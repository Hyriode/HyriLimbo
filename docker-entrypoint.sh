#!/bin/bash

: "${MIN_MEMORY:=256M}}"
: "${MAX_MEMORY:=4G}}"

echo "[init] Copying Limbo jar"
cp /usr/app/HyriLimbo.jar /server

echo "[init] Starting process..."
java -Xms${MIN_MEMORY} -Xmx${MAX_MEMORY} -jar HyriLimbo.jar