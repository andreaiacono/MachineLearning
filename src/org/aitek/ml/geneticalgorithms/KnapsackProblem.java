package org.aitek.ml.geneticalgorithms;

import jenes.GenerationEventListener;
import jenes.GeneticAlgorithm;
import jenes.algorithms.SimpleGA;
import jenes.chromosome.BooleanChromosome;
import jenes.population.Individual;
import jenes.population.Population;
import jenes.population.Population.Statistics;

public class KnapsackProblem {

	private static int POPULATION_SIZE = 5;
	private static int MAX_GENERATIONS = 50;

	private final KnapsackGeneticAlgorithm algorithm;
	private final double[] values;
	private final double[] weights;

	public KnapsackProblem(int numberOfItems, int maxWeigth, final double[] values, final double[] weights) {

		this.values = values;
		this.weights = weights;

		// creates the population of chromosomes
		Individual<BooleanChromosome> individual = new Individual<BooleanChromosome>(new BooleanChromosome(numberOfItems));
		Population<BooleanChromosome> population = new Population<BooleanChromosome>(individual, POPULATION_SIZE);

		algorithm = new KnapsackGeneticAlgorithm(population, maxWeigth);
		algorithm.addGenerationEventListener(new GenerationEventListener<BooleanChromosome>() {

			@Override
			public void onGeneration(GeneticAlgorithm<BooleanChromosome> ga, long time) {

				Statistics stats = ga.getCurrentPopulation().getStatistics();
				GeneticAlgorithm.Statistics algostats = ga.getStatistics();

				System.out.println("Generation #" + ga.getGeneration() + "\tFittest chromosome " + stats.getLegalHighestIndividual() + " (weight=" + getKnapsackSum((BooleanChromosome) stats.getLegalHighestIndividual().getChromosome(), weights) + ")");
			}
		});
	}

	public void evolve() {

		algorithm.evolve();
	}

	class KnapsackGeneticAlgorithm extends SimpleGA<BooleanChromosome> {

		private final int maxWeight;

		public KnapsackGeneticAlgorithm(Population<BooleanChromosome> population, int maxWeight) {

			super(population, MAX_GENERATIONS);
			this.maxWeight = maxWeight;
			this.setBiggerIsBetter(true);
		}

		@Override
		public void evaluateIndividual(Individual<BooleanChromosome> individual) {

			BooleanChromosome chromosome = individual.getChromosome();

			// computes the value and the weight of the items selected by this chromosome
			double totalWeight = getKnapsackSum(chromosome, weights);
			double totalValue = getKnapsackSum(chromosome, values);

			// if total weight exceeds the maximum, penalizes the value obtained by this chromosome
			if (totalWeight > maxWeight) {
				totalValue = 0;
			}

			individual.setScore(totalValue);
		}

	}

	private double getKnapsackSum(BooleanChromosome chromosome, double[] vals) {

		double total = 0.0;

		// computes the sum of the values of all the items carried
		for (int i = 0; i < chromosome.length(); ++i) {

			// if the item is put in the knapsack
			if (chromosome.getValue(i)) {
				total += vals[i];
			}
		}

		return total;
	}
}
