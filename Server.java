/*  
The server class listens to the incoming requests
in the form of packets and bytes converts it into string
then it searches for the SSN of the corresponding name
and returns the SSN if found in record.
*/

import java.io.*;
import java.net.*;
import java.util.*;

/* UDP Server Class */
class Server
{
	public static void main(String args[]) throws Exception
    {
        DatagramSocket serverSocket = new DatagramSocket(3000);										//A DatgramSocket working at port 3000 
		System.out.println("The server is now running...\n\n");
        byte[] encryptedDataReceived = new byte[1024];												//Defining byte variable for received bytes
        byte[] encryptedDataSend = new byte[1024];													//and sending bytes.
		while(true)
		{
			DatagramPacket receivePacket = new DatagramPacket(encryptedDataReceived, encryptedDataReceived.length);
            
			serverSocket.receive(receivePacket);													//Receiving packets from the client
            String sentence = new String( receivePacket.getData(),0,receivePacket.getLength());		//Converting received bytes into String
	
			String out = decryptData(sentence);														//Decrypting the received string from the client using decrypting algo 
            System.out.println("SSN request from " + out);											//Printing the Client name from whom the request is received.
			try
			{
				File dataFile = new File("Records.txt");											//Opening the file with the records.
				Scanner data = new Scanner(dataFile);												//Using scanner to read data from file.
				boolean found = false;
				String ssn;
				String name;
				while(data.hasNext())																//Keep reading while the file has data
				{
					ssn = data.next();																//Assigning the value of SSN
					name = data.next();																//Assigning the value of corresponding name
					name = name.toUpperCase();														//Converting the name to upper case for comparison
					String noSpace = out.replaceAll("\\s+","");										//Removing any white spaces from received name
					if(name.equals(noSpace))														//Checking if the name from file matches the received name
					{
						found = true;																
						ssn = encryptData(ssn);														//If the name matches then encrypt the SSN
						encryptedDataSend = ssn.getBytes();											//and convert the data to bytes
						break;																		//then break the loop
					}
				}
				if(found == false)
				{
					String notFound = "Sorry your name is not in the record.";						//If the names dont match then sent the user denial message
					encryptedDataSend = notFound.getBytes();
					InetAddress IPAddress = receivePacket.getAddress();									//Getting the IP and port
					int port = receivePacket.getPort();													//of the client from whom the request was received
					DatagramPacket sendPacket =
					new DatagramPacket(encryptedDataSend, encryptedDataSend.length, IPAddress, port);
					serverSocket.send(sendPacket);
					// System.exit(0);
				}
				else 
				{
					InetAddress IPAddress = receivePacket.getAddress();									//Getting the IP and port
					int port = receivePacket.getPort();													//of the client from whom the request was received
					DatagramPacket sendPacket =
					new DatagramPacket(encryptedDataSend, encryptedDataSend.length, IPAddress, port);
					serverSocket.send(sendPacket);														//Sending SSN
					
					receivePacket = new DatagramPacket(encryptedDataReceived, encryptedDataReceived.length);
					serverSocket.receive(receivePacket);												//Receiving Thankyou message
					String plainTextSend = new String( receivePacket.getData(),0,receivePacket.getLength());
					System.out.println(out+": "+plainTextSend+"\n");
				}
				
			}
			catch (FileNotFoundException e)															//catching file not found exception
			{
				System.out.println("Error reading file!");
			}
		}
    }
	
	/*
	Method: decryptData
	Input: received encrypted string
	Output: decrypted string
	*/
	public static String decryptData(String input)
	{
		String decrypted ="";
		for(int i = 0; i<input.length(); i++)
		{
			char c = input.charAt(i);
			c = (char)(c-4);
			if(c<'A' && c != ' ')
			{
				c = (char)(c+26);
			}
			decrypted = decrypted + Character.toString(c);
		}
		return decrypted;
	}
	
	/*
	Method: encryptData
	Input: SSN number of the client
	Output: encrypted SSN number
	*/
	public static String encryptData(String ssn)
	{
		String encrypted = "";
		for(int i = 0; i<ssn.length(); i++)
		{
			char c = ssn.charAt(i);
			
			c = (char)(c+4);
			encrypted = encrypted+ Character.toString(c);
		}
		return encrypted;
	}
}