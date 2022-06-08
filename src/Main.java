import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение ");
        String input = scanner.nextLine();
        String output = "";

        output = calc(input);
        System.out.println("Результат = " + output);
    }

    public static String calc(String input) throws IOException {

        String output = "";

        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<String> symbols = new ArrayList<>();

        boolean firstNumberIsRoman = false;
        boolean secondNumberIsRoman = false;

        StringTokenizer numbersTrigger = new StringTokenizer(input, "+-*/ ");
        StringTokenizer symbolsTrigger = new StringTokenizer(input, "0123456789IVX ");

        while (numbersTrigger.hasMoreTokens()) {numbers.add(numbersTrigger.nextToken());}
        while (symbolsTrigger.hasMoreTokens()) {symbols.add(symbolsTrigger.nextToken());}


        for (String i: symbols) {if (i.equals(".") || i.equals(",")) throw new IOException("Выражение не должно содержать дробные знаки <.> или <,>");}
        for (String i: numbers) {if (i.equals(".") || i.equals(",")) throw new IOException("Выражение не должно содержать дробные знаки <.> или <,>");}

        for (String i: symbols) {if( !(i.equals("+") || i.equals("-") || i.equals("*") || i.equals("/"))) throw new IOException("Введена некорректная математическая операция");}
        if (symbols.size() != 1) {throw new IOException("Введите один математический знак (+, -, /, *) и два числа");}
        if (numbers.size() != 2) {throw new IOException("Строка не является математической операцией");}

        try {Integer.parseInt(numbers.get(0));}
        catch (NumberFormatException exception) {firstNumberIsRoman = true;}

        try {Integer.parseInt(numbers.get(1));}
        catch (NumberFormatException exception) {secondNumberIsRoman = true;}

        if (firstNumberIsRoman && secondNumberIsRoman) {
            int firstNumber = translateRomanToArab(numbers.get(0));
            int secondNumber = translateRomanToArab(numbers.get(1));
            if (firstNumber > 10 || firstNumber < 1 || secondNumber > 10 || secondNumber < 1) {throw new IOException("Входные числа должны быть в диапазоне [1;10]");}
            int result = doOperation(firstNumber, secondNumber, symbols.get(0));
            if (result <= 0) {throw new NumberFormatException("Результатом в римской системе может быть только число >= 1");}
            output = translateArabToRoman(result);
        }

        if (!firstNumberIsRoman && !secondNumberIsRoman) {
            int firstNumber = Integer.parseInt(numbers.get(0));
            int secondNumber = Integer.parseInt(numbers.get(1));
            if (firstNumber > 10 || firstNumber < 1 || secondNumber > 10 || secondNumber < 1) {throw new IOException("Входные числа должны быть в диапазоне [1;10]");}
            int result = doOperation(firstNumber, secondNumber, symbols.get(0));
            output = Integer.toString(result);
        }

        if (firstNumberIsRoman != secondNumberIsRoman) {throw new IOException("Введите числа из одной системы");}

        return output;
    }


    public static Integer doOperation(int a, int b, String action) throws IOException {

        int c = 0;

        switch (action) {
            case "+": c = a + b; break;
            case "-": c = a - b; break;
            case "/": c = a / b; break;
            case "*": c = a * b; break;
        }
        return c;
    }


    public static Integer translateRomanToArab(String number) throws IOException {

        int c = 0;

        switch (number) {
            case "I" : c = 1; break;
            case "II" : c = 2; break;
            case "III" : c = 3; break;
            case "IV" : c = 4; break;
            case "V" : c = 5; break;
            case "VI" : c = 6; break;
            case "VII" : c = 7; break;
            case "VIII" : c = 8; break;
            case "IX" : c = 9; break;
            case "X" : c = 10; break;
            default: throw new IOException("Неверный формат записи римского числа, введите число в диапазоне [I;X]");
        }
        return c;
    }


    public static String translateArabToRoman(int number) {

        String c = "";
        String cc = "";

        int[] cases = {100, 90, 80, 70, 60, 50, 40, 30, 20 ,10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        for (int i: cases) {
            if (number >= i) {
                switch (i) {
                    case 1: c = "I"; break;
                    case 2: c = "II"; break;
                    case 3: c = "III"; break;
                    case 4: c = "IV"; break;
                    case 5: c = "V"; break;
                    case 6: c = "VI"; break;
                    case 7: c = "VII"; break;
                    case 8: c = "VIII"; break;
                    case 9: c = "IX"; break;
                    case 10: c = "X"; break;
                    case 20: c = "XX"; break;
                    case 30: c = "XXX"; break;
                    case 40: c = "XL"; break;
                    case 50: c = "L"; break;
                    case 60: c = "LX"; break;
                    case 70: c = "LXX"; break;
                    case 80: c = "LXXX"; break;
                    case 90: c = "XC"; break;
                    case 100: c = "C"; break;
                }
                number = number - i;
                cc = cc + c;
            }
        }
        return cc;
    }
}
