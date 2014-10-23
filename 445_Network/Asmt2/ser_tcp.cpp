//    SERVER TCP PROGRAM
// revised and tidied up by
// J.W. Atwood
// 1999 June 30
// There is still some leftover trash in this code.

/* send and receive codes between client and server */
/* This is your basic WINSOCK shell */


#pragma once
#pragma comment( linker, "/defaultlib:ws2_32.lib" )
#include <winsock2.h>
#include <ws2tcpip.h>
#include <process.h>
#include <winsock.h>
#include <iostream>
#include <windows.h>
#include <stdio.h>
#include <stdlib.h>
#include <sstream>
#include <fstream>

using namespace std;

//port data types

#define REQUEST_PORT 5001
#define UTIMER 300000
#define STIMER 10



int port=REQUEST_PORT;

//socket data types
SOCKET sock;	//server socket?
//SOCKET s1;  //client socket??


SOCKADDR_IN sa_in;     // router info, IP, port(7001)
SOCKADDR_IN sa_router;        // port 5001, server IP address
SOCKADDR from;

int fromlen;

#define STOPNWAIT 1 //Bits width For stop and wait
struct MESSAGE_FRAME
{
	unsigned char header; //whats header for ? the packet numbers?
	unsigned int snwseq:STOPNWAIT;  //snwseq is 1 bit
	char data[1024];//or error message
} message_frame;

struct THREE_WAY_HS{
	int client_number;
	int server_number;
	int direction;
	char file_name[50];
} three_way_hs;


string fileDirection;



union {struct sockaddr generic;
	struct sockaddr_in ca_in;}ca;

	int calen=sizeof(ca); 

	//buffer data types
	const int PACKETSIZE=1024;
	char szbuffer[PACKETSIZE];
	char *buffer;
	int ibufferlen;
	int ibytesrecv;
	CONST int BINFILE=0; 
	CONST int TEXTFILE=1; 
	string filename; 

	int ibytessent;

	//host data types
	char localhost[11];

	HOSTENT *hp;

	//wait variables
	int nsa_in;
	int r,infds=1, outfds=0;
	struct timeval timeout;
	const struct timeval *tp=&timeout;

	

	fd_set readfds;

	//others
	HANDLE test;

	DWORD dwtest;


