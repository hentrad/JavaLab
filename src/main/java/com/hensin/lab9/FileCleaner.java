package com.hensin.lab9;

import com.hensin.LabException;
import com.hensin.LabException.Code;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileCleaner {

    public void deleteAllFiles(String folderPath) throws LabException {
        if (folderPath == null || folderPath.trim().isEmpty()) {
            throw new LabException(Code.PATH_NULL_OR_EMPTY);
        }

        folderPath = folderPath.trim();
        if (folderPath.equals("~") || folderPath.startsWith("~/") || folderPath.startsWith("~\\")) {
            String homeDir = System.getProperty("user.home");
            folderPath = homeDir + folderPath.substring(1);
        }

        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new LabException(Code.PATH_NOT_FOUND, folder.getAbsolutePath());
        }
        if (!folder.isDirectory()) {
            throw new LabException(Code.PATH_NOT_DIRECTORY, folder.getAbsolutePath());
        }
        
        checkSystemPaths(folder);

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteRecursive(file);
            }
        }
    }

    private void checkSystemPaths(File folder) throws LabException {
        try {
            String canonicalPath = folder.getCanonicalPath();
            
            List<String> blackList = new ArrayList<>();
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                blackList.add("C:\\");
                blackList.add("C:\\Windows");
                blackList.add("C:\\Program Files");
                blackList.add("C:\\Program Files (x86)");
            } else {
                blackList.add("/");
                blackList.add("/dev");
                blackList.add("/etc");
                blackList.add("/bin");
                blackList.add("/sbin");
                blackList.add("/usr");
                blackList.add("/sys");
                blackList.add("/proc");
                blackList.add("/System");
                blackList.add("/Library");
            }

            for (String sysPath : blackList) {
                File sysDir = new File(sysPath);
                String sysCanonicalPath = sysDir.getCanonicalPath();
                
                if (canonicalPath.equals(sysCanonicalPath) || 
                    canonicalPath.startsWith(sysCanonicalPath + File.separator)) {
                    throw new LabException(Code.UNSAFE_PATH, canonicalPath);
                }
            }
        } catch (IOException e) {
            throw new LabException(Code.UNSAFE_PATH, folder.getAbsolutePath());
        }
    }

    private void deleteRecursive(File file) throws LabException {
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles != null) {
                for (File sub : subFiles) {
                    deleteRecursive(sub);
                }
            }
        }
        if (!file.delete()) {
            throw new LabException(Code.DELETE_FAILED, file.getAbsolutePath());
        }
    }
}