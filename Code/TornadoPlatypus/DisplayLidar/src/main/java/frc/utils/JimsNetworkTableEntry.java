package frc.utils;

import edu.wpi.first.networktables.*;
import edu.wpi.first.hal.HALUtil;

public class JimsNetworkTableEntry {
    static long mNetworkTableStaleMs = 100000;

    static public void setStaleMs(long staleMs)
    {
        mNetworkTableStaleMs = staleMs;
    }

    static public boolean isState(NetworkTableEntry entry)
    {
        if(HALUtil.getFPGATime()-entry.getLastChange()>mNetworkTableStaleMs)
        {
            return true;
        }
        return false;
    }
} 
