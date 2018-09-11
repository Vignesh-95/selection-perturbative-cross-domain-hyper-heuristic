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

        for (int i = 0; i < 1; i++)
        {
            spshh = new SinglePointSearchHyperHeuristic(1111, seed++);
            problem = new PersonnelScheduling(seed++);
            problem.loadInstance(5);
            spshh.setTimeLimit(600000);
            spshh.loadProblemDomain(problem);
            spshh.run();
            System.out.println(spshh.getBestSolutionValue());
        }

//        for (int i = 0; i < 30; i++)
//        {
//            spshh = new SinglePointSearchHyperHeuristic(, seed++);
//            problem = new BinPacking(seed++);
//            problem.loadInstance(0);
//            spshh.setTimeLimit(600000);
//            spshh.loadProblemDomain(problem);
//            spshh.run();
//            System.out.println(spshh.getBestSolutionValue());
//        }
//
//        for (int i = 0; i < 30; i++)
//        {
//            spshh = new SinglePointSearchHyperHeuristic(, seed++);
//            problem = new FlowShop(seed++);
//            problem.loadInstance(0);
//            spshh.setTimeLimit(600000);
//            spshh.loadProblemDomain(problem);
//            spshh.run();
//            System.out.println(spshh.getBestSolutionValue());
//        }
//
//        for (int i = 0; i < 30; i++)
//        {
//            spshh = new SinglePointSearchHyperHeuristic(, seed++);
//            problem = new SAT(seed++);
//            problem.loadInstance(0);
//            spshh.setTimeLimit(600000);
//            spshh.loadProblemDomain(problem);
//            spshh.run();
//            System.out.println(spshh.getBestSolutionValue());
//        }
    }
}
