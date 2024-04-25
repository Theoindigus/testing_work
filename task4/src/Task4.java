import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Task4 {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Enter one path");
            System.exit(0);
        }
        if(!checkFile(args[0])) {
            System.out.println("File not found");
            System.exit(0);
        }
        List<String> data = null;
        try(FileReader fr = new FileReader(args[0])) {
            data = new BufferedReader(fr).lines().collect(Collectors.toList()); 
        }
        catch(IOException ex){ 
            System.out.println(ex.getMessage()); 
        }
        int[] nums = new int[data.size()];
        // Для приведения всех элементов массива к одному числу, нужно определиться, какое это будет число
        // ПУсть это будет среднее арифметическое
        int avg = 0;
        for(int i = 0; i < data.size(); i++) {
            var s = data.get(i);
            nums[i] = Integer.parseInt(s);
            avg += nums[i];
        }
        avg /= data.size();
        // Тогда минимальное количество ходов для приведения всех элементов к среднему значению -
        // сумма разностей каждого элемента и среднего значения
        int minMoves = 0;
        for(int i = 0; i < nums.length; i++) 
            minMoves += Math.abs(nums[i] - avg);
        System.out.println(minMoves);
    }

    static boolean checkFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.canRead();
    }

}