bool ListDirectoryContents(const char *sDir)
{
    WIN32_FIND_DATA fdFile;
    HANDLE hFind = NULL;

    char sPath[2048];

    //Specify a file mask. *.* = We want everything!
    sprintf(sPath, "%s\\*.*", sDir);

    if((hFind = FindFirstFile(sPath, &fdFile)) == INVALID_HANDLE_VALUE)
    {
        printf("Path not found: [%s]\n", sDir);
        return false;
    }

    do
    {
        //Find first file will always return "."
        //    and ".." as the first two directories.
        if(strcmp(fdFile.cFileName, ".") != 0
                && strcmp(fdFile.cFileName, "..") != 0)
        {
            //Build up our file path using the passed in
            //  [sDir] and the file/foldername we just found:
            sprintf(sPath, "%s\\%s", sDir, fdFile.cFileName);

            //Is the entity a File or Folder?
            if(fdFile.dwFileAttributes &FILE_ATTRIBUTE_DIRECTORY)
            {
                printf("Directory: %s\n", sPath);
                ListDirectoryContents(sPath); 
            }
            else{
                printf("File: %s\n", sPath);
            }
        }
    }
    while(FindNextFile(hFind, &fdFile)); //Find the next file.

    FindClose(hFind); //Always, Always, clean things up!

    return true;
}

	int convertHex(int hexValue){
		stringstream ss; 
		ss<<hexValue;
		ss>>std::hex>>hexValue;
		return hexValue;
	}

	int main(void){

		WSADATA wsadata;


		try{        		 
			if (WSAStartup(0x0202,&wsadata)!=0){  
				cout<<"Error in starting WSAStartup()\n";
			}else{
				buffer="WSAStartup was suuccessful\n";   
				WriteFile(test,buffer,sizeof(buffer),&dwtest,NULL); 

			}  

			//Display info of local host

			gethostname(localhost,10);
			cout<<"hostname: "<<localhost<< endl;

			if((hp=gethostbyname(localhost)) == NULL) {
				cout << "gethostbyname() cannot get local host info?"
					<< WSAGetLastError() << endl; 
				exit(1);
			}

			//Create the server socket
			if((sock = socket(AF_INET,SOCK_DGRAM, 0))==INVALID_SOCKET) 
				throw "can't initialize socket";
			// For UDP protocol replace SOCK_STREAM with SOCK_DGRAM 

			memset(&sa_in,0,sizeof(sa_in));
			//Fill-in Server Port and Address info.
			sa_in.sin_family = AF_INET;
			sa_in.sin_port = htons(port);
			sa_in.sin_addr.s_addr = htonl(INADDR_ANY);


			//Bind the server port
			if (bind(sock,(LPSOCKADDR)&sa_in,sizeof(sa_in)) == SOCKET_ERROR)
				throw "can't bind the socket";
			cout << "Bind was successful" << endl;


			if((hp=gethostbyname(localhost)) == NULL)  //i put localhost here since router and server are on same machine
			throw "get server name failed\n";
			memset(&sa_router,0,sizeof(sa_router));
			memcpy(&sa_router.sin_addr,hp->h_addr,hp->h_length);
			sa_router.sin_family = hp->h_addrtype;   
			sa_router.sin_port = htons(7001); //7001 for router, 5000 for client


			

			//wait loop
			
			while(1)

			{
				
				timeout.tv_sec = STIMER;
				int packetAmt=0;
				FD_ZERO(&readfds);
				FD_SET(sock,&readfds);  //always check the listener
				fromlen = sizeof(from);
				if(!(outfds = select (1 , &readfds, NULL, NULL, tp))) //wait until tp expires
				{			 
					//throw "Timer error!";
				}
				else if (outfds > 0) //there are incoming packets
				{
					
					// incoming packet for three way handshake
					if(FD_ISSET(sock, &readfds)) 
					{
						if((ibytesrecv = recvfrom(sock, (char*)& szbuffer, sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
							throw "Receive error in server program\n";
						int threeWayVal=atoi(szbuffer);
						cout << "This is the three way handshake value: " << szbuffer<< endl;

						// send three way handshake value back to client
						int server_seq_number= rand() % 256;
						// 10 is saving the number in decimal
						_itoa(server_seq_number,szbuffer, 10);  
						//three_way_hs.server_number = rand() % 256; 
						sendto(sock, szbuffer, sizeof(szbuffer), 0,(SOCKADDR*) &sa_router, sizeof(sa_router)); //send client number to server

						// receive three way handshake value from client
						if((ibytesrecv = recvfrom(sock, (char*)& szbuffer, sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
							throw "Receive error in server program\n";
						threeWayVal=atoi(szbuffer);
						cout << "This is the final three way handshake value: " << threeWayVal<< endl;
					}
					
					/*
					if(FD_ISSET(sock, &readfds)) //incoming packet for file direction
					{
						if((ibytesrecv = recvfrom(sock, (char*)& szbuffer, sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
							throw "Receive error in server program\n";
						fileDirection=szbuffer;
						cout << "This is the file direction: " << fileDirection<< endl;

						//send back file direction to client
						ibytessent = sendto(sock,szbuffer,sizeof(szbuffer),0, (SOCKADDR*) &sa_router, sizeof(sa_router));
						if(ibytessent == SOCKET_ERROR)
						{
							 throw "error in send in server program\n";
						}
						
					}
					else
					{
						//send nak since we timed out
					}
					if (fileDirection == "put" || fileDirection =="get")
					{
						if(FD_ISSET(sock, &readfds)) //another packet
						{
							//receive file name
							if((ibytesrecv = recvfrom(sock, (char*)& szbuffer, 	sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
									throw "Receive error in server program\n";
							filename=szbuffer;
							cout << "This is the file name: " << filename<< endl;
							// Send file name back to client
							if((ibytessent = sendto(sock,szbuffer,sizeof(szbuffer),0,(SOCKADDR*) &sa_router, sizeof(sa_router)))==SOCKET_ERROR)
							throw "error in send in server program\n";
				
						}
						if (fileDirection == "put")
						{		
							if(FD_ISSET(sock, &readfds))
							{
								//receive filesize
								if((ibytesrecv = recvfrom(sock,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
									throw "Receive error in server program\n";
								//Print file size 
								int fileSize = atoi(szbuffer); 
								cout << "This is the file size: " << fileSize<< endl;


								//send filesize back to router
								if((ibytessent = sendto(sock,szbuffer,sizeof(szbuffer),0, (SOCKADDR*) &sa_router, sizeof(sa_router)))==SOCKET_ERROR)
									throw "error in send in server program\n";
							}	
							else
							{
								//send nak
							}
							if(FD_ISSET(sock, &readfds))
							{
								//accept packet ammounts
								if((ibytesrecv = recvfrom(sock,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
									throw "Receive error in server program\n";
								packetAmt=atoi(szbuffer); 
								cout << "This is the number of packets received: " << packetAmt<< endl;
							}
							else
							{
								//send nak
							}
							if(FD_ISSET(sock, &readfds))
							{
								//Accept file
								ofstream myfile(filename, ios::binary); 
								int ctr=0; 
								char * memblock	= new char [1024];
								if (myfile.is_open())
								{
									if (packetAmt==0)
									{
										cout<<"PACKET AMT is 0!"<<endl;
									}
									for (int i=0; i<packetAmt; i++)
									{
										memset(szbuffer, '\0', 1024); 
										ibytessent= recvfrom(sock,memblock,PACKETSIZE,0, &from, &fromlen );
										cout<<"Iteration number "<<ctr<<" Amount received is: "<<ibytessent<<endl;
										myfile.write(memblock, PACKETSIZE); 
										++ctr; 
									}
									int remainderPacket=0; 
									if((ibytesrecv = recvfrom(sock,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
										throw "Receive error in server program\n";
									remainderPacket=atoi(szbuffer); 
									cout << "This is the remainder received: " << remainderPacket<< endl;

				
									//Write remainder to the file
				
									memset(szbuffer, '\0', sizeof(szbuffer)); 
									ibytessent= recvfrom(sock,szbuffer,sizeof(szbuffer),0, &from, &fromlen);
									cout<<"This is the buffer size of remainder: "<<sizeof(szbuffer)<<endl; 
									myfile.write(szbuffer, remainderPacket); 
								}
								myfile.close();
								cin.get();
							}
							else
							{
								//send nak
							}
						} //end put
						else if (fileDirection == "get")
						{
							// Open file on server side. Return error if it doesn't exist 
							// Same as put function on client side, but reversed 

							// send file size
							ifstream myfile(filename, ios::ate|ios::binary);
							//ifstream myfile(filename, ios::ate);
							ifstream::pos_type nonIntSize; 
							ifstream::pos_type currentPos;
							char buf[1024];
							if (myfile.is_open())
							{

								cout<<"Shouldn't be open"<<endl;
								int bytesLeft=0; 		
								nonIntSize=myfile.tellg(); 
								int fileSize =(int)nonIntSize; 
								cout<<"The file's size is "<<fileSize<<endl;
								ibytessent=sendto(sock,_itoa(fileSize,buf,10),sizeof(buf),0,(SOCKADDR*) &sa_router, sizeof(sa_router));
		
								//receive file size
								ibytesrecv=0; 
								if((ibytesrecv = recvfrom(sock,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
									throw "Receive failed\n";
								else
								{
									int serverFileSize=atoi(szbuffer);
									cout<<"File size from server: " << serverFileSize<<endl;   
		
								}
				
								// split file into separate packets
								int packetAmt = fileSize/PACKETSIZE;
								cout<<"Number of packet amounts: "<<packetAmt<<endl; 
								// send packet amount to client
								_itoa(packetAmt,buf,10);
								ibytessent=sendto(sock,buf,sizeof(buf),0,(SOCKADDR*) &sa_router, sizeof(sa_router));
								// send file to server  
								// reset pointer to start of document
								myfile.seekg(0,ios::beg);
			
								if (packetAmt == 0)
								{
									cout<<"PACKET AMT 0!"<<endl;
								}
								for (int i=0; i<packetAmt; i++)
								{
									memset(buf, '\0', PACKETSIZE); 
									myfile.read(buf, PACKETSIZE); 
									ibytessent= sendto(sock,buf,PACKETSIZE,0,(SOCKADDR*) &sa_router, sizeof(sa_router));
									cout<<"Bytes sent for iteration "<<i<<" is "<< ibytessent<<endl; 					
								}
		
								// get remainder size that must be sent 
								int remainderSize = fileSize % PACKETSIZE; 
								cout<<"Remainder amount is: "<<remainderSize<<endl; 
								ibytessent=sendto(sock,_itoa(remainderSize,buf,10),sizeof(buf),0,(SOCKADDR*) &sa_router, sizeof(sa_router));
		
							// send remainder to server
								// need to memset?
								memset(buf, '\0', sizeof(buf)); 
								myfile.read(buf, remainderSize);
								ibytessent=sendto(sock,buf,remainderSize,0,(SOCKADDR*) &sa_router, sizeof(sa_router));
								myfile.close();
								cin.get();
							
							}
							else
							{
								char errorMsg[50]="Error could not open file ";
								cout<<"Error could not open file "<<endl; 
								//sprintf(szbuffer,errorMsg);
								ibytessent= sendto(sock,errorMsg,50,0, (SOCKADDR*) &sa_router, sizeof(sa_router));

								cout<<"Exiting program "<<endl;
								cin.get();
								exit(1); 
							}

						}//end get

					} //end PUT or GET
					else if (fileDirection == "list")
					{
						ListDirectoryContents("C:\\Users\\Administrator\\Documents\\Visual Studio 2012\\Projects\\server");
						cin.get();
						
					}
				*/			
				} //outfds <= 0
				else
				{
					//no incoming packets at all
				}

			}  //while loops	
				
		} //try loop
				
	
		//Display needed error message.
		 
		catch(char* str) { cerr<<str<<WSAGetLastError()<<endl;}

		//close Client socket
		closesocket(sock);		

		//close server socket
		closesocket(sock);

		/* When done uninstall winsock.dll (WSACleanup()) and exit */
		WSACleanup();
		return 0;

	}








