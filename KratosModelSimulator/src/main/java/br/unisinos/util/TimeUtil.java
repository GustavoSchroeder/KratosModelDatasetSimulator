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
    
    public Date convertUnixTime(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(time * 1000));
        return calendar.getTime();
    }
    
    public Integer fetchHour(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.HOUR_OF_DAY);
    }
    
    public String checkWeekDay(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        if (c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || c1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return "Weekend";
        }
        return "Weekday";
    }
    
    public String defineDayShift(Calendar cal) {
        Integer hour = cal.get(Calendar.HOUR_OF_DAY);
        
        if (hour >= 6 && hour < 12) {
            return "Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Afternoon";
        } else if (hour >= 17 && hour < 20) {
            return "Evening";
        } else {
            return "Night";
        }
    }
    
    public String defineDayShift(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(cal.getTime());
        
        Integer hour = cal.get(Calendar.HOUR_OF_DAY);
        
        if (hour >= 6 && hour < 12) {
            return "Morning";
        } else if (hour >= 12 && hour < 17) {
            return "Afternoon";
        } else if (hour >= 17 && hour < 20) {
            return "Evening";
        } else {
            return "Night";
        }
    }    
}
