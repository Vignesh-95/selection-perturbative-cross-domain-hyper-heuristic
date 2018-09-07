import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import PersonnelScheduling.PersonnelScheduling;

public class MainMultiPointSearch
{
    public static void main (String [] args)
    {
        HyperHeuristic spshh;
        ProblemDomain problem;
        // Can change
        int seed = 1;

        spshh = new MultiPointSearchHyperHeuristic(seed++, 30, 3, 0.3);
        problem = new PersonnelScheduling(seed++);
        problem.loadInstance(2);
        spshh.setTimeLimit(100000);
        spshh.loadProblemDomain(problem);
        spshh.run();
        System.out.println(spshh.getBestSolutionValue());
    }
}
