package com.github.pizzacodr.autologpodbean;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.Sources;

@Sources({ "file:${user.dir}/autologpodbean.properties", 
"file:${user.home}/autologpodbean.properties"})

interface ConfigFile extends Config {
	
	boolean isHeadless();
	String chromeDriveLocation();
	String username();
	String password();
	String streamFullTitle();
	String streamPartialTitleRightAfterDayOfTheWeek();
	long streamingTimeInMinutes();
}
