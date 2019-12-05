import java.io.*;
import java.util.LinkedHashMap;

/**
 * A Bigram parsing object to read in a file and create a histogram of bigrams
 * (Frequency that two words next to each other appear next to each other in the file)
 */
public class BigramParser
{
        private String parseFile;
        private LinkedHashMap<String, Integer> histograms;

        /**
         * constructor
         * @param inputFile - name of the input text file
         */
        public BigramParser(String inputFile)
        {
                parseFile = inputFile;
                histograms = new LinkedHashMap<String, Integer>();
        }

        /**
         * print out file name followed by the histogram of each bigram
         */
        public void printHistogram()
        {
                System.out.println("'" + parseFile + "' Histogram of Bigrams:\n");

                histograms.forEach( (bigram, freq) -> {
                        System.out.println(bigram + " => " + String.valueOf(freq));
                });
        }

        /**
         * read in the file and count the bigram frequencies
         * @return boolean if it successfully worked - unsuccessful returns for testing
         */
        public boolean parseFile()
        {
                try 
                {
                        String fileTxt = "";

                        // get file, will throw FileNotFoundException if does not exist
                        File file = new File(parseFile);

                        // read in file and append to fileTxt string
                        BufferedReader br = new BufferedReader(new FileReader(file));

                        String str;
                        while( (str = br.readLine()) != null )
                        {
                                fileTxt += str + " "; // add space to end of line so lines don't run together
                        }

                        // normalize string to lowercase, remove all punctuation with alpha characters surrounding it
                        // replace all other punctuation with a space
                        // make all multi-spaces just 1 space, trim string
                        fileTxt = fileTxt.toLowerCase().replaceAll("([a-zA-Z])[^a-zA-Z ]([a-zA-Z])", "$1$2").replaceAll("[^a-zA-Z ]", " ").replaceAll(" +", " ").trim();

                        // split file by spaces
                        String[] words = fileTxt.split("\\s+", -1);
                        int wordAmnt   = words.length;

                        for( int i = 1; i < wordAmnt; i++ ) 
                        {
                                int prevIdx = i - 1;

                                String bigram = words[prevIdx] + " " + words[i];

                                // check if bigram already counted
                                if( ! histograms.containsKey(bigram) )
                                {
                                        // get amount of times bigram appears in text 
                                        int freq = this.getBigramFrequency(fileTxt, bigram); 
                                        histograms.put(bigram, freq); // add to found/counted bigrams
                                }
                        }
                }
                catch( Exception e )
                {
                        System.out.println(e.getMessage());
                        return false;
                }

                return true;
        }

        /**
         * internal helper function to get the frequency on a bigram in the give string
         * @param string of the file text
         * @param string of the bigram we want the frequency for
         * @return int of the frequency of given bigram
         */
        private int getBigramFrequency(String fullTxt, String bigram)
        {
                int bigramFreq = 0;
                int lastIdx    = 0;
                int bigramLen  = bigram.length();

                // while we're finding bigrams continue
                while( lastIdx != -1 )
                {
                        // find the first instance of the bigram starting at the last saved index
                        lastIdx = fullTxt.indexOf(bigram, lastIdx);

                        // if found increment amount and update last index
                        if( lastIdx != -1 )
                        {
                                bigramFreq++;
                                lastIdx += bigramLen;
                        }
                }

                return bigramFreq;
        }
}
