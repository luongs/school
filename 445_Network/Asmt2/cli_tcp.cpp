// CLIENT TCP PROGRAM
// Revised and tidied up by
// J.W. Atwood
// 1999 June 30



char* getmessage(char *);



/* send and receive codes between client and server */
/* This is your basic WINSOCK shell */
#pragma comment( linker, "/defaultlib:ws2_32.lib" )
#include <winsock2.h>
#include <ws2tcpip.h>

#include <winsock.h>
#include <stdio.h>
#include <iostream>
#include <fstream>
#include <string.h>
#include <stack>
#include <windows.h>
#include <time.h>

#include <sys/types.h>



using namespace std;

//user defined port number
#define REQUEST_PORT 5000;
int port=REQUEST_PORT;
//socket data types
SOCKET s;
SOCKADDR_IN sa;         // filled by bind
SOCKADDR_IN sa_in;      // fill with server info, IP, port
SOCKADDR from;
int fromlen;

int timeout_recvfrom (int sock, char *buf, int *length, struct sockaddr_in *connection, int timeoutinseconds);

int threeWay(void);

//buffer data types
const int PACKETSIZE= 1024;
char szbuffer[PACKETSIZE];
char buf[PACKETSIZE]; 
char *buffer;
char filename[50];
char direction[4];
char getOption[]="get"; 
char put[]="put"; 
int fileType; 
int ibufferlen=0;
int ibytessent=0;
int ibytesrecv=0;
HANDLE hFind;
WIN32_FIND_DATA data;


#define STOPNWAIT 1 //Bits width For stop and wait
struct MESSAGE_FRAME
{
	unsigned char header; //whats header for ? the packet numbers?
	unsigned int snwseq:STOPNWAIT;  //snwseq is 1 bit
	char data[PACKETSIZE];//or error message
} message_frame;

struct THREE_WAY_HS{
	int client_number;
	int server_number;
	int direction;
	char file_name[50];
} three_way_hs;





//host data types
HOSTENT *hp;
HOSTENT *rp;
string str="hopi";
char infilename[50];
char localhost[11],
     remotehost[11];
//other

HANDLE test;

DWORD dwtest;

	char * itoa (int x, char *in, size_t sz) {
		_snprintf(in, sz, "%d", x);
		return in;
	}

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

/*
int timeout_recvfrom(int sock, void *buf, int * len, int flags, struct sockaddr *from, socklen_t *fromlen,  int timeoutinseconds) 
{
    fd_set socks;
    struct timeval t;
    FD_ZERO(&socks);
    FD_SET(sock, &socks);
    t.tv_sec = timeoutinseconds;
	if (select(sock+1 , &socks, NULL, NULL, &t) && recvfrom(sock,szbuffer,sizeof(szbuffer),0, (SOCKADDR*) & from, fromlen )!=-1) //only 1 socket and 1 connection here so its hard coded
        {
			 return 1;
        }
	
    else
        return 0;
}
*/

int threeWay(void)
{
	
	bool restart = true;

	while(restart)
	{
	// What the TA will ask for
	// 20% drop rate, 0. (Values are 2, 0).  
	// put/get of binary file - make sure it transfers properly
	// show part of receive where it compares packet number between both
		
		srand((unsigned)time(NULL));
		//three_way_hs.client_number = rand() % 256;
		int client_seq_number= rand() % 256;
		_itoa(client_seq_number,buf, 10);  
		cout<<"Three way handshake value "<<client_seq_number<<endl;
		cin.get(); 
		sendto(s, buf, sizeof(buf), 0,(SOCKADDR*) &sa_in, sizeof(sa_in)); //send client number to server
		// wait for server to return 3 way handshake value
	// change handshake value to 1 bit value
		
		//if(timeout_recvfrom(s,szbuffer,(int*) sizeof(szbuffer), 0, (SOCKADDR_IN*) &sa_in, sizeof(sa_in), 5) == 1) //if it hasnt timed out
		
	// use select to handle timeouts
		if(recvfrom(s,szbuffer,sizeof(szbuffer),0, &from, &fromlen) != SOCKET_ERROR)
		{
			//success
			int threeWayVal=atoi(szbuffer);
			cout << "This is the three way handshake value from the server: " << szbuffer<< endl;
			//if(three_way_hs.client_number == (int)(szbuffer)) //if the servers reply was consistent
			//three_way_hs.server_number = (int)szbuffer;
			sendto(s, (char *)three_way_hs.server_number, ibufferlen, 0,(SOCKADDR*) &sa_in, sizeof(sa_in)); //send number back to server
			restart = false;

		}
		else
		{
			//restart handshake since we timed out
		}
	}
	


	return 0;
	

}


