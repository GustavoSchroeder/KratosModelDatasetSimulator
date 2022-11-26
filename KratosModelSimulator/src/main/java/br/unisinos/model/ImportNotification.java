/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.model;

import br.unisinos.pojo.ContextInformation.Notification;
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
public class ImportNotification implements Serializable {

    private final PersonUtil personUtil;
    private final FileUtil fileUtil;

    public ImportNotification() {
        this.personUtil = new PersonUtil();
        this.fileUtil = new FileUtil();
    }

    public void importFiles() throws FileNotFoundException, IOException, ParseException {
        String folder = "./src/main/java/files/notification/low_level/notification.csv";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        Map<Long, List<Notification>> notifications = new HashMap<>();

        Map<Long, Person> persons = this.personUtil.findPersonList();

        EntityManager em = JPAUtil.getEntityManager();
        try {
            try ( BufferedReader br = new BufferedReader(new FileReader(folder))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split("	");

                    if (values[1].equalsIgnoreCase("session_id")) {
                        continue;
                    }

                    Long id;
                    Long nid;
                    Integer priority;
                    String packageName;
                    Date timePosted;
                    Date timeRemoved;
                    Integer sound;
                    Integer defaultSound;
                    Integer led;
                    Integer defaultLed;
                    Integer vibrationPattern;
                    Integer defaultVibration;
                    Integer ringerMode;
                    Integer idle;
                    Integer interactive;
                    Integer screenState;
                    Integer lockScrNotifs;
                    Integer flags;
                    Person person = null;

                }

            }
        } catch (Exception e) {
        }

       
        em.close();
    }

}
