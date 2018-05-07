package com.test.patterns.core;

public class AdapterTest {
	public static void main(String[] args) {
		FurnanceRegulatorySystem sys = new FurnanceRegulatorySystem();
		sys.regulateFurnanceTemperature();
	}
}

class FurnanceRegulatorySystem {
	public void regulateFurnanceTemperature() {
		FurnanceControllerAdapter fca = new FurnanceControllerAdapter();
		fca.controlFurnance(9);
	}
}

// --------------------------------------------------------------
class FurnanceControllerAdapter {
	ThirdPartyFurnanceController adaptee = new ThirdPartyFurnanceController();
	public void controlFurnance(int heatLevel) {
		// convert temperature from centigrade to fahrenheit formate
		HEATLEVEL internalheatLevel = HEATLEVEL.LOW;
		if (heatLevel < 150 && heatLevel > 100) {
			internalheatLevel = HEATLEVEL.MEDIUM;
		} else if (heatLevel > 150) {
			internalheatLevel = HEATLEVEL.HIGH;
		}
		adaptee.changeFurnanceTemperature(internalheatLevel);
	}
}

// ----------------------------------------------------------------
class ThirdPartyFurnanceController {
	public void changeFurnanceTemperature(HEATLEVEL heatLevel) {
		System.out.println("Changing furnance tempratire to : " + heatLevel);
	}
}

enum HEATLEVEL {
	LOW, MEDIUM, HIGH
}