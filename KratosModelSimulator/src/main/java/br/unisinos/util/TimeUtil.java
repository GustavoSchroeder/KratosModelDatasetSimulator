/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.unisinos.util;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author gustavolazarottoschroeder
 */
public class TimeUtil {
      public Date diffHours(Date d1, Date d2) {
        Calendar ini = Calendar.getInstance();
        Calendar fin = Calendar.getInstance();

        ini.setTime(d1);
        fin.setTime(d2);

        Integer minutesDec = 0;

        Integer hourFin = fin.get(Calendar.HOUR);
        Integer minuteFin = fin.get(Calendar.MINUTE);

        while (true) {
            Integer hourIni = ini.get(Calendar.HOUR);
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
        Integer minutes = minutesDec - (60 * hour);
        cal.set(Calendar.HOUR, hour);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
