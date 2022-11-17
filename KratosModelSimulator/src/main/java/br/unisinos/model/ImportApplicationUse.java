/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.ContextInformation.ApplicationUse;
import br.unisinos.pojo.Person;
import br.unisinos.util.PersonUtil;
import br.unisinos.util.FileUtil;
import br.unisinos.util.JPAUtil;
import br.unisinos.util.TimeUtil;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;

/**
 *
 * @author gustavolazarottoschroeder Import files from
 * https://github.com/aliannejadi/LSApp
 */
public class ImportApplicationUse implements Serializable {

    private final PersonUtil personUtil;
    private final TimeUtil timeUtil;
    private final FileUtil fileUtil;

    public ImportApplicationUse() {
        this.personUtil = new PersonUtil();
        this.timeUtil = new TimeUtil();
        this.fileUtil = new FileUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException, ParseException {
        String folder = "./src/main/java/files/smartphone_use/";
        List<String> files;
        try {
            files = this.fileUtil.scanForFiles(folder);
        } catch (NullPointerException e) {
            System.out.println(e.getStackTrace());

            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<Long, Integer> counter = new HashMap<>();
        Map<Long, List<ApplicationUse>> appsInUseMap = new HashMap<>();

        EntityManager em = JPAUtil.getEntityManager();
        try {
            for (String fileName : files) {
                if (fileName.contains("tsv")) {
                    try ( BufferedReader br = new BufferedReader(new FileReader(folder + fileName))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split("	");

                            if (values[1].equalsIgnoreCase("session_id")) {
                                continue;
                            }

                            Long idPerson = Long.parseLong(values[0]);
                            Long sessionId = Long.parseLong(values[1]);
                            String time = values[2].trim();
                            Date dateTime = sdf.parse(time);
                            String appName = values[3].trim();
                            String eventType = values[4].trim();

//                            System.out.println(idPerson + ";"
//                                    + sessionId + ";"
//                                    + time + ";"
//                                    + appName + ";"
//                                    + eventType
//                            );

                            if (counter.get(idPerson) == null) {
                                counter.put(idPerson, 0);
                            }
                            counter.put(idPerson, counter.get(idPerson) + 1);

                            if (appsInUseMap.get(idPerson) == null) {
                                appsInUseMap.put(idPerson, new ArrayList<>());
                            }

                            Person person = this.personUtil.findPerson(idPerson);
                            ApplicationUse appInUse = new ApplicationUse(
                                    person,
                                    sessionId,
                                    dateTime,
                                    appName,
                                    eventType
                            );
                            appsInUseMap.get(idPerson).add(appInUse);
                        }
                    }
                }
            }
        } catch (Exception e) {
        }

        List<Integer> values = new ArrayList<>(counter.values());
        Collections.sort(values);
        Collections.reverse(values);
        List<Long> keysToImport = new ArrayList<>();

        for (int i = 0; i < values.size(); i++) {
            for (Map.Entry<Long, Integer> entry : counter.entrySet()) {
                Long key = entry.getKey();
                Integer val = entry.getValue();

                System.out.println(key + ";" + val);

                if (values.get(i) == val.intValue()) {
                    keysToImport.add(key);
                    values.remove(i);
                    counter.remove(key);
                    i--;
                }
            }
        }

        em.getTransaction().begin();

        for (Long key : keysToImport) {
            List<ApplicationUse> apps = appsInUseMap.get(key);
            for (ApplicationUse app : apps) {
                em.merge(app);
            }
        }

        em.getTransaction().commit();
        em.close();
    }

}
