import solver.Solver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.*;

public class Main {
    public static final int TIMEOUT_SECONDS = 500;
    static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main (String [] args) {
//        executeAll("resource/levels");

        executeOne("resource/levels/09.txt");
    }

    public static void executeAll(String classpathFolder) {
        try {
            Solver s = new Solver(classpathFolder);
            while(s.hasNextLevel()) {
                Future<?> future = executor.submit(() -> {
                    try { s.solve(); }
                    catch (InterruptedException e) {
                        System.out.println("Execution was interrupted.\n");
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                });

                try { future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS); }
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

    public static void executeOne(String classpathFile) {
        Future<?> future = executor.submit(() -> {
            try { Solver.solveOne(classpathFile); }
            catch (InterruptedException e) {
                System.out.println("Execution was interrupted.\n");
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        });

        try { future.get(TIMEOUT_SECONDS, TimeUnit.SECONDS); }
        catch (TimeoutException e) {
            System.out.println("Timeout occurred. Stopping execution.");
            future.cancel(true);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally { executor.shutdownNow(); }
    }
}