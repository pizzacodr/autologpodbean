package com.github.pizzacodr.autologpodbean;

import org.aeonbits.owner.ConfigFactory;

public class App {

	public static void main(String[] args) throws InterruptedException {
		
		ConfigFile configFile = ConfigFactory.create(ConfigFile.class, System.getProperties());
		
		Podbean podbean = new Podbean(configFile.chromeDriveLocation(), configFile.isHeadless());

		podbean.loginPage(configFile.username(), configFile.password());
		podbean.selectLiveStreamFromProfile();
		podbean.startNewLiveShow();		
		podbean.newLiveShowConfig(configFile.streamFullTitle(), configFile.streamPartialTitleRightAfterDayOfTheWeek());		
		podbean.inviteCoHosts(); 	    
		podbean.onAir(configFile.streamingTimeInMinutes());	    
		podbean.offAir();		
		podbean.endProcess();
	}
}
