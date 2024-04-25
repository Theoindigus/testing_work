import java.util.ArrayList;
import java.util.List;

public class Task1 {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Enter two numbers");
            System.exit(0);
        }
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        // Получить путь можно без инициализации кругового массива
        List<Integer> path = new ArrayList<>();
        int element = 1; // Элемент, который является концом одного и началом следующего интервала
        path.add(1); // Первый элемент
        // Ограничим число интераций
        for(int i = 1; i < 1000; i++) {
            element += (m - 1);
            if(element > n)
                element -= n;
            if(element == 1) // Если текущий элемент равен первому, завершаем цикл
                break;
            path.add(element);
        }
        System.out.println(path.toString());
    }
}