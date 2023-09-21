package demoted.spigot.wolfcore;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionEffectType;

public class utils {
    public static List<String> intList = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0");
    public static List<String> validEffects = arrayToStringArray(PotionEffectType.values());
    public static List<String> validEntityTypes = arrayToStringArray(EntityType.values());

    public static List<String> arrayToStringArray(Object[] array) {
        List<String> returnArray = new ArrayList<>();
        for (Object object : array) {
            returnArray.add(object.toString());
        }
        return returnArray;
    }

    public static List<String> arrayToStringArray(List<Object> array) {
        List<String> returnArray = new ArrayList<>();
        for (Object object : array) {
            returnArray.add(object.toString());
        }
        return returnArray;
    }
    public static List<String> listFiles(File file) {
        File[] files = file.listFiles();
        List<String> returnArray = new ArrayList<>();
        for (File object : files) {
            returnArray.add(object.getName());
        }
        return returnArray;
    }

    public static String stringAllowedValues(String string, String def, List<String> validOptions) {
        if (string == null)
            return def;
        if (validOptions.contains(string))
            return string;
        return def;
    }

    public static String NullCheck(String string, String def) {
        if (string == null)
            return def;
        return string;
    }

    public static Number NullCheck(Number number, Number def) {
        if (number == null)
            return def;
        return number;
    }

    public static Double parseNumber(String number, Double def) {
        if (number == null)
            return def;
        Double returnNumber;
        try {
            returnNumber = Double.parseDouble(number);
        } catch (Exception e) {
            returnNumber = def;
        }
        return returnNumber;
    }

    public static Integer parseNumber(String number, Integer def) {
        if (number == null)
            return def;
        Integer returnNumber;
        try {
            returnNumber = Integer.parseInt(number);
        } catch (Exception e) {
            returnNumber = def;
        }
        return returnNumber;
    }

    public String toRoman(int numeral) {
        String result = "";
        while (numeral > 0) {
            if (numeral >= 100) {
                numeral = numeral - 100;
                result = result + "C";
            } else if (numeral >= 90) {
                numeral = numeral - 90;
                result = result + "XC";
            } else if (numeral >= 50) {
                numeral = numeral - 50;
                result = result + "L";
            } else if (numeral >= 40) {
                numeral = numeral - 40;
                result = result + "XL";
            } else if (numeral >= 10) {
                numeral = numeral - 10;
                result = result + "X";
            } else if (numeral >= 9) {
                numeral = numeral - 9;
                result = result + "IX";
            } else if (numeral >= 5) {
                numeral = numeral - 5;
                result = result + "V";
            } else if (numeral >= 4) {
                numeral = numeral - 4;
                result = result + "IV";
            } else if (numeral >= 1) {
                numeral = numeral - 1;
                result = result + "I";
            } else {
                numeral = numeral - 1;
            }
        }
        return result;
    }

    public static returnValue<File> getFile(File[] files, String string) {
        for (File file : files) {
            if (file.getName().equalsIgnoreCase(string)) return new returnValue<File>(file, true);
        }
        return new returnValue<File>(files[0], false);
    }
}
