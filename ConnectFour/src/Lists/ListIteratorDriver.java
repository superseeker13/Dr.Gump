package Lists;
import java.util.Iterator;
import java.util.Scanner;

public class ListIteratorDriver {

    public static void main(String[] args) {
        int count;
        AList<String> names = new AList<>();
        Scanner in = new Scanner(System.in);
        System.out.print("Enter number of names: ");
        count = in.nextInt();
        in.nextLine();
        for (int i = 1; i <= count; i++) {
            System.out.printf("Enter name %d: ", i);
            names.add(in.nextLine());
        }
        
        System.out.println("\nYou entered the following names: ");
        Iterator<String> i = names.iterator();
        while(i.hasNext()) {
            System.out.println(i.next());
            i.remove();
        }
    }    
}
