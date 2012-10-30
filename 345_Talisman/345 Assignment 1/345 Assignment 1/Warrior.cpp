#include<iostream>
#include<string>
#include "Warrior.h"
#include "Character.h"
using namespace std;

Warrior::Warrior(): Character(){
	setAlignment("neutral");
	setStrength(4);
	setCraft(2);
	setLife(5);
	setFate(1);
	setGold(1);
	setAbility("You may roll two dice in battle and use the \
			  higher attack roll to determine your attack score. \
			  \n You may use two weapons at the same time.");


	/*alignment="neutral";
	strenght=4;
	craft=2;
	life=5;
	fate=1;
	gold=1;
	abilities="You may roll two dice in battle and use the \
			  higher attack roll to determine your attack score. \
			  \n You may use two weapons at the same time.";

	*/
}

string Warrior::getAbility(){
	return abil;
}

void Warrior::setAbility(string abil){
	this->abil=abil;
}