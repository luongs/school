#ifndef	EQUIPMENT_H
#define EQUIPMENT_H

#include <string>
#include <iostream>
#include "Character.h"

using std::string;
using std::endl;
using std::cout;

class Equipment{
public: 
		Equipment();
		void print();

		string getDescription();
		void setDescription(string x);

		string getEquipName();
		void setEquipName(string x);

		int getEncounter();
		void setEncounter(int x);

protected: 
		string equipName;
		string description;
		int encounter;
		int * 
		
};

class Armour: public Equipment{

public: 
		Armour();
		Armour(Character&);
};

class Axe: public Equipment{

public: 
		Axe();
		Axe(Character&);
};

class Helmet: public Equipment{

public: 
		Helmet();
		Helmet(Character&);

};

class HolyLance: public Equipment{

public: 
		HolyLance();
		HolyLance(Character&);

};

class RuneSword: public Equipment{

public: 
		RuneSword();
		RuneSword(Character&);

};

class Shield: public Equipment{

public: 
		Shield();
		Shield(Character&);
};

class Sword: public Equipment{

public: 
		Sword();
		Sword(Character&;
};


#endif