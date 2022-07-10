import java.util.Scanner;

class WarmUp{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str, newstr = "";
        int first, second, third;
        

        System.out.print("Введите строку: ");
        str = scanner.nextLine();
        scanner.close();
        newstr = new StringBuilder(str).reverse().toString();

        first  = getMillis(str, 1000);
        second = getMillis(str, 10000);
        third  = getMillis(str, 100000);

        System.out.printf("%s %s %d %d %d", str, newstr, first, second, third);
    }
    public static int getMillis(String str, int times){
        long start = System.currentTimeMillis();
        @SuppressWarnings("unused")
        String newstr = "";
        for (int i = 0; i < times; i++)
            newstr = new StringBuilder(str).reverse().toString();
        return (int)(System.currentTimeMillis() - start);
    }
}