import resource.ClassPathResourceLoader;
import resource.FilePathResourceLoader;
import resource.ResourceLoader;
import solver.Backtracking;
import solver.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.*;

public class Main {
    public static final int TIMEOUT_SECONDS = 20;
    static ExecutorService executor = Executors.newSingleThreadExecutor();

    public static void main (String [] args) {
        if (args.length == 0) {
            ResourceLoader rl = ClassPathResourceLoader.newInstance();
            executeAll("resource/levels", rl);
//            executeOne("resource/levels/04.txt", rl);
        } else {
            String path = args[0];
            File file = new File(path);

            if (file.exists()) {
                ResourceLoader rl = FilePathResourceLoader.newInstance();

                if (file.isDirectory()) executeAll(path, rl);
                else if (file.isFile()) executeOne(path, rl);
                else System.out.println("The path is neither a file nor a directory.");

            } else { System.out.println("The path does not exist."); }
        }
    }

    public static void executeAll(String classpathFolder, ResourceLoader rl) {
        try {
            Solver solver = new Solver(classpathFolder, new Backtracking(), rl);
            while(solver.hasNextLevel()) {
                Future<?> future = executor.submit(() -> {
                    try { solver.solve(); }
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

    public static void executeOne(String classpathFile, ResourceLoader rl) {
        Solver solver = new Solver(new Backtracking(), rl);
        Future<?> future = executor.submit(() -> {
            try { solver.solveOne(classpathFile); }
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