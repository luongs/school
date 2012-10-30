#include "Equipment.h"


Equipment::Equipment(){
	description="If you are defeated in battle and just lost a life, roll 1 die. If you roll a 4, 5, or 6, the Armour protected you and you did not lose that life, though you still lost the battle.";
	encounter=5;
	
}

void Equipment::print(){
	cout<<equipName<<" is equipped."<<endl;

}

string Equipment::getDescription(){
	return description;

}

void Equipment::setDescription(string x){
	description=x;
}

int Equipment:: getEncounter(){
	return encounter;
}

void Equipment:: setEncounter(int x){
	encounter=x;
}

string Equipment:: getEquipName(){
	return equipName;
}

void Equipment:: setEquipName(string x){
	equipName=x;
}

Armour::Armour(){
	description="If you are defeated in battle and just lost a life, roll 1 die. If you roll a 4, 5, or 6, the Armour protected you and you did not lose that life, though you still lost the battle.";
	encounter=5;
	setEquipName("Armour");
}

Armour::Armour(Character & player){
	description="If you are defeated in battle and just lost a life, roll 1 die. If you roll a 4, 5, or 6, the Armour protected you and you did not lose that life, though you still lost the battle.";
	encounter=5;
	setEquipName("Armour");
}

// Only allow one weapon benefit increase
Axe::Axe(Character & player){
	description = "Add 1 to your Strength during battle. \n You may build a Raft when you are in the Woods or the Forest.";
	setEquipName("Axe");
	player.setStrength(+1);
}

Helmet::Helmet(){
	description="If you are defeated in battle and just lost a life, roll 1 die. If you roll a 6, the Helmet protected you and you did not lose that life, though you still lost the battle.";
	encounter=5;
	setEquipName("Helmet");
}

HolyLance::HolyLance(){
	description = "No evil character may have the Holy Lance. \n Add 1 to your Strength during battle. \n Add 3 to your Strength during battle against Dragons.";
	setEquipName("Holy Lance");

}
// Add boolean for dragons.
HolyLance::HolyLance(Character & player){
	description = "No evil character may have the Holy Lance. \n Add 1 to your Strength during battle. \n Add 3 to your Strength during battle against Dragons.";
	setEquipName("Holy Lance");
	
	if(player.getAlignment() != "evil")
		player.setStrength(+1);
	else
		cout<<"May not equip on evil character."<<endl;
}

RuneSword::RuneSword(){
	description = "No good character may have the Runesword. \n Add 1 to your Strength during battle. \n When you use the Runesword in battle to defeat an Enemy or another character and then cause him to lose a life, you gain 1 life.";
	setEquipName("Rune Sword");
}

RuneSword::RuneSword(Character & player){
	description = "No good character may have the Runesword. \n Add 1 to your Strength during battle. \n When you use the Runesword in battle to defeat an Enemy or another character and then cause him to lose a life, you gain 1 life.";
	setEquipName("Rune Sword");

	if(player.getAlignment() != "good")
		player.setStrength(+1);
	else
		cout<<"May not equip on good character."<<endl;
}

Shield::Shield(){
	description="If you are defeated in battle and just lost a life, roll 1 die. If you roll a 5 or 6, the Shield protected you and you did not lose that life, though you still lost the battle.";
	setEquipName("Shield");
	
}

Sword::Sword(){
	description = "Add 1 to your Strength during battle.";
	setEquipName("Sword");
}

Sword::Sword(Character & player){
	description = "Add 1 to your Strength during battle.";
	setEquipName("Sword");
	player.setStrength(+1);
}