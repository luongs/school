#ifndef WARRIOR_H
#define WARRIOR_H

#include "Character.h"
#include <string>


class Warrior: public Character{
private: 
	string abil;

public:	
	Warrior();

	string getAbility();
	void setAbility(string abil);
};

#endif