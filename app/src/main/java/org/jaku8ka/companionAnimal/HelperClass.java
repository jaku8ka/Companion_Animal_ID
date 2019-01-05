package org.jaku8ka.companionAnimal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HelperClass  {

    public HelperClass() {
    }

    public String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        Integer ageInt = new Integer(age);
        String fail = "0";
        if (ageInt < 0)
            return fail;

        String ageS = ageInt.toString();

        return ageS;
    }

    public long getNumberOfDays(String date1,String date2,String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        Date Date1 = null,Date2 = null;
        try{
            Date1 = sdf.parse(date1);
            Date2 = sdf.parse(date2);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        return (Date2.getTime() - Date1.getTime())/(24*60*60*1000);
    }

    public float getPbPercent(int currentDays, long daysToEnd) {

        return (currentDays * 100 / daysToEnd);
    }

    public String getAgePet(int years, int spinnerValue) {
        String petYears = "neviem spocitat";

        switch (spinnerValue) {
            case 0: //macka
                switch (years) {
                    case 1:
                        petYears = "17";
                        break;
                    case 2:
                        petYears = "24";
                        break;
                    case 3:
                        petYears = "28";
                        break;
                    case 4:
                        petYears = "32";
                        break;
                    case 5:
                        petYears = "36";
                        break;
                    case 6:
                        petYears = "40";
                        break;
                    case 7:
                        petYears = "44";
                        break;
                    case 8:
                        petYears = "48";
                        break;
                    case 9:
                        petYears = "52";
                        break;
                    case 10:
                        petYears = "56";
                        break;
                    case 11:
                        petYears = "60";
                        break;
                    case 12:
                        petYears = "64";
                        break;
                    case 13:
                        petYears = "68";
                        break;
                    case 14:
                        petYears = "72";
                        break;
                    case 15:
                        petYears = "76";
                        break;
                    case 16:
                        petYears = "80";
                        break;
                    case 17:
                        petYears = "84";
                        break;
                    case 18:
                        petYears = "88";
                        break;
                    case 19:
                        petYears = "92";
                        break;
                    case 20:
                        petYears = "100";
                        break;
                    case 21:
                        petYears = "108";
                        break;
                    default:
                        break;
                }

                break;
            case 1: //pes
                switch (years) {
                    case 1:
                        petYears = "15.5";
                        break;
                    case 2:
                        petYears = "25";
                        break;
                    case 3:
                        petYears = "29";
                        break;
                    case 4:
                        petYears = "32";
                        break;
                    case 5:
                        petYears = "37";
                        break;
                    case 6:
                        petYears = "40";
                        break;
                    case 7:
                        petYears = "44";
                        break;
                    case 8:
                        petYears = "47";
                        break;
                    case 9:
                        petYears = "52";
                        break;
                    case 10:
                        petYears = "55";
                        break;
                    case 11:
                        petYears = "60";
                        break;
                    case 12:
                        petYears = "64";
                        break;
                    case 13:
                        petYears = "68";
                        break;
                    case 14:
                        petYears = "72";
                        break;
                    case 15:
                        petYears = "77";
                        break;
                    case 16:
                        petYears = "80";
                        break;
                    case 17:
                        petYears = "85";
                        break;
                    case 18:
                        petYears = "88";
                        break;
                    case 19:
                        petYears = "96";
                        break;
                    case 20:
                        petYears = "100";
                        break;
                    case 21:
                        petYears = "104";
                        break;
                    default:
                        break;
                }

                break;
            case 2: //zajac
                switch (years) {
                    case 1:
                        petYears = "20";
                        break;
                    case 2:
                        petYears = "28";
                        break;
                    case 3:
                        petYears = "36";
                        break;
                    case 4:
                        petYears = "44";
                        break;
                    case 5:
                        petYears = "52";
                        break;
                    case 6:
                        petYears = "60";
                        break;
                    case 7:
                        petYears = "68";
                        break;
                    case 8:
                        petYears = "76";
                        break;
                    case 9:
                        petYears = "84";
                        break;
                    case 10:
                        petYears = "92";
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        return petYears;
    }
}
