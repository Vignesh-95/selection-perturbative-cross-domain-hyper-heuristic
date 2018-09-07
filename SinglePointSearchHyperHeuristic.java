import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.ArrayList;

public class SinglePointSearchHyperHeuristic extends HyperHeuristic
{
    public SinglePointSearchHyperHeuristic(double opFitness, long seed)
    {
        super(seed);
        optimalFitnessValue = opFitness;
    }
    
    private double optimalFitnessValue;
    
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
        double[] utility = new double[totalHeuristics];
        int heuristicIndex;
        double initialFitness, currentFitness=0.0, tempFitness, waterLevel=0.0;

        if (!hasTimeExpired())
        {
            initialFitness = problemDomain.getFunctionValue(0);
            currentFitness = initialFitness;
            waterLevel = initialFitness;
        }

        while (!hasTimeExpired())
        {
           heuristicIndex = getIndexOfLargest(utility, totalHeuristics);
           tempFitness = problemDomain.applyHeuristic(heuristicIndex, 0, 1);
           if (tempFitness < currentFitness)
           {
               utility[heuristicIndex] += 1;
               problemDomain.copySolution(1, 0);
               currentFitness = tempFitness;
           }
           else
           {
               utility[heuristicIndex] /= 2;
               if (tempFitness < waterLevel)
               {
                   problemDomain.copySolution(1, 0);
                   currentFitness = tempFitness;
               }

               waterLevel -= (waterLevel - optimalFitnessValue)/getElapsedTime();
           }
        }
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