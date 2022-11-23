/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package br.unisinos.kratosmodelsimulator;

import br.unisinos.model.ImportApplicationUse;
import br.unisinos.model.ImportChargingActivity;
import br.unisinos.model.ImportMoodEMA;
import br.unisinos.model.ImportNMPQResponse;
import br.unisinos.model.ImportPSSResponse;
import br.unisinos.model.ImportSASResponse;
import br.unisinos.model.ImportScreenLocked;
import br.unisinos.model.ImportSleepEMA;
import br.unisinos.model.ImportStressEMA;
import br.unisinos.model.ImportTimeDarkEnviroment;
import br.unisinos.pojo.Person;
import br.unisinos.util.JPAUtil;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class KratosModelSimulator {

    public static void main(String[] args) {
        //        Person p1 = new Person(null, "Carlos da Silva", "carlos@gmail.com");
//		Person p2 = new Person(null, "Joaquim Torres", "joaquim@gmail.com");
//		Person p3 = new Person(null, "Ana Maria", "ana@gmail.com");
//
//		EntityManager em = JPAUtil.getEntityManager();
//		em.getTransaction().begin();
//		em.persist(p1);
//		em.persist(p2);
//		em.persist(p3);
//		em.getTransaction().commit();
//		
//		System.out.println("Pronto!");
//		em.close();        

        try {
//            (new ImportTimeDarkEnviroment()).importFiles();
//            (new ImportChargingActivity()).importFiles();
//            (new ImportScreenLocked()).importFiles();
//            (new ImportApplicationUse()).importFiles();
//            (new ImportSASResponse()).importFiles();
//            (new ImportPSSResponse()).importFiles();
//              (new ImportSleepEMA()).importFiles();
//              (new ImportNMPQResponse()).importFiles();
              (new ImportStressEMA()).importFiles();
              (new ImportMoodEMA()).importFiles();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hello World!");
    }
}
