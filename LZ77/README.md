# LZ77 encoding program

## About
The program encodes the given message with LZ77 algorithm. Program is running until whole message is sent or character "*" is found. When starting the program you need to input dictionary and lookahead buffer size. Both numbers need to be non-negative integers. When float is given the program stops running. If both parametars are good you can send your message.

## Examples 

### Correct input:
Enter dictionary size >> 6
Enter lookahead buffer >> 5
Enter a message >> aaaabbbbbbccccccd
Sent message >> aaaabbbbbbccccccd*
Encoding...
(0, 0, a)  (1, 3, b)  (1, 4, b)  (0, 0, c)  (1, 4, c)  (0, 0, d)  (0, 0, *)

### Incorrect inputs:
Enter dictionary size >> 5
Enter lookahead buffer >> -3
Lookahead buffer can't be negative! 
Enter lookahead buffer >> 1,6
Size of the lookahead buffer needs to be an integer... Turning off...

Enter dictionary size >> 6
Enter lookahead buffer >> 5
Enter a message >> 
You didn't send a message.

### If character * is not at the end of the message(but exist)
Enter dictionary size >> 6
Enter lookahead buffer >> 5
Enter a message >> aaba*aacbddbbbcca
Sent message >> aaba*aacbddbbbcca*
Encoding...
(0, 0, a)  (1, 1, b)  (2, 1, *)


## Using the program
The program is made for fun and is not made for encoding whole documents. To start it you can dowload the LZ77.jar file and run it inside command prompt with command > java -jar "path to LZ77.jar file" 
If you want to modify, run inside of the code editor of your choice or just see my algorithm it is inside package -> src -> Main -> Main.java

The program is great if you are leaning LZ77 encoding and want some practice. Just simply think of some random numbers and a random string and see the encoded message --author Danko Čurlin
