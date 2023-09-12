package calculator;

import java.util.Scanner;

class Main {
    public static boolean isRoman = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(calc(scanner.nextLine()));

//        System.out.println(calc("10 * 10"));
//        System.out.println(calc("IV * IV"));
//        System.out.println(calc("-1 - 2"));
//        System.out.println(calc("I - V")); //т.к. в римской системе нет отрицательных чисел
//        System.out.println(calc("I + 1")); // т.к. используются одновременно разные системы счисления
//        System.out.println(calc(" 1  ")); // т.к. строка не является математической операцией
//        System.out.println(calc("1 + 2 + 3")); // т.к. формат математической операции не удовлетворяет заданию
    }

    public static String calc(String input) {

        input = input.replaceAll(" ", ""); // избавляемся от пробелов
        String[] values = input.split("[+-/*]");     // определим числа

        if (values.length > 2) {
            throw new RuntimeException("Формат математической операции не удовлетворяет заданию");
        } else if (values.length == 1) {
            throw new RuntimeException("Строка не является заданной математической операцией");
        }

        char sign = input.substring(values[0].length()).charAt(0); // определение ариф. знака

        if (Converter.isRoman(values[0]) != Converter.isRoman(values[1])) { // проверка на индентичность видов чисел

            throw new RuntimeException("Используются одновременно разные системы счисления");
        } else {
            isRoman = Converter.isRoman(values[0]);     // проверка на римские цифры
        }

        int value1, value2, result = 0;   // значения для мат. операции

        if (isRoman) {
            value1 = Integer.parseInt(Converter.fromRomanToArabic(values[0])); // перевод римских в арабские
            value2 = Integer.parseInt(Converter.fromRomanToArabic(values[1])); // перевод римских в арабские
        } else {
            value1 = Integer.parseInt(values[0]);
            value2 = Integer.parseInt(values[1]);
        }

        if (value1 <= 0 | value2 <= 0 | value1 > 10 | value2 > 10) {
            throw new RuntimeException("Значения меньше 0 или больше 10");
        }
        switch (sign) {
            case '+' -> result = add(value1, value2);
            case '-' -> result = minus(value1, value2);
            case '/' -> result = div(value1, value2);
            case '*' -> result = multiply(value1, value2);
        }

        if (isRoman) {
            if (result < 0) throw new RuntimeException("В римской системе нет отрицательных чисел");
            if (result == 0) throw new RuntimeException("В римской системе нет нуля");

            return Converter.fromArabicToRoman(String.valueOf(result));
        }

        return String.valueOf(result);
    }

    public static int add(int a, int b) {
        return a + b;
    } // сумма

    public static int minus(int a, int b) {
        return a - b;
    } // разность

    public static int div(int a, int b) {
        return (int) a / b;
    } // деление

    public static int multiply(int a, int b) {
        return a * b;
    } // умножение
}

class Converter {
    static final String[] roman = {"C", "XC", "LXXX", "LXX", "LX", "L", "XL", "XXX",
            "XX", "X", "IX", "VIII", "VII", "VI", "V", "IV", "III", "II", "I"};
    static final int[] arabic = {100, 90, 80, 70, 60, 50, 40, 30, 20, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

    public static boolean isRoman(String input) { // проверка на римские цифры
        for (String val : roman) {
            if (val.equals(input)) {
                return true;
            }
        }
        return false;
    }

    public static String fromRomanToArabic(String input) { // перевод из римских в арабские
        int result = 0;
        while (!input.isEmpty()) {
            for (int i = 0; i < roman.length; i++) {
                if ((roman[i].length() <= input.length())
                        && (roman[i].equals(input.substring(0, roman[i].length())))) {
                    result += arabic[i];
                    input = input.substring(roman[i].length());
                }
            }
        }
        return String.valueOf(result);
    }

    public static String fromArabicToRoman(String input) { // перевод из арабских в римские
        String result = "";

        int length = input.length();

        if (length == 3) result = "C";
        else if (length == 2) {

            int tens = Integer.parseInt(input.charAt(0) + "0");
            int ones = Integer.parseInt(input.substring(1, 2));

            for (int i = 0; i < arabic.length; i++) {
                if (arabic[i] == tens) {
                    result += roman[i];
                    break;
                }
            }
            for (int i = 0; i < arabic.length; i++) {
                if (arabic[i] == ones) {
                    result += roman[i];
                    break;
                }
            }
        } else if (length == 1) {
            for (int i = 0; i < arabic.length; i++) {
                if (arabic[i] == Integer.parseInt(input)) {
                    result += roman[i];
                    break;
                }
            }
        }
        return result;
    }
}

