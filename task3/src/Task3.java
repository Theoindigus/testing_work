import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Task3 {
    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Enter three paths");
            System.exit(0);
        }
        if(!checkFile(args[0]) || !checkFile(args[1]) || !checkFile(args[2])) {
            System.out.println("Files not found");
            System.exit(0);
        }
        File tests = new File(args[0]);
        File values = new File(args[1]);

        ObjectMapper mapper = new ObjectMapper();
        List<Test> testsList = null;
        List<Value> valuesList = null;
        try {
            var testsRoot = mapper.readTree(tests).get("tests").toString();
            var valuesRoot = mapper.readTree(values).get("values").toString();
            testsList = mapper.readValue(testsRoot, mapper.getTypeFactory().constructCollectionType(List.class, Test.class));
            valuesList = mapper.readValue(valuesRoot, mapper.getTypeFactory().constructCollectionType(List.class, Value.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Value value : valuesList)
            searchByIdAndSetValue(testsList, value.getId(), value.getValue());
        try {
            var root = mapper.createObjectNode().putPOJO("tests", testsList);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(args[2]), root);
        } catch (StreamWriteException e) {
            e.printStackTrace();
        } catch (DatabindException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean checkFile(String path) {
        File file = new File(path);
        return file.exists() && file.isFile() && file.canRead();
    }

    static boolean searchByIdAndSetValue(List<Test> tests, int id, String value) {
        boolean found = false;
        for(Test t : tests) {
            if(t.getId() == id) {
                t.setValue(value);
                found = true;
                break;
            }
        }
        if(!found) {
            for(Test t : tests) {
                if(t.getValues() != null && t.getValues().size() > 0) 
                    found = searchByIdAndSetValue(t.getValues(), id, value); // Рекурсивно ищем объект с искомым ID по всему дереву объектов
                if(found)
                    break;
            }
        }
        return found;
    }
}

class Test {
    private int id;
    private String title;
    private String value;
    private List<Test> values;

    public Test() { }

    public Test(int id, String title, String value, List<Test> values) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.values = values;
    }

    public int getId() { return id; }

    public String getTitle() { return title; }

    public String getValue() { return value; }

    public List<Test> getValues() { return values; }
    
    public void setId(int id) { this.id = id; }
    
    public void setTitle(String title) { this.title = title; }
    
    public void setValue(String value) { this.value = value; }
    
    public void setValues(List<Test> values) { this.values = values; }
    
    @Override
    public String toString() {
        return "Test [ " +
                "id = " + id +
                ", title = '" + title + "\'" +
                ", value = '" + value + "\'" +
                ", values size = " + (values != null ? values.size() : 0) +
                " ]";
    }
}

class Value {
    private int id;
    private String value;

    public Value() { }

    public Value(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() { return id; }
    
    public String getValue() { return value; }

    @Override
    public String toString() {
        return "Values [ " +
                "id = " + id +
                ", value = '" + value + "\'" +
                " ]";
    }
}