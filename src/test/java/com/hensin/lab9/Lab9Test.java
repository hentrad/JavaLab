package com.hensin.lab9;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class Lab9Test {

    @Test
    public void testDeleteFilesSuccess(@TempDir Path tempDir) throws Exception {
        Files.createFile(tempDir.resolve("file1.txt"));
        Files.createFile(tempDir.resolve("file2.txt"));

        assertEquals(2, tempDir.toFile().listFiles().length);

        FileCleaner cleaner = new FileCleaner();
        cleaner.deleteAllFiles(tempDir.toString());

        // папка должна остаться, но быть пустой
        assertTrue(tempDir.toFile().exists());
        assertEquals(0, tempDir.toFile().listFiles().length);
    }

    @Test
    public void testDeleteNestedDirectories(@TempDir Path tempDir) throws Exception {
        //tempDir/subDir/subSubDir/file.txt
        Path subDir = Files.createDirectory(tempDir.resolve("subDir"));
        Path subSubDir = Files.createDirectory(subDir.resolve("subSubDir"));
        Files.createFile(subSubDir.resolve("deepFile.txt"));

        FileCleaner cleaner = new FileCleaner();
        cleaner.deleteAllFiles(tempDir.toString());

        assertEquals(0, tempDir.toFile().listFiles().length);
    }
}