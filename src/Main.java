import solver.Solver;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main (String [] args) {
        try {
            Solver s = new Solver("resource/levels");
            while(s.hasNextLevel()) {
                try {
                    s.solve();
                } catch(Exception e) {}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}