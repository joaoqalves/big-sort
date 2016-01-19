import net.joaoqalves.sort.ExternalSort;

public class Main {

    public static void main(String[] args) {

        if(args.length < 2) {
            System.err.println("Two arguments. Input and Output file. (e.g., mybigfile.txt sorted.out");
        }

        ExternalSort.sort(args[0], args[1]);
    }
}
