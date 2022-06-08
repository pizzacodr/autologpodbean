package com.github.pizzacodr.autologpodbean;

import org.aeonbits.owner.ConfigFactory;

public class App {

	public static void main(String[] args) throws InterruptedException {
		
		ConfigFile configFile = ConfigFactory.create(ConfigFile.class, System.getProperties());
		
		Podbean podbean = new Podbean(configFile);

		podbean.loginPage(configFile);

		podbean.selectLiveStreamFromProfile();

		podbean.startNewLiveShow();
		
		podbean.newLiveShowConfig(configFile);
		
		podbean.inviteCoHosts(); 
	    
		podbean.onAir(configFile);
	    
		podbean.offAir();
	}
}
