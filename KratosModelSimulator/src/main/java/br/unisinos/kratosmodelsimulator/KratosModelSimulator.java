/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package br.unisinos.kratosmodelsimulator;

import br.unisinos.model.ImportApplicationUse;
import br.unisinos.model.ImportBasicSmartphoneUseInformation;
import br.unisinos.model.ImportChargingActivity;
import br.unisinos.model.ImportDass21;
import br.unisinos.model.ImportMoodEMA;
import br.unisinos.model.ImportNMPQResponse;
import br.unisinos.model.ImportNotification;
import br.unisinos.model.ImportPSSResponse;
import br.unisinos.model.ImportSASResponse;
import br.unisinos.model.ImportScreenLocked;
import br.unisinos.model.ImportSleepEMA;
import br.unisinos.model.ImportStressEMA;
import br.unisinos.model.ImportTimeDarkEnviroment;
import br.unisinos.model.generator.ContextGenerator;
import br.unisinos.model.generator.GeneratePersonas;
import br.unisinos.model.generator.QuestionnaireSimulator;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class KratosModelSimulator {

    public static void main(String[] args) {
        try {
//Uploads
//            (new ImportTimeDarkEnviroment()).importFiles();
//            (new ImportChargingActivity()).importFiles();
//            (new ImportScreenLocked()).importFiles();
//            (new ImportSASResponse()).importFiles();
//            (new ImportPSSResponse()).importFiles();
//            (new ImportSleepEMA()).importFiles();
//            (new ImportNMPQResponse()).importFiles();
//            (new ImportStressEMA()).importFiles();
//            (new ImportDass21()).importFiles();
            (new ImportApplicationUse()).importFiles();
//            (new ImportMoodEMA()).importFiles();
//            (new ImportBasicSmartphoneUseInformation()).importFiles();
//            (new ImportNotification()).importFiles();

//Context Generation
//            (new ContextGenerator()).generateContext();
//            (new QuestionnaireSimulator()).fetchPeopleEligibleNomophobia();
//            (new QuestionnaireSimulator()).fetchPeopleEligibleSAS();
//Manhattan Distance
            (new GeneratePersonas()).generatePersonas();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hello World!");
    }
}
