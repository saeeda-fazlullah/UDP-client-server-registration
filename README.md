# UDP Client–Server Registration System (Java)

This project is a **Java-based UDP Client–Server application** developed as part of a **Distributed Application Development** course.  
It demonstrates **connectionless communication using UDP** where multiple clients can send civil registration data to a centralized server.

---

## 🎯 Project Objective

The objective of this project is to:
- Understand **distributed systems fundamentals**
- Implement **UDP-based client–server communication**
- Practice data transmission without persistent connections
- Store received client data on the server side using file handling

---

## 🧠 System Overview

### 🔹 Client
- Takes user input:
  - Civil Registration Number (CRN)
  - First Name
  - Last Name
- Sends the data to the server using UDP packets

### 🔹 Server
- Listens continuously on a fixed UDP port
- Receives client data
- Stores all received records in a text file

---

## 📡 Communication Model

- **Protocol:** UDP (User Datagram Protocol)
- **Server Port:** 3000
- **Communication Type:** Connectionless
- **Data Format:** CRN FirstName LastName

---

## 🧑‍💻 Technologies Used

- **Language:** Java
- **Networking:** UDP (`DatagramSocket`, `DatagramPacket`)
- **I/O:** File handling using `FileWriter`
- **Architecture:** Client–Server
- **Course:** Distributed Application Development

---

## 📂 Project Structure

udp-client-server-registration/
│

├── Server.java # UDP server that receives and stores client data

├── Client.java # UDP client that sends CRN and name to server

├── Record.txt # Auto-generated file storing registration records

└── README.md # Project documentation


---

## 🚀 How to Run the Project

### 1️⃣ Compile both files

** ```bash
javac Server.java
javac Client.java

2️⃣ Run the Server (first)
java Server


The server will start listening on port 3000.

3️⃣ Run the Client (in a new terminal)
java Client


Enter:

CRN

First Name

Last Name

You can run the client multiple times to simulate multiple users.

📝 Sample Input
CRN: 123456789
First Name: Saeeda
Last Name: Fazlullah

📄 Output (Server Side)

The server saves data in Record.txt:

123456789 Saeeda Fazlullah
987654321 Sara Ali
456789123 John Smith

🔑 Key Concepts Demonstrated

UDP socket programming

Datagram packet transmission

Client–server architecture

Distributed system communication

File-based persistence

Handling multiple client requests

Stateless communication model

👩‍💻 Author

Saeeda Fazlullah
MSc Computer Science (Current)| AI Researcher | AI Enthusiast

📍 Riyadh 

🔗 GitHub: https://github.com/saeeda-fazlullah

🔗LinkedIn: https://www.linkedin.com/in/saeeda-fazlullah-98a6a01b5

⭐ If this project helped you, consider giving it a star on GitHub!
