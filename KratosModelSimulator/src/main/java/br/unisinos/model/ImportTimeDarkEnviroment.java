/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.Person;
import br.unisinos.pojo.TimeDarkEnvironment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class ImportTimeDarkEnviroment implements Serializable {

    private PersonUtil personUtil;

    public ImportTimeDarkEnviroment() {
        this.personUtil = new PersonUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException {
        String folder = "./src/main/java/files/dark/";
        List<String> files;
        try {
            files = scanForFiles(folder);
        } catch (NullPointerException e) {
            System.out.println(e.getStackTrace());

            return;
        }

        for (String fileName : files) {
            if (fileName.contains("csv")) {
                System.out.println(fileName.split("_")[1].replace(".csv", "").replace("u", ""));
                Long idPerson = Long.parseLong(fileName.split("_")[1].replace(".csv", "").replace("u", ""));

                try ( BufferedReader br = new BufferedReader(new FileReader(folder + fileName))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        System.out.println(values[0] + ";" + values[1]);

                        Long timeInicial = Long.parseLong(values[0].trim());
                        Long timeFinal = Long.parseLong(values[1].trim());

                        Date inicialDate = new Date(timeInicial * 1000);
                        Date finalDate = new Date(timeFinal * 1000);
                        Date diffHours = diffHours(inicialDate, finalDate);

                        Person person = this.personUtil.findPerson(idPerson);

                        TimeDarkEnvironment tde = new TimeDarkEnvironment(//Todo);
                               
                  
                    
                    
                    }
                }
            }
        }
    }

    private Date diffHours(Date d1, Date d2) {
        Calendar ini = Calendar.getInstance();
        Calendar fin = Calendar.getInstance();

        ini.setTime(d1);
        fin.setTime(d2);

        Integer minutesDec = 0;

        Integer hourFin = fin.get(Calendar.HOUR_OF_DAY);
        Integer minuteFin = fin.get(Calendar.MINUTE);

        while (true) {
            Integer hourIni = ini.get(Calendar.HOUR_OF_DAY);
            Integer minuteIni = ini.get(Calendar.MINUTE);

            if (hourIni.intValue() == hourFin 
                    && minuteIni.intValue() == minuteFin) {
                break;
            }
            
            ini.add(Calendar.MINUTE, 1);
            minutesDec++;
        }
        
        Calendar cal = Calendar.getInstance();
        Integer hour = (int) minutesDec / 60;
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minutesDec - (60 * hour));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    private List<String> scanForFiles(String path) throws NullPointerException {
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
