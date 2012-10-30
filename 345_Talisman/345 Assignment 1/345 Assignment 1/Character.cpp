#include "Character.h"
#include <stdlib.h>

// Constructors

Character::Character(){
	alignment="neutral";
	strength=0;
	craft=0;
	life=0;
	fate=0;
	gold=1;
	maxCarry=4;
	carried=0;
	//*arr=new Equipment[maxCarry];
}

Character::Character(string align, int str, int crft, int life, int fate, int gold, int maxC,int carried){
	alignment=align;
	strength=str;
	craft=crft;
	this->life=life;
	this->fate=fate;
	this->gold=gold;
	maxCarry=maxC;
	this->carried=carried;
	//*arr=new Equipment[maxCarry];
}

// Member functions

void Character::addEquipment(Equipment& e){
	if(carried<4) 
		arr[carried]= e;
		carried++;
}

void Character::printCarried(){
	for(int i=0;i<=carried;i++){
	if(i>=4) 
		cout<<"Maximum number of equipment carried reached"<<endl;
	else
		arr[i].print();
	}
}

// Getters and setter methods

string Character::getAlignment(){
	return alignment;
}

void Character::setAlignment(string align){
	alignment=align;
}

int Character::getStrength(){
	return strength;
}

void Character::setStrength(int str){
	strength=str;
}

int Character::getCraft(){
	return craft;
}

void Character::setCraft(int crft){
	craft=crft;
}

int Character::getLife(){
	return life;
}

void Character::setLife(int life){
	this->life=life;
}

int Character::getFate(){
	return fate;
}

void Character::setFate(int fate){
	this->fate=fate;
}

int Character::getGold(){
	return gold;
}

void Character::setGold(int gold){
	this->gold=gold;
}

int Character::getMaxCarry(){
	return maxCarry;
}

void Character::setMaxCarry(int max){
	maxCarry=max;
}

int Character::getCarried(){
	return carried;
}

void Character::setCarried(int x){
	carried=x;
}


