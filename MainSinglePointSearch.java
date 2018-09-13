import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import PersonnelScheduling.*;
import BinPacking.*;
import FlowShop.*;
import SAT.*;


public class MainSinglePointSearch
{
    public static void main (String [] args)
    {
        HyperHeuristic spshh;
        ProblemDomain problem;
        // Can change
        int seed = 1;
        double average = 0.0;
        double best = Double.MAX_VALUE;

//        for (int i = 0; i < 30; i++)
//        {
//            spshh = new SinglePointSearchHyperHeuristic(seed++);
//            problem = new PersonnelScheduling(seed++);
//            problem.loadInstance(5);
//            spshh.setTimeLimit(600000);
//            spshh.loadProblemDomain(problem);
//            spshh.run();
//            double current = spshh.getBestSolutionValue();
//            average += current;
//            if (current < best)
//            {
//                best = current;
//            }
//            System.out.println(spshh.getBestSolutionValue());
//        }
//        System.out.println("Average: " + average/30.0);
//        System.out.println("Best: " + best);

        System.out.println();

        seed = 1;
        average = 0.0;
        best = Double.MAX_VALUE;

        for (int i = 0; i < 30; i++)
        {
            spshh = new SinglePointSearchHyperHeuristic(seed++);
            problem = new BinPacking(seed++);
            problem.loadInstance(7);
            spshh.setTimeLimit(600000);
            spshh.loadProblemDomain(problem);
            spshh.run();
            double current = spshh.getBestSolutionValue();
            average += current;
            if (current < best)
            {
                best = current;
            }
            System.out.println(spshh.getBestSolutionValue());
        }
        System.out.println("Average: " + average/30.0);
        System.out.println("Best: " + best);

        System.out.println();

        seed = 1;
        average = 0.0;
        best = Double.MAX_VALUE;

        for (int i = 0; i < 30; i++)
        {
            spshh = new SinglePointSearchHyperHeuristic(seed++);
            problem = new FlowShop(seed++);
            problem.loadInstance(1);
            spshh.setTimeLimit(600000);
            spshh.loadProblemDomain(problem);
            spshh.run();
            double current = spshh.getBestSolutionValue();
            average += current;
            if (current < best)
            {
                best = current;
            }
            System.out.println(spshh.getBestSolutionValue());
        }
        System.out.println("Average: " + average/30.0);
        System.out.println("Best: " + best);

        System.out.println();

        seed = 1;
        average = 0.0;
        best = Double.MAX_VALUE;

        for (int i = 0; i < 30; i++)
        {
            spshh = new SinglePointSearchHyperHeuristic(seed++);
            problem = new SAT(seed++);
            problem.loadInstance(3);
            spshh.setTimeLimit(600000);
            spshh.loadProblemDomain(problem);
            spshh.run();
            double current = spshh.getBestSolutionValue();
            average += current;
            if (current < best)
            {
                best = current;
            }
            System.out.println(spshh.getBestSolutionValue());
        }
        System.out.println("Average: " + average/30.0);
        System.out.println("Best: " + best);
    }
}
