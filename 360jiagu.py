#!/usr/bin/env python
# -*- coding: utf-8 -*-
import os
import shutil
import push_fir

apkDirPathName = "app/build/outputs/apk/release"
jiaguDirPathWorkName="/Users/happyhou/develop/bte/360jiagubao_mac/jiagu"
jiaguOutputPath=" /Users/happyhou/Desktop/360jiaguapk"


mainPath = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
apkPath = mainPath +'/'+apkDirPathName

#region find apk
src_apks=[]
for file in os.listdir(apkPath):
    if os.path.isfile(apkPath +'/'+file):
        extension = os.path.splitext(file)[1][1:]
        if extension in 'apk':
            src_apks.append(apkPath+'/'+file)


#region jiagu
os.chdir(jiaguDirPathWorkName)
os.system("java/bin/java -jar jiagu.jar -jiagu "+src_apks[0]+jiaguOutputPath+" -autosign  -automulpkg")
