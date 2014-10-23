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

#define REQUEST_PORT 0x7070

int port=REQUEST_PORT;

//socket data types
SOCKET s;

SOCKET s1;
SOCKADDR_IN sa;      // filled by bind
SOCKADDR_IN sa1;     // fill with server info, IP, port
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
	int nsa1;
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

				/* display the wsadata structure 
				cout<< endl
					<< "wsadata.wVersion "       << wsadata.wVersion       << endl
					<< "wsadata.wHighVersion "   << wsadata.wHighVersion   << endl
					<< "wsadata.szDescription "  << wsadata.szDescription  << endl
					<< "wsadata.szSystemStatus " << wsadata.szSystemStatus << endl
					<< "wsadata.iMaxSockets "    << wsadata.iMaxSockets    << endl
					<< "wsadata.iMaxUdpDg "      << wsadata.iMaxUdpDg      << endl;
				*/
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
			if((s = socket(AF_INET,SOCK_STREAM,0))==INVALID_SOCKET) 
				throw "can't initialize socket";
			// For UDP protocol replace SOCK_STREAM with SOCK_DGRAM 


			//Fill-in Server Port and Address info.
			sa.sin_family = AF_INET;
			sa.sin_port = htons(port);
			sa.sin_addr.s_addr = htonl(INADDR_ANY);


			//Bind the server port

			if (bind(s,(LPSOCKADDR)&sa,sizeof(sa)) == SOCKET_ERROR)
				throw "can't bind the socket";
			cout << "Bind was successful" << endl;

			//Successfull bind, now listen for client requests.

			if(listen(s,10) == SOCKET_ERROR)
				throw "couldn't  set up listen on socket";
			else cout << "Listen was successful" << endl;

			FD_ZERO(&readfds);

			//wait loop

			while(1)

			{

				FD_SET(s,&readfds);  //always check the listener

				if(!(outfds=select(infds,&readfds,NULL,NULL,tp))) {}

				else if (outfds == SOCKET_ERROR) throw "failure in Select";

				else if (FD_ISSET(s,&readfds))  cout << "got a connection request" << endl; 

				//Found a connection request, try to accept. 

				if((s1=accept(s,&ca.generic,&calen))==INVALID_SOCKET)
					throw "Couldn't accept connection\n";

				//Connection request accepted.
				/*
				cout<<"accepted connection from "<<inet_ntoa(ca.ca_in.sin_addr)<<":"
					<<hex<<htons(ca.ca_in.sin_port)<<endl;
				*/

				//Accept file's direction 
				if((ibytesrecv = recv(s1,szbuffer,sizeof(szbuffer),0)) == SOCKET_ERROR)
					throw "Receive error in server program\n";
				string fileDirection=szbuffer;
				cout << "This is the file direction: " << fileDirection<< endl;

				// Send file direction back to client
				if((ibytessent = send(s1,szbuffer,sizeof(szbuffer),0))==SOCKET_ERROR)
					throw "error in send in server program\n";

				if (fileDirection == "put" || fileDirection =="get"){
				
					//Accept file name
				if((ibytesrecv = recv(s1,szbuffer,sizeof(szbuffer),0)) == SOCKET_ERROR)
					throw "Receive error in server program\n";
				//Print file name 
				filename=szbuffer;
				cout << "This is the file name: " << filename<< endl;

				// Send file name back to client
				if((ibytessent = send(s1,szbuffer,sizeof(szbuffer),0))==SOCKET_ERROR)
					throw "error in send in server program\n";
				
				}
						
				// 'put' option selected. Receive file from client
				if (fileDirection == "put"){

				
					// Accept file size 
					if((ibytesrecv = recv(s1,szbuffer,sizeof(szbuffer),0)) == SOCKET_ERROR)
						throw "Receive error in server program\n";
					//Print file size 
					int fileSize = atoi(szbuffer); 
					cout << "This is the file size: " << fileSize<< endl;

					// Send file size back to client
					if((ibytessent = send(s1,szbuffer,sizeof(szbuffer),0))==SOCKET_ERROR)
						throw "error in send in server program\n";

					//Accept packet amounts
					int packetAmt=0;
					if((ibytesrecv = recv(s1,szbuffer,sizeof(szbuffer),0)) == SOCKET_ERROR)
						throw "Receive error in server program\n";
					packetAmt=atoi(szbuffer); 
					cout << "This is the number of packets received: " << packetAmt<< endl;
			
					//Accept file
					ofstream myfile(filename, ios::binary); 
					
					int ctr=0; 
					char * memblock	= new char [1024];
					if (myfile.is_open()){
						if (packetAmt==0){
							//ibytessent= recv(s1,memblock,PACKETSIZE,0);
							//myfile.write(memblock, sizeof(memblock)); 
							cout<<"PACKET AMT is 0!"<<endl;
						}
						for (int i=0; i<packetAmt; i++){
							memset(szbuffer, '\0', 1024); 
							ibytessent= recv(s1,memblock,PACKETSIZE,0);
							cout<<"Iteration number "<<ctr<<" Amount received is: "<<ibytessent<<endl;
							myfile.write(memblock, PACKETSIZE); 
							++ctr; 
						}
						//cout<<"Total amount of packets written is: "<<ctr<<endl; 
				
				
						//Accept remainder amounts
						int remainderPacket=0; 
						if((ibytesrecv = recv(s1,szbuffer,sizeof(szbuffer),0)) == SOCKET_ERROR)
							throw "Receive error in server program\n";
						remainderPacket=atoi(szbuffer); 
						cout << "This is the remainder received: " << remainderPacket<< endl;

				
						//Write remainder to the file
				
						memset(szbuffer, '\0', sizeof(szbuffer)); 
						ibytessent= recv(s1,szbuffer,sizeof(szbuffer),0);
						cout<<"This is the buffer size of remainder: "<<sizeof(szbuffer)<<endl; 
						myfile.write(szbuffer, remainderPacket); 
					}

					myfile.close();
					cin.get();
				}

				// 'GET' option selected. Sending file to client. 
				else if (fileDirection == "get"){
					// Open file on server side. Return error if it doesn't exist 
					// Same as put function on client side, but reversed 

					// send file size
					ifstream myfile(filename, ios::ate|ios::binary);
					//ifstream myfile(filename, ios::ate);
					ifstream::pos_type nonIntSize; 
					ifstream::pos_type currentPos;
					char buf[1024];
					if (myfile.is_open()){

						cout<<"Shouldn't be open"<<endl;
					int bytesLeft=0; 		
						nonIntSize=myfile.tellg(); 
						int fileSize =(int)nonIntSize; 
						cout<<"The file's size is "<<fileSize<<endl;
						ibytessent=send(s1,itoa(fileSize,buf,10),sizeof(buf),0);
		
						//receive file size
					ibytesrecv=0; 
					if((ibytesrecv = recv(s1,szbuffer,sizeof(szbuffer),0)) == SOCKET_ERROR)
						throw "Receive failed\n";
					else{
						int serverFileSize=atoi(szbuffer);
						cout<<"File size from server: " << serverFileSize<<endl;   
		
					}
				
					// split file into separate packets
						int packetAmt = fileSize/PACKETSIZE;
						cout<<"Number of packet amounts: "<<packetAmt<<endl; 
					// send packet amount to client
						itoa(packetAmt,buf,10);
						ibytessent=send(s1,buf,sizeof(buf),0);
					// send file to server  
						// reset pointer to start of document
						myfile.seekg(0,ios::beg);
			
						if (packetAmt == 0){
							//myfile.read(buf, PACKETSIZE); 
							//ibytessent= send(s1,buf,PACKETSIZE,0);
							//cout<<"Amount sent to server: "<<ibytessent<<endl; 
							cout<<"PACKET AMT 0!"<<endl;
						}
						for (int i=0; i<packetAmt; i++){
								memset(buf, '\0', PACKETSIZE); 
								myfile.read(buf, PACKETSIZE); 
								ibytessent= send(s1,buf,PACKETSIZE,0);
								cout<<"Bytes sent for iteration "<<i<<" is "<< ibytessent<<endl; 
						
						}
		
					// get remainder size that must be sent 
						int remainderSize = fileSize % PACKETSIZE; 
						cout<<"Remainder amount is: "<<remainderSize<<endl; 
						ibytessent=send(s1,itoa(remainderSize,buf,10),sizeof(buf),0);
		
					// send remainder to server
						// need to memset?
						memset(buf, '\0', sizeof(buf)); 
						myfile.read(buf, remainderSize);
						ibytessent=send(s1,buf,remainderSize,0);
						myfile.close();
						cin.get();
					}
					
					else{
						char errorMsg[50]="Error could not open file ";
						cout<<"Error could not open file "<<endl; 
						//sprintf(szbuffer,errorMsg);
						ibytessent= send(s1,errorMsg,50,0);

						cout<<"Exiting program "<<endl;
						cin.get();
						exit(1); 
					}

				}
				else if (fileDirection == "list"){
					ListDirectoryContents("G:\\My Documents\\Visual Studio 2012\\Projects\\asmt1_2.0\\serv");
					cin.get();

				}
			}
				
		} //try loop
	
		//Display needed error message.
		 
		catch(char* str) { cerr<<str<<WSAGetLastError()<<endl;}

		//close Client socket
		closesocket(s1);		

		//close server socket
		closesocket(s1);

		/* When done uninstall winsock.dll (WSACleanup()) and exit */ 
		WSACleanup();
		return 0;
	}




