#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import shutil

apkDirPathName = "app/build/outputs/apk/release"


mainPath = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
apkPath = mainPath +'/'+apkDirPathName

# region clean
shutil.rmtree(apkPath, True)

#region build
os.system("./gradlew assembleRelease")
#
#region find apk
src_apks=[]
for file in os.listdir(apkPath):
    if os.path.isfile(apkPath +'/'+file):
        extension = os.path.splitext(file)[1][1:]
        if extension in 'apk':
            src_apks.append(apkPath+'/'+file)

#region publish fir
os.system("fir publish "+src_apks[0]+" -T 7ad0f574bf932a3495ed3463ef350c00 -Q")
#