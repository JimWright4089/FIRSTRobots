package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Lidar;
import frc.robot.utils.DriveMath;

/**
    * A command that will turn the robot to the specified angle using a motion profile.
    */
public class MoveTo500mm extends CommandBase {
    double mTarget = 500;
    double mCurrent = 10000;
    DriveSubsystem mDrive;
    Lidar mLidar;
    double mCenter = 0;

    public MoveTo500mm(double target, DriveSubsystem drive, Lidar lidar) {
        mTarget = target;
        mDrive = drive;
        mLidar = lidar;
    }

    @Override
    public boolean isFinished() {
        return ((mCurrent < mTarget)&&(DriveMath.DeadBand(mCenter, .06) ==0.0));
    }

    @Override
    public void execute() {
        mCurrent = mLidar.get0();
        double center = (mLidar.get45() - mLidar.getNdeg45())/10000;
        mCenter = DriveMath.Cap(center, .05);

        System.out.print("execute ");
        System.out.print(mCurrent);
        System.out.print(" ");
        System.out.print(mTarget);
        System.out.print(" ");
        System.out.print(mLidar.get45());
        System.out.print(" ");
        System.out.print(mLidar.getNdeg45());

        if(mCurrent > mTarget)
        {
            mDrive.tankDrive(.3-mCenter, .3+mCenter);
        }
        else
        {
            System.out.print(" ");
            System.out.print(mCenter);
            System.out.print(" here ");
            center = mCenter * 30;
            center = DriveMath.Cap(center, .30);
            mDrive.tankDrive(-center, center);
        }
        System.out.print(" ");
        System.out.println(mCenter);

    }
    @Override
    public void end(boolean interupted) {
        mDrive.tankDrive(0,0);
        System.out.print("End:");
        System.out.println(interupted);
    }
}