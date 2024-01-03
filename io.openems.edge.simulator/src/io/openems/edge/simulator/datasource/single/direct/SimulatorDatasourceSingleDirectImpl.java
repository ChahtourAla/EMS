package io.openems.edge.simulator.datasource.single.direct;
import java.time.LocalDateTime;

import java.io.IOException;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.EventHandler;
import org.osgi.service.event.propertytypes.EventTopics;
import org.osgi.service.metatype.annotations.Designate;

import io.openems.edge.common.component.ComponentManager;
import io.openems.edge.common.component.OpenemsComponent;
import io.openems.edge.common.event.EdgeEventConstants;
import io.openems.edge.simulator.DataContainer;
import io.openems.edge.simulator.datasource.api.AbstractCsvDatasource;
import io.openems.edge.simulator.datasource.api.SimulatorDatasource;

@Designate(ocd = Config.class, factory = true)
@Component(//
		name = "Simulator.Datasource.Single.Direct", //
		immediate = true, //
		configurationPolicy = ConfigurationPolicy.REQUIRE //
)
@EventTopics({ //
		EdgeEventConstants.TOPIC_CYCLE_AFTER_WRITE //
})
public class SimulatorDatasourceSingleDirectImpl extends AbstractCsvDatasource
		implements SimulatorDatasourceSingleDirect, SimulatorDatasource, OpenemsComponent, EventHandler {

	@Reference
	private ComponentManager componentManager;

	private Config config;

	public SimulatorDatasourceSingleDirectImpl() {
		super(//
				OpenemsComponent.ChannelId.values(), //
				SimulatorDatasourceSingleDirect.ChannelId.values() //
		);
	}

	@Activate
	private void activate(ComponentContext context, Config config) throws NumberFormatException, IOException {
		this.config = config;
		super.activate(context, config.id(), config.alias(), config.enabled(), config.timeDelta());
	}

	@Override
	@Deactivate
	protected void deactivate() {
		super.deactivate();
	}

	@Override
	protected ComponentManager getComponentManager() {
		return this.componentManager;
	}
	@Override
	protected DataContainer getData() throws NumberFormatException, IOException {
		
		 LocalDateTime now = LocalDateTime.now();

	     int hour = now.getHour();
		var result = new DataContainer();
		
		
		int[] originalArray = {0,0,0,0,0,0,100,250,550,1150,2350,4750,2350,1150,550,250,100,0,0,0,0,0,0};
        
        int rank  =  hour-1;
        int[] newArray = new int[originalArray.length];
        int j =0;
        for (int i = rank; i < originalArray.length; i++) {
           newArray[j]=originalArray[i];
           j++;
        }
        for (int i = 0; i < rank; i++) {
            newArray[j]=originalArray[i];
            j++;   
         }
        
        
        
		for (int value : newArray) {
			result.addRecord(new Float[] { Float.valueOf(value) });
		}
		return result;
	}
	
	protected DataContainer getData2() throws NumberFormatException, IOException {
		var result = new DataContainer();
		
		
		for (int value : this.config.values()) {
			result.addRecord(new Float[] { Float.valueOf(value) });
		}
		return result;
	}

}