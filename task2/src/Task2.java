import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Task2 {
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Enter two paths");
            System.exit(0); 
        }
        String circlePath = args[0];
        String pointsPath = args[1];
        if(!checkFile(circlePath) || !checkFile(pointsPath)) {
            System.out.println("Files not found");
            System.exit(0);
        }
        var circleData = getData(circlePath); // Должно быть три числа через пробелы
        if(circleData == null) {
            System.out.println("Need a three parameters for circle (coordinates and radius)");
            System.exit(0);
        }
        var pointsData = getData(pointsPath); // Любое количество пар чисел через пробел
        if(pointsData == null) {
            System.out.println("Need a points");
            System.exit(0);
        }
        float[] circle = getCircle(circleData.get(0));
        float[][] points = getPoints(pointsData);
        for(int i = 0; i < points.length; i++) {
            var x = points[i][0] - circle[0];
            var y = points[i][1] - circle[1];
            var r = Math.sqrt(x * x + y * y); // Определяем модуль вектора из центра окружности к точке
            if(r < circle[2])
                System.out.println("1");
            else if(r == circle[2])
                System.out.println("0");
            else
                System.out.println("2");
        }
    }

    static boolean checkFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.canRead();
    }

    static List<String> getData(String path) {
        List<String> data = null;
        try(FileReader fr = new FileReader(path)) { 
            data = new BufferedReader(fr).lines().collect(Collectors.toList()); 
        }
        catch(IOException ex){ 
            System.out.println(ex.getMessage()); 
        }
        return data;
    }

    static float[] getCircle(String circle) {
        String[] circleData = circle.split(" ");
        if(circleData.length != 3) {
            System.out.println("Need a three parameters for circle (coordinates and radius)");
            System.exit(0);
        }
        float[] circleArray = new float[3];
        for(int i = 0; i < 3; i++)
            circleArray[i] = Float.parseFloat(circleData[i]);
        return circleArray;
    }

    static float[][] getPoints(List<String> points) {
        float[][] pointsArray = new float[points.size()][2];
        for(int i = 0; i < points.size(); i++) {
            var s = points.get(i).split(" ");
            if(s.length != 2) {
                System.out.println("Need two coordinates for point");
                System.exit(0);
            }
            pointsArray[i][0] = Float.parseFloat(s[0]);
            pointsArray[i][1] = Float.parseFloat(s[1]);
        }
        return pointsArray;
    }
}