int main(void){

	
	WSADATA wsadata;
	fromlen = sizeof(from);

	try {

		

		if (WSAStartup(0x0202,&wsadata)!=0){  
			cout<<"Error in starting WSAStartup()" << endl;
		} else {
			buffer="WSAStartup was successful\n";   
			WriteFile(test,buffer,sizeof(buffer),&dwtest,NULL); 
		}  


		//Display name of local host.

		gethostname(localhost,10);
		cout<<"Local host name is \"" << localhost << "\"" << endl;

		if((hp=gethostbyname(localhost)) == NULL) 
			throw "gethostbyname failed\n";

		//Ask for name of remote server

		cout << "Type FTP server name :" << flush ;   
		cin >> remotehost ;
		
		cout << "Type direction of transfer (get, put, list): "<<flush; 
		cin >> direction;
		
		if ((std::strcmp(direction,getOption)==0) || (std::strcmp(direction,put)==0)){
			cout << "Type name of file to be transferred: "<<flush; 
			cin >>filename;
			if  (std::strcmp(direction,put)==0){
				ifstream temp(filename,ios::binary||ios::ate); 
				if (!temp.is_open()){
					cout<<"Error opening file!"<<endl;
					cin.get();
					exit(1);
				}
			}
		}


		// make sure the correct input/output received
		if ((std::strcmp(direction,getOption)!=0) && (std::strcmp(direction,put)!=0) && (std::strcmp(direction,"list")!=0)) {
			cout<<"Error. Incorrect input, program will now exit"<<endl;
			cin.get(); 
			exit(1); 
		}
		else {

		
		if((rp=gethostbyname(remotehost)) == NULL)
			throw "remote gethostbyname failed\n";

		//Create the socket
		if((s = socket(AF_INET,SOCK_DGRAM,0))==INVALID_SOCKET) 
			throw "Socket failed\n";
		/* For UDP protocol replace SOCK_STREAM with SOCK_DGRAM */


		
		sa.sin_family = AF_INET;
		sa.sin_port = htons(port);
		sa.sin_addr.s_addr = htonl(INADDR_ANY); 



		//Specify server address for client to connect to server.
		memset(&sa_in,0,sizeof(sa_in));
		memcpy(&sa_in.sin_addr,rp->h_addr,rp->h_length);
		sa_in.sin_family = rp->h_addrtype;   
		sa_in.sin_port = htons(7000);  //5001 for the server, 7000 for the router

		//Display the host machine internet address

		cout << "Connecting to remote host:";
		cout << inet_ntoa(sa_in.sin_addr) << endl;

		//Connect Client to the server
		if (bind(s,(LPSOCKADDR)&sa,sizeof(sa)) == SOCKET_ERROR)
			throw "connect failed\n";

		

		threeWay();
		/*
		// send file's direction 
		sprintf(szbuffer,direction); 
		ibufferlen=strlen(szbuffer); 
		ibytessent = sendto(s,szbuffer, ibufferlen,0, (SOCKADDR*) &sa_in, sizeof(sa_in));
		if (ibytessent == SOCKET_ERROR)
			throw "Send failed\n"; 

		// receive file direction
		ibytesrecv=0; 
		if((ibytesrecv = recvfrom(s,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
			throw "Receive failed\n";
		else
			cout<<"Successful message replied from server: " << szbuffer<<endl;   

		if (std::strcmp(direction,put)==0 || std::strcmp(direction,getOption)==0)
		{
		// send file name
		sprintf(szbuffer,filename); 

		ibytessent=0;    
		ibufferlen = strlen(szbuffer);
		ibytessent = sendto(s,szbuffer,ibufferlen,0, (SOCKADDR*) &sa_in, sizeof(sa_in));
		if (ibytessent == SOCKET_ERROR)
			throw "Send failed\n";  

		// receive file name 
		ibytesrecv=0; 
		if((ibytesrecv = recvfrom(s,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
			throw "Receive failed\n";
		else
			cout<<"Successful message replied from server: " << szbuffer<<endl;   
		}
		
		// PUT option selected. Upload file to server
		if (std::strcmp(direction,put)==0){
			// send file size
			ifstream myfile(filename, ios::ate|ios::binary);
			
			ifstream::pos_type nonIntSize; 
			ifstream::pos_type currentPos;
			int bytesLeft=0; 
			if(myfile.is_open()){		
				nonIntSize=myfile.tellg(); 
				int fileSize =(int)nonIntSize; 
				cout<<"The file's size is "<<fileSize<<endl;
				ibytessent=sendto(s,_itoa(fileSize,buf,10),sizeof(buf),0, (SOCKADDR*) &sa_in, sizeof(sa_in));
		
				//receive file size
			ibytesrecv=0; 
			if((ibytesrecv = recvfrom(s,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
				throw "Receive failed\n";
			else{
				int serverFileSize=atoi(szbuffer);
				cout<<"File size from server: " << serverFileSize<<endl;   
		
			}
		
			// split file into separate packets
				int packetAmt = fileSize/PACKETSIZE;
				cout<<"Number of packet amounts: "<<packetAmt<<endl; 
			// send packet amount to serv
				_itoa(packetAmt,buf,10);
				ibytessent=sendto(s,buf,sizeof(buf),0,(SOCKADDR*) &sa_in, sizeof(sa_in));
			// send file to server  
				// reset pointer to start of document
				myfile.seekg(0,ios::beg);
			
				if (packetAmt == 0){
					//myfile.read(buf, PACKETSIZE); 
					//myfile2.write(buf, PACKETSIZE);
					//ibytessent= send(s,buf,PACKETSIZE,0);
					cout<<"Packet AMT is 0!"<<endl; 
				}
				else{
					for (int i=0; i<packetAmt; i++){
						memset(buf, '\0', PACKETSIZE); 
						myfile.read(buf, PACKETSIZE); 
						ibytessent= sendto(s,buf,PACKETSIZE,0, (SOCKADDR*) &sa_in, sizeof(sa_in));
						cout<<"Bytes sent for iteration "<<i<<" isL "<< ibytessent<<endl; 
				}
				}
		
			// get remainder size that must be sent 
				int remainderSize = fileSize % PACKETSIZE; 
				cout<<"Remainder amount is: "<<remainderSize<<endl; 
				ibytessent=sendto(s,_itoa(remainderSize,buf,10),sizeof(buf),0, (SOCKADDR*) &sa_in, sizeof(sa_in));
		
			// send remainder to server
				// need to memset?
				memset(buf, '\0', sizeof(buf)); 
				myfile.read(buf, remainderSize); 
				ibytessent=sendto(s,buf,remainderSize,0, (SOCKADDR*) &sa_in, sizeof(sa_in));
				myfile.close(); 
			}
			else{
				cout<<"Error could not open file "<<endl; 
				cout<<"Exiting program "<<endl;
				cin.get();
				exit(1); 
			}
			}
		// GET Option - Save binary file onto computer
		else if (std::strcmp(direction,getOption)==0){
								

					// Accept file size 
					if((ibytesrecv = recvfrom(s,szbuffer,sizeof(szbuffer),0,&from, &fromlen)) == SOCKET_ERROR)
						throw "Receive error in server program\n";
					//Print file size 
					if (sizeof(szbuffer) != PACKETSIZE){
						cout<< szbuffer <<endl;
					}
					else{
						int fileSize = atoi(szbuffer); 
						cout << szbuffer<< endl;
						if (strcmp(szbuffer, "Error could not open file ")==0)
							cout<<"Exiting program!"<<endl;
							cin.get();
							//exit(1);
						//cout<<sizeof(szbuffer)<<endl; 
					}
					// Send file size back to client
					if((ibytessent = sendto(s,szbuffer,sizeof(szbuffer),0, (SOCKADDR*) &sa_in, sizeof(sa_in)))==SOCKET_ERROR)
						throw "error in send in server program\n";

					//Accept packet amounts
					int packetAmt=0;
					if((ibytesrecv = recvfrom(s,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
						throw "Receive error in server program\n";
					packetAmt=atoi(szbuffer); 
					cout << "This is the number of packets received: " << packetAmt<< endl;
					//Accept file
					ofstream myfile(filename, ios::binary); 
					//ofstream myfile(filename); 

					int ctr=0; 
					char * memblock	= new char [1024];
					if (myfile.is_open()){
						if (packetAmt==0){
							//ibytessent= recv(s,memblock,PACKETSIZE,0);
							//myfile.write(memblock, sizeof(memblock)); 
							//cout<<"Amount received is "<<ibytessent<<endl; 
							cout<<"PACKET AMOUNT is 0! "<<endl;
						
						}
						for (int i=0; i<packetAmt; i++){
							memset(memblock, '\0', 1024); 
							ibytessent = recvfrom(s,memblock,PACKETSIZE,0, &from, &fromlen);
							//cout<<"Iteration number "<<ctr<<" Amount received is: "<<ibytessent<<endl;
							myfile.write(memblock, PACKETSIZE); 
							++ctr; 
						}
						//cout<<"Total amount of packets written is: "<<ctr<<endl; 
				
				
						//Accept remainder amounts
						int remainderPacket=0; 
						if((ibytesrecv = recvfrom(s,szbuffer,sizeof(szbuffer),0, &from, &fromlen)) == SOCKET_ERROR)
							throw "Receive error in server program\n";
						remainderPacket=atoi(szbuffer); 
						cout << "This is the remainder received: " << remainderPacket<< endl;

				
						//Write remainder to the file
				
						memset(szbuffer, '\0', sizeof(szbuffer)); 
						ibytessent= recvfrom(s,szbuffer,sizeof(szbuffer),0, &from, &fromlen);
						cout<<"This is the buffer size of remainder: "<<ibytessent<<endl; 
						myfile.write(szbuffer, remainderPacket); 
						
						myfile.close(); 
						cout<<"File closed properly"<<endl;
						cin.get();
					}
					else{
						ibytessent= recvfrom(s,szbuffer,sizeof(szbuffer),0, &from, &fromlen);
						cout<<szbuffer<<endl; 
						//cout<<"Error could not open file "<<endl; 
						//cout<<"Exiting program "<<endl;
						cin.get();
						//exit(1); 
				}
					

		}
		else if (std::strcmp(direction,"list")==0){
				ListDirectoryContents("C:\\Users\\Administrator\\Documents\\Visual Studio 2012\\Projects\\client");
				cin.get();
		}*/
	}
		

		//wait for reception of server response.		
		cin.get(); 

	} // try loop

	//Display any needed error response.

	catch (char *str) { cerr<<str<<":"<<dec<<WSAGetLastError()<<endl;}

	//close the client socket
	closesocket(s);

	/* When done uninstall winsock.dll (WSACleanup()) and exit */ 
	WSACleanup();  
	return 0;
}





