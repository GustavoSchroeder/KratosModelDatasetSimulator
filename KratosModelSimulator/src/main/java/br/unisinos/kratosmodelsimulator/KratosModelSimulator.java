/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package br.unisinos.kratosmodelsimulator;

import br.unisinos.model.ImportApplicationUse;
import br.unisinos.model.ImportBasicSmartphoneUseInformation;
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
        try {
//            (new ImportTimeDarkEnviroment()).importFiles();
//            (new ImportChargingActivity()).importFiles();
//            (new ImportScreenLocked()).importFiles();
//            (new ImportApplicationUse()).importFiles();
//            (new ImportSASResponse()).importFiles();
//            (new ImportPSSResponse()).importFiles();
//            (new ImportSleepEMA()).importFiles();
//            (new ImportNMPQResponse()).importFiles();
//            (new ImportStressEMA()).importFiles();
//            (new ImportMoodEMA()).importFiles();
//            (new ImportBasicSmartphoneUseInformation()).importFiles();

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hello World!");
    }
}
