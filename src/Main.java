import java.io.IOException;
import java.util.LinkedList;

public class Main
{
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        DataSeparator dataSeparator = new DataSeparator();
        NaiveBayes naiveBayes = new NaiveBayes();

        LinkedList<String> testingData = new LinkedList<String>();
        
        FileManager.readIntoLinkedList("positiveTesting.txt", testingData);
        naiveBayes.classifyDocuments(testingData, 0);
        
        FileManager.readIntoLinkedList("negativeTesting.txt", testingData);
        naiveBayes.classifyDocuments(testingData, 1);
        
        FileManager.readIntoLinkedList("neutralTesting.txt", testingData);
        naiveBayes.classifyDocuments(testingData, 2);
        
        naiveBayes.metrics();
    }
}
