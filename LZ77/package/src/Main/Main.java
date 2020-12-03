package Main;

import java.util.Scanner;
import java.util.Arrays;
import java.util.InputMismatchException;


public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		//defining variables and taking inputs
		Scanner input = new Scanner(System.in);
		Scanner input2 = new Scanner(System.in);
		int dictionary;
		int lookAhead;
		do {
			System.out.println("Enter dictionary size >> ");
			try {
				dictionary = input.nextInt();
			} catch (InputMismatchException ex) {
				System.err.print("Size of the dictionary needs to be an integer... Turning off...");
				return;
			}
			if(dictionary < 0) {
				System.out.println("Dictionary size can't be negative! ");
			} else break;
		} while(true);
		
		do {
			System.out.println("Enter lookahead buffer >> ");
			try {
				lookAhead = input.nextInt();
			} catch (InputMismatchException ex) {
				System.err.println("Size of the look ahead buffer needs to be an integer... Turning off...");
				return;
			}
			if(lookAhead < 0) {
				System.out.println("Look ahead buffer can't be negative!");
			} else break;
		} while(true);
		
		int current = 0;
		int lenght = 0;
		StringBuilder strBld = new StringBuilder();
		
		System.out.println("Enter a message >> ");	
		String message = input2.nextLine();
		
		int strlen = message.length();
		char[] messageArray = new char[strlen + lookAhead];			//making an array biggest to avoid dealing with exceptions(IndexOutOfBounds)
		
		if(!message.endsWith("*")) {						//message can be sent with or without character for ending, we will add it after anyways
			strBld.append(message).append("*");	
			message = strBld.toString();
			strlen++;
		}
		
		lenght = message.toString().length();
		if(lenght == 1) {
			System.out.println("You didn't send a message."); 		//if there is no message output the message
			return;
		}
		
		
		StringBuilder foundInDictionary = new StringBuilder();
		

		messageArray = message.toLowerCase().replaceAll(" ", "").toCharArray();		//making the string useable and comparable
		System.out.println("Sent message >> " + new String(messageArray));
		System.out.println("Encoding...");
		
		
		//making new arrays to keep the original intact
		char[] searchBuffer = "".toCharArray();
		char[] lookAheadBuffer = "".toCharArray();
		
		
		//
		//MY LZ77 ALGORITHM
		//
		while(true) {
			//first letter will always be output in the same way
			if(current == 0) {											
				lookAheadBuffer = Arrays.copyOfRange(messageArray, current, current+lookAhead);
				System.out.printf("(0, 0, %c)  ", messageArray[0]);			
				current++;
				continue;
			} 
			//we have 2 possible outcomes, when we are in our dictionary space and when we exceed it
			else if(current < dictionary) {
				searchBuffer = Arrays.copyOfRange(messageArray, 0, current);
				lookAheadBuffer = Arrays.copyOfRange(messageArray, current, current+lookAhead);
			} 
			//2nd outcome - if our current position is over max dictionary length 
			else {
				searchBuffer = Arrays.copyOfRange(messageArray, current-dictionary, current);		
				lookAheadBuffer = Arrays.copyOfRange(messageArray, current, current+lookAhead);
			}
			
			
			int searchBufferLenght = searchBuffer.length;		
			int foundInDictionaryLenght = 0;					//number of repeated chars in our dictionary
			
			int jumpBack = 0;							//number of places we go back into out dictionary from current position
			
			String string1 = new String(searchBuffer);	
			string1 = string1.trim();				//trimming the strings
			String string2 = new String(lookAheadBuffer);	
			string2 = string2.trim();
			boolean flag = false;				//true if we took all the characters from our dictionary from jumpBack to the end
			
			
			foundInDictionary = new StringBuilder();
			
			for(int loop = 1; loop <= lookAhead; loop++) {	
				//loop variable will tell us how much and which characters in sequence we took from our dictionary
				if(string1.contains(string2.subSequence(0, loop))) {				
					foundInDictionaryLenght++;				
					foundInDictionary.append(lookAheadBuffer[loop-1]);
					
					//jumpBack will be our searchBufferLenght - the last index of string(or single char) we found in dictionary
				    jumpBack = string1.lastIndexOf(foundInDictionary.toString());	
					jumpBack = searchBufferLenght - jumpBack;	
					
					//if we jumped back more than one character and we took every character from jumpBack to the end
					if(jumpBack > 1 && foundInDictionaryLenght == jumpBack) {		
						flag = true;												
							//look for chars that will be repeated from the dictionary again, test variable will tell us the number
							for(int test = 0; test < lookAheadBuffer.length-foundInDictionaryLenght;) {
								try {
									if(lookAheadBuffer[foundInDictionaryLenght+test] == searchBuffer[(searchBufferLenght-jumpBack+test)%
									                                                                 (lookAheadBuffer.length-foundInDictionaryLenght)]) {
										foundInDictionaryLenght++;							
									}
								} catch (ArrayIndexOutOfBoundsException ex) {	
								}
								test++;
								break;
							}
					}
				
				//when we jump back from upper loop we are checking how much chars did we found in dictionary
				} else {													 
					if(foundInDictionaryLenght == 0) break;				//if no chars found nothing needs to be set
					//if we found more chars than we can white in the buffer, take the max number we can put -1(the one we add last)
					else if(foundInDictionaryLenght >= lookAhead) {		
						foundInDictionaryLenght = lookAhead-1;	
					}
																			
					//if flag is set we already know everything we need	else we need to calculate how much we go back into the dictionary	
					else if(flag == true) break;						
					jumpBack = string1.lastIndexOf(foundInDictionary.toString());
					jumpBack = searchBufferLenght - jumpBack;					
					break;
				}
			}
			
			
			int repeated = 1;				//number of repeated chars we found in the dictionary
			int var = 0;											
			
			//if we jumped back max. 1 character we calculate how many time the char before/current char will be repeated
			if(jumpBack == 1 || jumpBack == 0) {					
				for(int k = 2; k < lookAheadBuffer.length; k++, var++) {
					if(k > lookAhead-foundInDictionaryLenght) break;			//again stoping if we exceed lenght of out buffer
					if(lookAheadBuffer[var] == lookAheadBuffer[var+1]) {	
						repeated++;
					} else break;
				}
			}
			
			else if(jumpBack > 1) {			
				repeated = foundInDictionaryLenght;
			}
			
			//if no chars found in dictionary we are jumping all the way here and outputing the same string with next char 
			if(foundInDictionaryLenght == 0) {				
				System.out.printf("(0, 0, %c)  ", messageArray[current]);		
				if(messageArray[current] == '*') break;					//checking for message ending
				repeated = 0;										
				current++;
				continue;
			}
			
			//if we found at least one char in dictionary, moving the current position, and outputting encoded string
			current = current + repeated + 1;													
			System.out.printf("(%d, %d, %c)  ", jumpBack, repeated, messageArray[current-1]);
			if(messageArray[current-1] == '*') break;			//checking for message ending
		}
		input.close();			
		input2.close();
	}

}
