#include <iostream>
#include <string>
#include "Character.h"
#include "Warrior.h"
#include "Sorceress.h"
#include "Equipment.h"

using std::cout;
using std::endl;

int main(){
	
	//Character a;
	Sorceress a;
	Warrior warr;

	Shield b;
	Sword c;
	Helmet d;
	
	a.addEquipment(b);
	a.addEquipment(c);
	a.addEquipment(d);
	a.addEquipment(d);
	a.addEquipment(d);
	a.printCarried();

	warr.addEquipment(c);
	cout<<"Displaying sword's effect on character: "<<endl;
	cout<<c.getDescription()<<endl;

	system("pause");
	return 0;
}
