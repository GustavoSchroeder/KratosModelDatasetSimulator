/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class FileUtil {

    public List<String> scanForFiles(String path) throws NullPointerException {
        File directory = new File(path);
        System.out.println(directory.getAbsolutePath());

        String[] directoryContents = directory.list();

        List<String> files = new ArrayList<>();

        for (String fileName : directoryContents) {
            files.add(fileName);
        }
        return files;
    }
}
