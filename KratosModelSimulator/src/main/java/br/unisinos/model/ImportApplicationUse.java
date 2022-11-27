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
import java.util.Random;
import javax.persistence.EntityManager;

/**
 *
 * @author gustavolazarottoschroeder Import files from
 * https://github.com/aliannejadi/LSApp
 */
public class ImportApplicationUse implements Serializable {

    private final PersonUtil personUtil;
    private final FileUtil fileUtil;

    public ImportApplicationUse() {
        this.personUtil = new PersonUtil();
        this.fileUtil = new FileUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException, ParseException {
        String folder = "./src/main/java/files/smartphone_use/lsapp.tsv";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<Long, Integer> counter = new HashMap<>();
        Map<Long, List<ApplicationUse>> appsInUseMap = new HashMap<>();

        Map<Long, Person> persons = this.personUtil.findPersonList();
        Map<String, String> appsCategory = this.personUtil.fetchAppCategory();

        EntityManager em = JPAUtil.getEntityManager();
        try {
            try ( BufferedReader br = new BufferedReader(new FileReader(folder))) {
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
                    String appCategory = appsCategory.get(appName);

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

                    Person person = null;

                    ApplicationUse appInUse = new ApplicationUse(
                            person,
                            sessionId,
                            dateTime,
                            appName,
                            eventType,
                            appCategory
                    );
                    appsInUseMap.get(idPerson).add(appInUse);
                }

            }
        } catch (Exception e) {
        }

        em.getTransaction().begin();

        List<Integer> positionsGone = new ArrayList<>();

        List<Long> ids = new ArrayList<>(appsInUseMap.keySet());
        Integer max = ids.size() - 1;

        Random rand = new Random();
        for (Integer i = 0; i < 60; i++) {
            Integer n = null;
            do {
                n = rand.nextInt(max);
            } while (positionsGone.contains(n));

            positionsGone.add(n);
            List<ApplicationUse> apps = appsInUseMap.get(ids.get(n));

            Long idPerson = i.longValue();
            Person person = persons.get(idPerson);

            if (null == person) {
                System.out.println(idPerson);
                if (idPerson <= 60L) {
                    this.personUtil.createNewSimplePerson(idPerson);
                    persons = this.personUtil.findPersonList();
                    person = persons.get(idPerson);
                } else {
                    continue;
                }
            }

            for (ApplicationUse app : apps) {
                app.setPerson(person);
                em.merge(app);
            }
        }

        em.getTransaction().commit();
        em.close();
    }

}
