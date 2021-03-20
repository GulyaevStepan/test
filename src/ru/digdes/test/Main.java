package ru.digdes.test;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String userText = scanner.nextLine();

        if (validity(userText)) {
            System.out.println(reed(userText));
        }
    }

    /*
     * Метод проверяет каждый символ по очереди.
     * Если попадается недопустимый - вернет false.
     * Если весь цикл прошёл успешно - true.
     * */
    static boolean validity(String text){
        StringBuilder builder = new StringBuilder(text);
        int count = 0;

        for (int i = 0; i < builder.length(); i++) {
            int key = (int) builder.charAt(i);
            //Символы латинского алфавита (заглавные и строчные)
            if ((65 <= key && key <= 90) || (97 <= key && key <= 122)) {
                continue;
            }
            //Цифры (числа) и скобка [
            else if (48 <= key && key <= 57) {
                while (48 <= key && key <= 57 && ++i < builder.length()) {
                    key = (int) builder.charAt(i);
                }
                if (key == 91) {
                    ++count;
                    continue;
                } else {
                    return false;
                }
            }
            //Скобка ]
            else if (key == 93 & count > 0) {
                --count;
                continue;
            }
            //Если попалось что-то другое, то false
            else {
                return false;
            }
        }
        //Все ли скобки закрыты
        return count == 0;
    }

    /*
     * Входную строку можно представить в виде: "abc3[abc]abc" == "abc" + "3[abc]" + "abc"
     * Метод распаковывает строку по частям.
     * */
    static String reed(String text) {
        StringBuilder builder = new StringBuilder(text);
        String result = null;

        int firstIndex = builder.indexOf("[");
        int lastIndex = builder.lastIndexOf("]");
        //Если строка вида "abcabc"
        if (firstIndex == -1 && lastIndex == -1){
            return text;
        }
        int number = 0;
        int key;
        do {
            if (firstIndex-1-number < 0){
                break;
            }
            number = number + 1;
            char d = builder.charAt(firstIndex-1-number);
            key = (int) d;

        } while (key >= 48 && key <= 57);
        //Первая часть входной строки
        if (firstIndex > 1) {
            result = builder.substring(0, firstIndex-number);
        }
        //Вторая часть входной строки, рекурсия
        int count = Integer.parseInt(String.valueOf(builder.substring(firstIndex-number,firstIndex)));
        for (int i = 0; i < count; i++) {
            result = result + reed(builder.substring(firstIndex+1, lastIndex));
        }
        //Третья часть входной строки
        if (lastIndex + 1 < builder.length()) {
            result = result + builder.substring(lastIndex+1, builder.length());
        }
        return result;
    }

    /*
     * Этот метод тоже проверяет на валидность строку,
     * но в нём есть ограничения, не указанные в задании.
     * Нужужны они или нет - решать Вам.
     * 1. Недопустимы пустые скобки, пример: "abc3[]abc"
     * 2. недопустимы числа вида 0012 и число 0
     * */
    static boolean newValidity(String text){
        StringBuilder builder = new StringBuilder(text);
        int count = 0;

        for (int i = 0; i < builder.length(); i++) {
            int key = (int) builder.charAt(i);
            if ((65 <= key && key <= 90) || (97 <= key && key <= 122)) {
                continue;
            }
            else if (49 <= key && key <= 57) {
                while (48 <= key && key <= 57 && ++i < builder.length()) {
                    key = (int) builder.charAt(i);
                }
                if (key == 91) {
                    ++count;
                    continue;
                } else {
                    return false;
                }
            }
            else if (key == 93 && count > 0 && (int) builder.charAt(i - 1) != 91) {
                --count;
                continue;
            }
            else {
                return false;
            }
        }
        return count == 0;
    }
}
