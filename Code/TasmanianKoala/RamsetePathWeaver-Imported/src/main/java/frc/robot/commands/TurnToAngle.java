package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;

/**
 * A command that will turn the robot to the specified angle.
 */
public class TurnToAngle extends CommandBase {
  double mAngle = 0;
  double mPrevError = 0;
  double mSumError = 0;
  DriveSubsystem mDrive = DriveSubsystem.getInstance();

  public TurnToAngle(double targetAngleDegrees) {
    mAngle = targetAngleDegrees;
  }

  @Override
  public void execute()
  {
    double error = mAngle - mDrive.getHeading();

    mSumError += error;
    mSumError = 

    mPrevError = error;

    double output = (error * DriveConstants.kTurnP) + 
                    ((mPrevError - error) * DriveConstants.kTurnD) +
                    (mSumError * DriveConstants.kTurnI);


  }
 
 
    super(
        new PIDController(DriveConstants.kTurnP, DriveConstants.kTurnI, DriveConstants.kTurnD),
        // Close loop on heading
        drive::getHeading,
        // Set reference to target
        targetAngleDegrees,
        // Pipe output to turn robot
        output -> drive.arcadeDrive(0, output),
        // Require the drive
        drive);

    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(DriveConstants.kTurnToleranceDeg, DriveConstants.kTurnRateToleranceDegPerS);
  }

  @Override
  public boolean isFinished() {
    System.out.println(getController().getVelocityError()+" "+getController().getPositionError());

    return getController().atSetpoint();
  }
}
