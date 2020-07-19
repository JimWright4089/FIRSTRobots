package frc.robot.command;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.hal.HALUtil;

public class WaitForTime extends CommandBase {
    long mLengthOfTimeMs = 1000;
    long mStartOfTime = 0;
    long mHALStartOfTime = 0;

    public WaitForTime() {
    }

    public WaitForTime(long lengthOfTimeMs) {
        mLengthOfTimeMs = lengthOfTimeMs;
    }

    @Override
    public void initialize() {
        System.out.println("public void initialize()");
        mHALStartOfTime = HALUtil.getFPGATime();
        mStartOfTime = System.currentTimeMillis();
    }

    @Override
    public void execute() {
        System.out.print("public void execute(");
        System.out.print(System.currentTimeMillis() - mStartOfTime);
        System.out.print(" ");
        System.out.print((HALUtil.getFPGATime() - mHALStartOfTime)/1000.0);
        System.out.println(")");
    }

    @Override
    public boolean isFinished() {
        boolean finished = ((System.currentTimeMillis() - mStartOfTime)>mLengthOfTimeMs);
        System.out.print("public boolean isFinished(");
        System.out.print(finished);
        System.out.println(")");
        return finished;
    }

    @Override
    public void end(boolean interupted) {
        System.out.print("public void end(");
        System.out.print(interupted);
        System.out.println(")");
    }
}
