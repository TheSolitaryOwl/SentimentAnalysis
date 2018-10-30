//=================================================================================================
// Program		: Sentiment Analysis
// Class		: NaiveBayes.java
// Developer	: Zachary Rowton, Brandon Edwards
// Abstract		: Main java class for all things related to the Naive Bayes
//                method of classification. Creates frequency and TF-IDF weight
//                HashMaps.
//=================================================================================================

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class NaiveBayes
{
    //private int nk; // Number of times particular word appears in document
    private int positiveN, negativeN, neutralN; // Number of total words
    private int positiveV, negativeV, neutralV, totalV; // Number of unique words
    private double linesPositive = 0.0;
    private double linesNeutral = 0.0;
    private double linesNegative = 0.0;

    private HashMap<String, Integer> positiveFreq;
    private HashMap<String, Integer> negativeFreq;
    private HashMap<String, Integer> neutralFreq;
    private HashMap<String, Integer> totalFreq = new HashMap<String, Integer>();
    private LinkedList<String> positiveDocuments;
    private LinkedList<String> negativeDocuments;
    private LinkedList<String> neutralDocuments;
    private HashMap<String, Double> weights = new HashMap<String, Double>();

    // Metrics
    // Precision = True Positives / True Positives + False Positives
    // Recall = True Positives / True Positives + False Negatives

    private double precision = 0.0;
    private double recall = 0.0;
    private double posPrecision = 0.0;
    private double neuPrecision = 0.0;
    private double negPrecision = 0.0;
    private double posRecall = 0.0;
    private double neuRecall = 0.0;
    private double negRecall = 0.0;
    private double truePositives = 0.0;
    private double trueNegatives = 0.0;
    private double trueNeutrals = 0.0;
    private double falsePositives = 0.0;
    private double falseNegatives = 0.0;
    private double falseNeutrals = 0.0;
    private int control; // for telling the program which class is being tested. 0 = positive, 1 = negative, 2 = neutral


    /*
     * NOTES
     * 
     * TF-IDF:
     * TF = Frequency of word in document. (Number of times word appears in the given sentence
     * IDF = N / df where N is total number of documents (sentences) in collection and 
     * df is number of documents (sentences) where word appears.
     * 
     */

    // constructor for reading the training data, storing frequency, and calculating weights
    public NaiveBayes() throws IOException
    {
        File positiveFile = new File("positiveTraining.txt");
        File negativeFile = new File("negativeTraining.txt");
        File neutralFile = new File("neutralTraining.txt");

        positiveFreq(positiveFile);
        negativeFreq(negativeFile);
        neutralFreq(neutralFile);
        calcWeights();
        //System.out.println("Positive lines: " + linesPositive);
        //System.out.println("Negative lines: " + linesNegative);
    }
    
    // calculating frequency of positive words and incrementing various variables for later calculations
    private void positiveFreq(File file) throws IOException
    {
        positiveN = 0;
        positiveV = 0;
        String line = "";
        String[] tokens;
        positiveFreq = new HashMap<String, Integer>();
        positiveDocuments = new LinkedList<String>();

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        // Read pos.txt and store each unique word's count in the file
        while ((line = br.readLine()) != null)
        {
            line = line.toLowerCase();
            positiveDocuments.add(line);
            tokens = line.split(" ");
            linesPositive++;

            for (int i = 0; i < tokens.length; i++)
            {
                if (positiveFreq.get(tokens[i]) == null) // new positive word
                {
                    positiveFreq.put(tokens[i], 1);
                    positiveV++;
                    positiveN++;
                    totalV++;
                }
                else
                {
                    int freq = positiveFreq.get(tokens[i]);
                    freq++;
                    positiveFreq.put(tokens[i], freq);
                    positiveN++;
                }
                
                if (totalFreq.get(tokens[i]) == null)
                {
                	totalFreq.put(tokens[i], 1);
                }
                else
                {
                	int freq = totalFreq.get(tokens[i]);
                	freq++;
                	totalFreq.put(tokens[i], freq);
                }
            }
        }

        br.close();
        fr.close();
    }

 // calculating frequency of negative words and incrementing various variables for later calculations
    private void negativeFreq(File file) throws IOException
    {
        negativeN = 0;
        negativeV = 0;
        String line = "";
        String[] tokens;
        negativeFreq = new HashMap<String, Integer>();
        negativeDocuments = new LinkedList<String>();

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        // Read pos.txt and store each unique word's count in the file
        while ((line = br.readLine()) != null)
        {
            line = line.toLowerCase();
            negativeDocuments.add(line);
            tokens = line.split(" ");
            linesNegative++;

            for (int i = 0; i < tokens.length; i++)
            {
                if (negativeFreq.get(tokens[i]) == null) // new negative word
                {
                    negativeFreq.put(tokens[i], 1);
                    negativeV++;
                    negativeN++;
                    totalV++;
                }
                else
                {
                    int freq = negativeFreq.get(tokens[i]);
                    freq++;
                    negativeFreq.put(tokens[i], freq);
                    negativeN++;
                }
                
                if (totalFreq.get(tokens[i]) == null)
                {
                	totalFreq.put(tokens[i], 1);
                }
                else
                {
                	int freq = totalFreq.get(tokens[i]);
                	freq++;
                	totalFreq.put(tokens[i], freq);
                }
            }
        }

        br.close();
        fr.close();
    }

 // calculating frequency of neutral words and incrementing various variables for later calculations
    private void neutralFreq(File file) throws IOException
    {
        neutralN = 0;
        neutralV = 0;
        String line = "";
        String[] tokens;
        neutralFreq = new HashMap<String, Integer>();
        neutralDocuments = new LinkedList<String>();

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        // Read pos.txt and store each unique word's count in the file
        while ((line = br.readLine()) != null)
        {
            line = line.toLowerCase();
            neutralDocuments.add(line);
            tokens = line.split(" ");
            linesNeutral++;

            for (int i = 0; i < tokens.length; i++)
            {
                if (neutralFreq.get(tokens[i]) == null) // new neutral word
                {
                    neutralFreq.put(tokens[i], 1);
                    neutralV++;
                    neutralN++;
                    totalV++;
                }
                else
                {
                    int freq = neutralFreq.get(tokens[i]);
                    freq++;
                    neutralFreq.put(tokens[i], freq);
                    neutralN++;
                }
                
                if (totalFreq.get(tokens[i]) == null)
                {
                	totalFreq.put(tokens[i], 1);
                }
                else
                {
                	int freq = totalFreq.get(tokens[i]);
                	freq++;
                	totalFreq.put(tokens[i], freq);
                }
            }
        }

        br.close();
        fr.close();
    }
    
    private void calcWeights()
    {
    	for (String document : positiveDocuments)
    	{
    		String[] word = document.split(" ");
    		for (int i = 0; i < word.length; i++)
    		{
    			calcWeight(document, word[i]);
    		}
    	}
    	
    	for (String document : negativeDocuments)
    	{
    		String[] word = document.split(" ");
    		for (int i = 0; i < word.length; i++)
    		{
    			calcWeight(document, word[i]);
    		}
    	}
    	
    	for (String document : neutralDocuments)
    	{
    		String[] word = document.split(" ");
    		for (int i = 0; i < word.length; i++)
    		{
    			calcWeight(document, word[i]);
    		}
    	}
    }
    
    private void calcWeight(String document, String word)
    {
    	double tf;
    	double idf;
    	double tfidf;
    	double N = (positiveDocuments.size() + negativeDocuments.size() + neutralDocuments.size());
    	double df = 0.0;
    	
    	
    	tf = countMatches(document, word);
    	
    	for (int i = 0; i < positiveDocuments.size(); i++)
    	{
    		df += countMatches(positiveDocuments.get(i), word);
    	}
    	
    	for (int i = 0; i < negativeDocuments.size(); i++)
    	{
    		df += countMatches(negativeDocuments.get(i), word);
    	}
    	
    	for (int i = 0; i < neutralDocuments.size(); i++)
    	{
    		df += countMatches(neutralDocuments.get(i), word);
    	}
    	
    	idf = N / df;
    	tfidf = tf * idf;
    	tfidf = Math.log(tfidf);
    	
    	if (weights.get(word) == null)
    	{
    		weights.put(word, tfidf);
    	}
    }
    
    private int countMatches(String document, String word)
    {
    	int index = document.indexOf(word);
    	int count = 0;
    	while (index != -1 && index < document.length())
    	{
    		count++;
    		document = document.substring(index + 1);
    		index = document.indexOf(word);
    	}
    	
    	return count;
    }

    public void classifyDocuments(LinkedList<String> testingData, int control)
    {
        this.control = control;
        

        for (String line : testingData)
        {
            classify(line);
        }

        
    }

    public String classify(String review)
    {
        String[] token = review.toLowerCase().split(" ");
        double positiveProbability = linesPositive / (linesPositive + linesNegative + linesNeutral);
        double negativeProbability = linesNegative / (linesPositive + linesNegative + linesNeutral);
        double neutralProbability = linesNeutral / (linesPositive + linesNegative + linesNeutral);
        double numerator;
        double denominator;
        double threshold = 1.75;

        for (String word : token)
        {
            //System.out.println(weights.get(word) + "   " + word);
            if (positiveFreq.get(word) == null)
            {
            	positiveProbability *= 0.1;
            }
            else if (weights.get(word) > threshold)
            {
                numerator = positiveFreq.getOrDefault(word, 0) + 1;
                denominator = positiveN;
                positiveProbability *= numerator / denominator;
            }

            if (negativeFreq.get(word) == null)
            {
            	negativeProbability *= 0.1;
            }
            else if (weights.get(word) > threshold)
            {
                numerator = negativeFreq.getOrDefault(word, 0) + 1;
                denominator = negativeN;
                negativeProbability *= numerator / denominator;
            }
            
            if (neutralFreq.get(word) == null)
            {
            	neutralProbability *= 0.1;
            }
            
            else if (weights.get(word) > threshold)
            {
            	numerator = neutralFreq.getOrDefault(word, 0) + 1;
            	denominator = neutralN;
            	neutralProbability *= numerator / denominator;
            }
        }

        //System.out.println("Positive: " + positiveProbability);
        //System.out.println("Negative: " + negativeProbability);
        //System.out.println();
        //System.out.println("Input: " + review);
        //System.out.printf("Positive: %.10f%% \n", positiveProbability * 100.00);
        //System.out.printf("Neutral : %.10f%% \n", neutralProbability * 100.00);
        //System.out.printf("Negative: %.10f%% \n", negativeProbability * 100.00);

        if (positiveProbability > negativeProbability && positiveProbability > neutralProbability)
        {
        	// if statements for precision and recall metrics
        	if (control == 0)
        	{
        		truePositives++;
        	}
        	if (control == 1)
        	{
        		falseNegatives++;
        	}
        	if (control == 2)
        	{
        		falseNeutrals++;
        	}
        	
        	
            return "Positive";
        }
        else if (negativeProbability > positiveProbability && negativeProbability > neutralProbability)
        {
        	// if statements for precision and recall metrics
        	if (control == 0)
        	{
        		falsePositives++;
        	}
        	if (control == 1)
        	{
        		trueNegatives++;
        	}
        	if (control == 2)
        	{
        		falseNeutrals++;
        	}
        	
        	
            return "Negative";
        }
        else if (neutralProbability > positiveProbability && neutralProbability > negativeProbability)
        {
        	// if statements for precision and recall metrics
        	if (control == 0)
        	{
        		falsePositives++;
        	}
        	if (control == 1)
        	{
        		falseNegatives++;
        	}
        	if (control == 2)
        	{
        		trueNeutrals++;
        	}
        	
        	
            return "Neutral";
        }
        else
        	return null;
    }
    
    public String metrics()
    {
    	posPrecision = truePositives / (truePositives + falsePositives);
        neuPrecision = trueNeutrals/ (trueNeutrals + falseNeutrals);
        negPrecision = trueNegatives / (trueNegatives + falseNegatives);
        precision = (posPrecision + neuPrecision + negPrecision) / 3;
        precision *= 100;
        posRecall = truePositives / (truePositives + falseNegatives + falseNeutrals);
        neuRecall = trueNeutrals / (trueNeutrals + falseNegatives + falsePositives);
        negRecall = trueNegatives / (trueNegatives + falsePositives + falseNeutrals);
        recall = (posRecall + neuRecall + negRecall) / 3;
        recall *= 100;
        //System.out.printf("\nPercision: %2.2f%%\n", precision);
        //System.out.printf("Recall: %2.2f%%\n", recall);
        String output = String.format("Precision: %2.3f%%\n | Recall: %2.3f%%", precision, recall);
        return output;
    }
}
