import solver.Solver;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.*;

public class Main {
    public static void main (String [] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        final int timeoutInSeconds = 30;

        try {
            Solver s = new Solver("resource/levels");
            while(s.hasNextLevel()) {
                Future<?> future = executor.submit(() -> {
                            try { s.solve(); }
                            catch (InterruptedException e) {
                                System.out.println("Execution was interrupted.\n");
                            }
                        });
                try { future.get(timeoutInSeconds, TimeUnit.SECONDS); }
                catch (TimeoutException e) {
                    System.out.println("Timeout occurred. Stopping execution.");
                    future.cancel(true);
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) { e.printStackTrace(); }
        catch (URISyntaxException e) { e.printStackTrace(); }
        finally { executor.shutdownNow(); }

    }
}