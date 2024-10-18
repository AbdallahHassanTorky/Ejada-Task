import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataLoader {
    public static List<Object[]> loadLoginData() throws IOException {
        List<Object[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("testData.csv"))) {
            // Skip the header line if necessary
            reader.readLine(); // Uncomment if you have a header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                data.add(new Object[]{values[0], values[1], values.length > 2 ? values[2] : null});
            }
        }
        return data;
    }
}
