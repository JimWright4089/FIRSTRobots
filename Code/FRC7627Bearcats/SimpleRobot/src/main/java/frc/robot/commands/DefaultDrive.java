package frc.robot.commands;

import frc.robot.Constants;
import frc.robot.subsystems.DriveBase;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.math.MathUtil;

public class DefaultDrive extends CommandBase {
    private final DriveBase mDriveBase;

    private final DoubleSupplier mLeftSupplier;
    private final DoubleSupplier mRightSupplier;
    private final BooleanSupplier mSlowModeSupplier;
    private final BooleanSupplier mFastModeSupplier;

    public DefaultDrive(DriveBase driveBase,
            DoubleSupplier leftSupplier,
            DoubleSupplier rightSupplier,
            BooleanSupplier slowModeSupplier,
            BooleanSupplier fastModeSupplier) {
        mDriveBase = driveBase;
        mLeftSupplier = leftSupplier;
        mRightSupplier = rightSupplier;
        mFastModeSupplier = fastModeSupplier;
        mSlowModeSupplier = slowModeSupplier;

        addRequirements(driveBase);
    }

    @Override
    public void execute() {
        double leftDrive = MathUtil.applyDeadband(mLeftSupplier.getAsDouble(), Constants.DriveConstants.kDeadBand);
        double rightDrive = MathUtil.applyDeadband(mRightSupplier.getAsDouble(), Constants.DriveConstants.kDeadBand);

        System.out.print(leftDrive);
        System.out.print(" ");
        System.out.println(rightDrive);

        if (true == mFastModeSupplier.getAsBoolean()) {
            mDriveBase.tankDrive(   leftDrive * Constants.DriveConstants.kFastDrive, 
                                    rightDrive * Constants.DriveConstants.kFastDrive);
        }
        else
        if (true == mSlowModeSupplier.getAsBoolean()) {
            mDriveBase.tankDrive(   leftDrive * Constants.DriveConstants.kSlowDrive, 
                                    rightDrive * Constants.DriveConstants.kSlowDrive);
        }
        else {
            mDriveBase.tankDrive(   leftDrive * Constants.DriveConstants.kNormalDrive, 
                                    rightDrive * Constants.DriveConstants.kNormalDrive);
        }	
        
    }

    @Override
    public void end(boolean interrupted) {
        mDriveBase.tankDrive(0, 0);
    }
}