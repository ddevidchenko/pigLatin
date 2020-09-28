package dmde;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        String fileName = args[0];
        PigLatinTextConverter pigLatinTextConverter = new PigLatinTextConverter();
        try (Stream<String> stream = Files.lines(Paths.get(fileName));
             PrintWriter pw = new PrintWriter("output.txt", "UTF-8")) {

            stream.map(pigLatinTextConverter::convert)
                    .forEachOrdered(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
