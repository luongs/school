/**
	Author: Sebastien Luong
	Description: Generic class to set up Character properties and to set up basic
				 actions such as equipping items. Base class for warrior and sorceress 
				 classes. 
**/

#ifndef CHARACTER_H
#define CHARACTER_H

#include<string>
#include "Equipment.h"
using std::string;

class Character{
private: 
	
	string alignment;
	int strength;
	int craft;
	int life;
	int fate;
	int gold; 
	int maxCarry;
	int carried;
	Equipment arr [4];

public:	
	Character();
	Character(string align, int str, int crft, int life, int fate, int gold,int maxC,int carried);
	
	void addEquipment(Equipment& e);
	void printCarried();
	//getter and setter methods
	
	string getAlignment();
	void setAlignment(string newAlign);
	
	int getStrength();
	void setStrength(int str);
	
	int getCraft();
	void setCraft(int craft);
	
	int getLife();
	void setLife(int life);
	
	int getFate();
	void setFate(int fate);
	
	int getGold();
	void setGold(int gold);

	int getMaxCarry();
	void setMaxCarry(int max);

	int getCarried();
	void setCarried(int carry);

	//Equipment [] getArr();
	//void setArr(Equipment& e);
};


#endif