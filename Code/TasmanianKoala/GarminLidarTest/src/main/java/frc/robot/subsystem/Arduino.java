package frc.robot.subsystem;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.hal.I2CJNI;

public class Arduino{

	private static final byte k_deviceAddress = 0x62;
	private final ByteBuffer m_buffer = ByteBuffer.allocateDirect(6);
    private final byte m_port;
    private int dist1 = 0;
    private int dist2 = 0;
    private int dist3 = 0;

	public Arduino(Port port) {
		m_port = (byte) port.value;
		I2CJNI.i2CInitialize(m_port);
	}

	public void getDistance() {
		readIt(0x8f);
        dist1 = m_buffer.getShort(0);
        dist2 = m_buffer.getShort(2);
        dist3 = m_buffer.getShort(4);
	}

	public int getDistance1() {
		return dist1;
	}

	public int getDistance2() {
		return dist2;
	}

	public int getDistance3() {
		return dist3;
	}

	private void readIt(int address) {
		m_buffer.put(0, (byte) address);
		I2CJNI.i2CWrite(m_port, k_deviceAddress, m_buffer, (byte) 1);
		I2CJNI.i2CRead(m_port, k_deviceAddress, m_buffer, (byte) 6);
	}
};