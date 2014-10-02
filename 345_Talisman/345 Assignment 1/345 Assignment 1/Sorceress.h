#ifndef SORCERESS_H
#define SORCERESS_H

#include "Character.h"


class Sorceress: public Character{
private: 
	string abilities;

public: 
	Sorceress();

	string getAbility();
	void setAbility(string abil);
};


#endif