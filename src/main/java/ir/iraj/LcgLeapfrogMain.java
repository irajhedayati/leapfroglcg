package ir.iraj;

import ir.iraj.rng.LcgLeapfrog;

import java.util.*;
import java.util.concurrent.*;

import static java.lang.System.exit;

public class LcgLeapfrogMain {

    public static void main(String[] args){

        if (args.length < 2 ) {
            System.out.println("Insufficient number of arguments");
            usage();
        }
        try {
            int buckets = Integer.parseInt(args[0]);
            int generators = Integer.parseInt(args[1]);
            int c = Integer.parseInt(args[2]);
            int a = Integer.parseInt(args[3]);
            int n = Integer.parseInt(args[4]);

            System.out.println("- Modulus: " + buckets);
            System.out.println("- Generators: " + generators);
            System.out.println("- Multiplier: " + a);
            System.out.println("- Increment: " + c);

            Map<Integer,Future<List<Integer>>> numbers = new HashMap<>(generators);
            Set<Integer> uniqueNumbers = new HashSet<>(n);

            ExecutorService executorService = Executors.newFixedThreadPool(generators);

            for (int i = 0; i < generators; i++) {
                int finalI = i;
                Callable<List<Integer>> aGenerator = () -> {
                    LcgLeapfrog lcg = new LcgLeapfrog(finalI + 1, buckets, a, c, generators);
                    List<Integer> integers = new ArrayList<>();
                    for (int j = 0; j < n; j++) {
                        int generated = lcg.next();
                        integers.add(generated);
                    }
                    return integers;
                };
                numbers.put(i, executorService.submit(aGenerator));
            }

            for (int i = 0; i < generators; i++) {
                System.out.println("Generator " + i + " : ");
                List<Integer> someNumbers = numbers.get(i).get();
                for (int j = 0; j < someNumbers.size(); j++) {
                    System.out.print(" " + someNumbers.get(j) + " ");
                    uniqueNumbers.add(someNumbers.get(j));

                }
                System.out.println();
            }

            System.out.println("Number of unique numbers: " + uniqueNumbers.size());

            System.out.println("List of unique numbers:");
            List<Integer> aList = new ArrayList<>(uniqueNumbers);
            aList.sort((o1, o2) -> o1 - o2);
            for (Integer integer : aList) {
                System.out.println(integer);
            }

            try {
                System.out.println("attempt to shutdown executor");
                executorService.shutdown();
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            }
            catch (InterruptedException e) {
                System.err.println("tasks interrupted");
            }
            finally {

                if (!executorService.isTerminated()) {
                    System.err.println("cancel non-finished tasks");
                }
                executorService.shutdownNow();
                System.out.println("shutdown finished");
            }




        }catch (NumberFormatException ex){
            usage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    private static void usage(){
        System.out.println("<m> <r> [a c] [n]");
        System.out.println("- m is modulus");
        System.out.println("- r is number of generators");
        System.out.println("- a is multiplier (automatically computed if not provided)");
        System.out.println("- c is increment (automatically computed if not provided");
        System.out.println("- n is total number of random numbers (default: m)");
        exit(1);
    }

}
