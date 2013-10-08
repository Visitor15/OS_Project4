//Name			John Mutabazi
//Course			Java Programming 1302J
//Assignment   Lab 7 - Late Submission
//Due Date 		10/17/2012
//Purpose 		Reads a phrase from the standard input and prints the number of 
// occurrences of each letter in that phrase.         
// ****************************************************************
import java.io.*;
import java.util.Scanner;
public class CountLetters
{
    public static void main(String[] args)
    {
	int[] counts = new int[26];
	Scanner scan = new Scanner(System.in);

	//get word from user
	System.out.print("Enter a phrase: ");
	String word = scan.nextLine();

	//convert to all upper case
	word = word.toUpperCase();

	//count frequency of each letter in string
	for (int i=0; i < word.length(); i++)
	{
		try
		{
			 counts[word.charAt(i)-'A']++;
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
				System.out.println(word.charAt(i) +"Not a letter");
		}
	}

	//print frequencies
	System.out.println();
	for (int i=0; i < counts.length; i++)
	    if (counts [i] != 0)
		System.out.println((char)(i +'A') + ": " + counts[i]);

    }

}