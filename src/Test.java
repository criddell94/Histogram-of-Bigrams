import java.io.*;

public class Test
{
        public static void main(String[] args)
        {
                // test that FileNotFoundException is thrown
                BigramParser bp = new BigramParser("imaginary-file.txt");

                if( ! bp.parseFile() )
                {
                        System.out.println("GOOD! This failed because the file does exist.\n");
                }
                else
                {
                        System.out.println("BAD! Should have failed for non-existing file.\n");
                }

                bp = new BigramParser("test-files/tester2.txt");
                bp.parseFile();
                bp.printHistogram();
        }
}
