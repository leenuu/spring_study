#!/bin/sh

ehco "`git add .`"
echo "`git commit -m "$*"`"
echo "`git push -u origin main`"   
