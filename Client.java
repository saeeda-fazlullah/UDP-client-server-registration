import java.io.*;
import java.net.*;
import java.util.*;

/*
A UDP Client that sends the name in packets and bytes
and receives the SSN number of the client in encrypted form
then decrypts it and sents Thankyou message to the server
*/
class Client
{
	public static void main(String args[]) throws Exception
	{
		Scanner input = new Scanner(System.in);													
		System.out.println("Please enter your first name:\n");									//Promting for first name
		String firstName = input.next();														
		System.out.println("Please enter your last name:\n");									//Prompting for last name
		String lastName = input.next();															
		
		String plainText = firstName+" "+lastName;												//Concatinating first name and last name
		
		plainText = plainText.toUpperCase();													//Converting the name to upper-case
		
		DatagramSocket clientSocket = new DatagramSocket();										//Creating a DatagramSocket for UDP
		InetAddress IPAddress = InetAddress.getByName("localhost");								//Getting IP of client
		
		byte[] cypherTextSend = new byte[1024];
		byte[] cypherTextReceive = new byte[1024];
		byte[] plainTextSend = new byte[18];
		
		String cypherText = encryptData(plainText);												//Encrypting the name of client
		cypherTextSend = cypherText.getBytes();													//and converting to bytes
		
		DatagramPacket sendPacket = new DatagramPacket(cypherTextSend, cypherTextSend.length, IPAddress, 3000);
		clientSocket.send(sendPacket);															//Sending the data packet
		
		DatagramPacket receivePacket = new DatagramPacket(cypherTextReceive, cypherTextReceive.length);
		clientSocket.receive(receivePacket);													//Receiving the encrypted SSN from server
		
		String decryptedSentence = new String(receivePacket.getData(),0,receivePacket.getLength());
		
		if(decryptedSentence.startsWith("Sorry your"))											//If the received sentence starts with the words of denial message
		{																						//then display the denial message and terminate the program
			System.out.println("MESSAGE FROM SERVER: "+decryptedSentence);
			System.exit(1);
		}
		
		decryptedSentence = decryptData(decryptedSentence);										//Decrypting the message using the decrypting algorithm
		
		System.out.println("MESSAGE FROM SERVER: " + decryptedSentence);						//Displaying the Decrypted SSN
		
		String response = "Thankyou!";
		plainTextSend = response.getBytes();
		
		DatagramPacket plainPacket = new DatagramPacket(plainTextSend, plainTextSend.length, IPAddress,3000);
		clientSocket.send(plainPacket);															//Sending a plain Thankyou message
		
		clientSocket.close();																	//Closing the connection
	}
	
	/*
	Method: encryptData
	Input: Name of the client
	Output: The name of client in encrypted form.
	Function: Shifts the letters 4 places to the right.
	*/
	public static String encryptData(String input)
	{
		String encrypted ="";
		for(int i = 0; i<input.length(); i++)
		{
			char c = input.charAt(i);
			c = (char)(c+4);
			if(c > 'Z')
			{
				c = (char)(c-26);
			}
			encrypted = encrypted + Character.toString(c);
		}
		return encrypted;
	}
	
	/*
	Method: decryptData
	Input: Encrypted message from server.
	Output: decrypted SSN.
	Function: Shifts the letters 4 places to the left.
	*/
	public static String decryptData(String output)
	{
		String decrypted = "";
		for(int i = 0; i<output.length(); i++)
		{
			if((int)(output.charAt(i)) != 00)
			{
				char c = output.charAt(i);
				c = (char)(c-4);
				decrypted = decrypted+ Character.toString(c);
			}
		}
		return decrypted;
	}
}