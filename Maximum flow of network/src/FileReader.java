import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class FileReader{
    public String[] file(String fileName){
        try{
            File data = new File(fileName);
            String[] list =new String[10000];
            Scanner sc = new Scanner(data);
            String details = sc.nextLine();
            while (sc.hasNext()) {
                details = details + "\n" + sc.nextLine();
            }
            String s="\n";
            list=details.split(s);
            return list;
        } catch (FileNotFoundException e) {
            System.out.println("Oops!! Something went wrong.");
            return null;
        }
    }
    public List<Edge> list(String[] list) {
        String[] each = new String[4];
        String j =" ";
        List<Edge> edges= new ArrayList<Edge>();
        for (int i = 1; i < list.length; i++) {
            each = list[i].split(j);
            edges.add(new Edge(Integer.parseInt(each[0]), Integer.parseInt(each[1]), Integer.parseInt(each[2])));
        }
        return edges;
    }
}
