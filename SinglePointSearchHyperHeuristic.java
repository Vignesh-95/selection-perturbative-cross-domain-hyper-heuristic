import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.ArrayList;

public class SinglePointSearchHyperHeuristic extends HyperHeuristic
{
    public SinglePointSearchHyperHeuristic(long seed)
    {
        super(seed);
    }
    
    public String toString()
    {
        return "Single Point Search Hyper-Heuristic\nHeuristic Selection Method: Reinforcement Learning" +
                "\nMove Acceptance Method: Great Deluge";
    }

    public void solve (ProblemDomain problemDomain)
    {
        // Initialisation
        problemDomain.initialiseSolution(0);
        int totalHeuristics = problemDomain.getNumberOfHeuristics();
        double utilityLowerBound = 0, utilityUpperBound = totalHeuristics * 3;
        double[] utility = new double[totalHeuristics];
        double initialValue = utilityUpperBound * 0.65;
        for (int counter = 0; counter < totalHeuristics; counter++)
        {
            utility[counter] = initialValue;
        }

        int heuristicIndex;
        double initialFitness, currentFitness=0.0, tempFitness;
        int noChange = 0;

        if (!hasTimeExpired())
        {
            initialFitness = problemDomain.getFunctionValue(0);
            currentFitness = initialFitness;
            // System.out.println("Fitness: " + currentFitness);
        }

        while (!hasTimeExpired() && noChange != 200)
        {
           heuristicIndex = getIndexOfLargest(utility, totalHeuristics);
           tempFitness = problemDomain.applyHeuristic(heuristicIndex, 0, 1);
           if (tempFitness < currentFitness)
           {
               noChange = 0;
               utility[heuristicIndex] += 1;
               if (utility[heuristicIndex] > utilityUpperBound)
               {
                   utility[heuristicIndex] = utilityUpperBound * 0.9;
               }

               problemDomain.copySolution(1, 0);
               currentFitness = tempFitness;
           }
           else
           {
               noChange++;
               double elapsedTime = getElapsedTime();
               double timeLimit = getTimeLimit();
               // System.out.println("Elapsed Time: " + elapsedTime + "\t\tTime Limit: " + timeLimit);
               if (elapsedTime < 0.2 * timeLimit)
               {
                   utility[heuristicIndex] -= 1;
               }
               else if (elapsedTime < 0.8 * timeLimit)
               {
                   utility[heuristicIndex] /= 2;
               }
               else
               {
                   utility[heuristicIndex] = Math.sqrt(utility[heuristicIndex]);
               }

               if (utility[heuristicIndex] < utilityLowerBound)
               {
                   utility[heuristicIndex] = utilityLowerBound + utilityUpperBound * 0.1;
               }
           }
           // System.out.println("Fitness: " + currentFitness);
        }

        System.out.print("Utility Values:\t[");
        for (int count = 0; count < totalHeuristics; count++)
        {
            System.out.print(utility[count] + " ");
        }
        System.out.println("]");
        System.out.println("Utility Upper Bound: " + utilityUpperBound +
                "\t\tUtility Lower Bound: " + utilityLowerBound);
    }

    private int getIndexOfLargest(double[] array, int arrayLength)
    {
        ArrayList<Integer> largest = new ArrayList<Integer>();
        largest.add(0);
        for (int i = 1; i < arrayLength; i++)
        {
            if (array[i] > array[largest.get(0)])
            {
                largest.clear();
                largest.add(i);
            }
            else if (array[i] == array[largest.get(0)])
            {
                largest.add(i);
            }
        }

        return largest.get(rng.nextInt(largest.size())); // position of the first largest found
    }
}