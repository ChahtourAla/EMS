package io.openems.edge.battery.BlueCarbon;

import org.junit.Test;

import io.openems.edge.battery.BlueCarbon.enums.BatteryState;
import io.openems.edge.bridge.modbus.test.DummyModbusBridge;
import io.openems.edge.common.test.ComponentTest;
import io.openems.edge.common.test.DummyConfigurationAdmin;

public class BlueCarbonBatteryImplTest {

	private static final String BATTERY_ID = "battery0";
	private static final String MODBUS_ID = "modbus0";

	@Test
	public void test() throws Exception {
		new ComponentTest(new BlueCarbonBatteryImpl()) //
				.addReference("cm", new DummyConfigurationAdmin()) //
				.addReference("setModbus", new DummyModbusBridge(MODBUS_ID)) //
				.activate(MyConfig.create() //
						.setId(BATTERY_ID) //
						.setModbusId(MODBUS_ID) //
						.setBatteryState(BatteryState.DEFAULT) //
						.setErrorDelay(0) //
						.setMaxStartAttempts(0) //
						.setMaxStartTime(0) //
						.setPendingTolerance(0) //
						.setStartUnsuccessfulDelay(0) //
						.build()) //
		;
	}

}
