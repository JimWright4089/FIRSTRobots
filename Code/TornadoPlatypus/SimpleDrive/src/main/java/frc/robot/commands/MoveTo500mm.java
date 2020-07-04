package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;

import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.Lidar;

/**
    * A command that will turn the robot to the specified angle using a motion profile.
    */
public class MoveTo500mm extends ProfiledPIDCommand {
    /**
    * Turns to robot to the center of the camera blob.
    *
    * @param targetAngleDegrees The angle to turn to
    * @param drive              The drive subsystem to use
    */
    public MoveTo500mm(double target, DriveSubsystem drive, Lidar lidar) {
    super(
        new ProfiledPIDController(DriveConstants.kMoveP, DriveConstants.kMoveI,
                                    DriveConstants.kMoveD, new TrapezoidProfile.Constraints(
            DriveConstants.kMaxMoveRateDegPerS,
            DriveConstants.kMaxMoveAccelerationDegPerSSquared)),
        // Close loop on heading
        lidar::get0,
        // Set reference to target
        target,
        // Pipe output to turn robot
        (output, setpoint) -> drive.tankDrive(-output, -output),
        // Require the drive
        drive);

    // Set the controller to be continuous (because it is an angle controller)
    getController().enableContinuousInput(-180, 180);
    // Set the controller tolerance - the delta tolerance ensures the robot is stationary at the
    // setpoint before it is considered as having reached the reference
    getController()
        .setTolerance(DriveConstants.kMoveToleranceDeg, DriveConstants.kMoveRateToleranceDegPerS);
    }

    @Override
    public boolean isFinished() {
    System.out.println("isfinished");
    return getController().atGoal();
    }
/*
    @Override
    public void execute() {
        System.out.println("execute");
    }
    @Override
    public void end(boolean interupted) {
        System.out.print("End:");
        System.out.println(interupted);
    }
*/
}