package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Lidar;
import frc.robot.utils.DriveMath;

/**
    * A command that will turn the robot to the specified angle using a motion profile.
    */
public class FollowLeftWall extends CommandBase {
    double mTarget = 500;
    double mCurrent = 10000;
    DriveSubsystem mDrive;
    Lidar mLidar;
    double mCenter = 0;
    double mSpeed = 0.6;
    double mSlowSpeed = 0.35;
    double mCorrection = 0.04;
    int mFrontCount = 0;

    public FollowLeftWall(double target, DriveSubsystem drive, Lidar lidar) {
        mTarget = target;
        mDrive = drive;
        mLidar = lidar;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void execute() {
        if((true == mLidar.is60Stale())&&(true == mLidar.is5Stale())&&(true == mLidar.is65Stale()))
        {
            System.out.format("%b %b %b \n",
            mLidar.is5Stale(),
            mLidar.is60Stale(),
            mLidar.is65Stale());
            mDrive.tankDrive(mSlowSpeed,mSlowSpeed);
        }
        else
        {
            double center = 0;
            if(false == mLidar.is60Stale())
            {
                center = mLidar.get60();
            }
            else
            {
                if(false == mLidar.is65Stale())
                {
                    center = mLidar.get65();
                }
                else
                {
                    center = mLidar.get55();
                }
            }

            center = (center - mTarget)/10000;
            mCenter = DriveMath.Cap(center, mCorrection);

            System.out.print("execute ");
            System.out.print(mTarget);
            System.out.print(" ");
            System.out.print(mLidar.get60());
            System.out.print(" ");
            System.out.print(mLidar.get0());

            if(mLidar.get0() < mTarget)
            {
                mFrontCount = 1;
            }

            if(mFrontCount>0)
            {
                mFrontCount--;
                System.out.print(" front ");
                mDrive.tankDrive(-mSlowSpeed,mSlowSpeed);
            }
            else
            {
                mDrive.tankDrive(mSpeed+mCenter,mSpeed-mCenter);
            }

            System.out.println(mCenter);
        }
    }
    @Override
    public void end(boolean interupted) {
        mDrive.tankDrive(0,0);
        System.out.print("End:");
        System.out.println(interupted);
    }
}