import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.ArrayList;


public class MultiPointSearchHyperHeuristic extends HyperHeuristic
{
    int populationSize, tournamentSize, heuristics_count, maxChromosomeLength, minChromosomeLength;
    double mutationProbability;
    ProblemDomain problemDomain;
    ArrayList<ArrayList<Integer>> population;
    int work = 0;

    public MultiPointSearchHyperHeuristic(int seed, int pSize, int tSize, double mP)
    {
        super(seed);
        populationSize = pSize;
        tournamentSize = tSize;
        mutationProbability = mP;
        problemDomain = null;
        heuristics_count = 0;
        maxChromosomeLength = 0;
        minChromosomeLength = 0;
        population = new ArrayList<ArrayList<Integer>>();
    }

    public String toString()
    {
        return "Multi Point Search Hyper-Heuristic: Genetic Algorithm for Perturbative Low-Level Heuristics";
    }

    public void solve (ProblemDomain problemDomain)
    {
        this.problemDomain = problemDomain;
        heuristics_count = this.problemDomain.getNumberOfHeuristics();
        minChromosomeLength = 3;
        maxChromosomeLength = 10;
        this.problemDomain.initialiseSolution(0);
        for (int counter = 0; counter < populationSize; counter++)
        {
            ArrayList<Integer> chromosome = new ArrayList<Integer>();
            int length = rng.nextInt(maxChromosomeLength - minChromosomeLength + 1) + minChromosomeLength;

            for (int i = 0; i < length; i++)
            {
                chromosome.add(rng.nextInt(heuristics_count));
            }

            population.add(chromosome);
        }

        evolve();
    }

    public void evolve()
    {
        while(!hasTimeExpired() && work != 100)
        {
            int parents[] = new int[2];
            selectParents(parents);
            crossover(parents);
            work++;
        }

        System.out.println();
        for (int counter = 0; counter < populationSize; counter++)
        {
            int chromosomeLength = population.get(counter).size();
            for (int next = 0; next < chromosomeLength; next++)
            {
                System.out.print(population.get(counter).get(next) + " ");
            }
        }
        System.out.println();
    }

    public double evaluate_heuristic(ArrayList<Integer> chromosome)
    {
        int length = chromosome.size();
        double currentFitness = problemDomain.getFunctionValue(0);
        double tempFitness;
        double startFitness = currentFitness;
        for (int counter = 0; counter < length; counter++)
        {
            tempFitness = problemDomain.applyHeuristic(chromosome.get(counter), 0, 1);
            while (tempFitness < currentFitness)
            {
                work = 0;
                problemDomain.copySolution(1,0);
                currentFitness = tempFitness;
                hasTimeExpired();
                tempFitness = problemDomain.applyHeuristic(chromosome.get(counter), 0, 1);
            }
        }
        return currentFitness-startFitness;
    }

    public void selectParents(int parents[])
    {
        int[] tournament = new int[tournamentSize];
        ArrayList<Integer> values = new ArrayList<Integer>();
        int valueLength = 0;

        for (int counter = 0; counter < tournamentSize; counter++)
        {
            int random = rng.nextInt(populationSize);

            while (valueLength != 0 && values.indexOf(random) != -1)
            {
                random = rng.nextInt(populationSize);
            }

            values.add(random);
            valueLength++;

            tournament[counter] = random;
        }

        int parent = 0;
        int parentCounter = 0;
        double min = Double.MAX_VALUE;

        for (int counter = 0; counter < tournamentSize; counter++)
        {
            double fit = evaluate_heuristic(population.get(tournament[counter]));
            if (fit < min)
            {
                min = fit;
                parent = tournament[counter];
                parentCounter = counter;
            }
        }

        parents[0] = parent;

        int parent2 = 0;
        min = Double.MAX_VALUE;
        for (int counter = 0; counter < tournamentSize; counter++)
        {
            if (counter != parentCounter)
            {
                double fit = evaluate_heuristic(population.get(tournament[counter]));
                if (fit < min)
                {
                    min = fit;
                    parent2 = tournament[counter];
                }
            }
        }

        parents[1] = parent2;
    }

    public void crossover(int parents[])
    {
        int random = rng.nextInt(population.get(parents[0]).size());
        int random2 = rng.nextInt(population.get(parents[1]).size());

        ArrayList<Integer> temp1 = new ArrayList<Integer>();
        ArrayList<Integer> temp2 = new ArrayList<Integer>();

        for (int it = 0; it <= random; it++)
        {
            temp1.add(population.get(parents[0]).get(it));
        }

        for (int it = 0; it <= random2; it++)
        {
            temp2.add(population.get(parents[1]).get(it));
        }

        for (int it = random2 + 1; it < population.get(parents[1]).size(); it++)
        {
            temp1.add(population.get(parents[1]).get(it));
        }

        for (int it = random + 1; it < population.get(parents[0]).size(); it++)
        {
            temp2.add(population.get(parents[0]).get(it));
        }

        double r;
        int r2;

        int oSize = temp1.size();
        for (int c = 0; c < oSize; c++)
        {
            r = rng.nextDouble();
            if (r < mutationProbability)
            {
                r2 = rng.nextInt(heuristics_count);
                while (r2 == temp1.get(c))
                {
                    r2 = rng.nextInt(heuristics_count);
                }

                temp1.set(c, r2);
            }
        }

        int o2Size = temp2.size();
        for (int c = 0; c < o2Size; c++)
        {
            r = rng.nextDouble();
            if (r < mutationProbability)
            {
                r2 = rng.nextInt(heuristics_count);
                while (r2 == temp2.get(c))
                {
                    r2 = rng.nextInt(heuristics_count);
                }

                temp2.set(c, r2);
            }
        }

        int weakest = 0;
        double weakestFitness = evaluate_heuristic(population.get(weakest));
        for (int iterator = 1; iterator < populationSize; iterator++)
        {
            double tempFitness = evaluate_heuristic(population.get(iterator));
            if (tempFitness < weakestFitness)
            {
                weakest = iterator;
                weakestFitness = tempFitness;
            }
        }

        double oFitness = evaluate_heuristic(temp1);
        double o2Fitness = evaluate_heuristic(temp2);

        if (oFitness < o2Fitness)
        {
            if (oFitness < weakestFitness)
            {
                population.set(weakest, temp1);
            }
        }
        else
        {
            if (o2Fitness < weakestFitness)
            {
                population.set(weakest, temp2);
            }
        }
    }
}