#include<iostream>
#include<string>
#include "Sorceress.h"
#include "Character.h"
using namespace std;




Sorceress::Sorceress():Character(){
	setAlignment("evil");
	setStrength(2);
	setCraft(4);
	setLife(4);
	setFate(3);
	setGold(1);
	abilities="You begin the game with one spell. \
			  \n When you attack another character, you may choose to make the attack \
			  \n psychic combat. You may not do this when you are attacked by another character. \
			  \n You may attempt to beguile a character that you land on, allowing you to take one gold \
			  \n or object of your choice. To do so, roll one die: you must roll a 6 to beguile \
			  \n a good character; 5 or 6 for a neutral character; or a 4,5, or 6 for an evil character. \
			  \n You may take any one follower, except for the maiden, unicorn or princess from a \
			  \n character that you land on.";
}

string Sorceress::getAbility(){
	return abilities;
}

void Sorceress::setAbility(string abil){
	abilities=abil;
}