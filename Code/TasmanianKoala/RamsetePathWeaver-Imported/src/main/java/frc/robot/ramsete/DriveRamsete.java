package frc.robot.ramsete;

import frc.robot.subsystems.DriveSubsystem;
import frc.robot.trajectory.DefaultTrajectory;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.controller.PIDController;

import static frc.robot.Constants.AutoConstants.kRamseteB;
import static frc.robot.Constants.AutoConstants.kRamseteZeta;
import static frc.robot.Constants.DriveConstants.ksVolts;
import static frc.robot.Constants.DriveConstants.kvVoltSecondsPerMeter;
import static frc.robot.Constants.DriveConstants.kaVoltSecondsSquaredPerMeter;
import static frc.robot.Constants.DriveConstants.kDriveKinematics;
import static frc.robot.Constants.DriveConstants.kPDriveVel;

public class DriveRamsete {
    private final DriveSubsystem mRobotDrive = DriveSubsystem.getInstance();
    private Trajectory mTrajectory = DefaultTrajectory.getTrajectory();

    public DriveRamsete() {
    }

    public DriveRamsete(Trajectory trajectory) {
        mTrajectory = trajectory;
    }

    public Command getRamsete()
    {
        RamseteCommand ramseteCommand = new RamseteCommand(
            mTrajectory,
            mRobotDrive::getPose,
            new RamseteController(
                    kRamseteB, 
                    kRamseteZeta),
            new SimpleMotorFeedforward(ksVolts,
                                       kvVoltSecondsPerMeter,
                                       kaVoltSecondsSquaredPerMeter),
            kDriveKinematics,
            mRobotDrive::getWheelSpeeds,
            new PIDController(kPDriveVel, 0, 0),
            new PIDController(kPDriveVel, 0, 0),
            // RamseteCommand passes volts to the callback
            mRobotDrive::tankDriveVolts,
            mRobotDrive
        );
    
        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> mRobotDrive.tankDriveVolts(0, 0));
    }
}