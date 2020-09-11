
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveTrain;

/**
 * A command that will drive the robot driveTrain.
 */
public class Drive extends CommandBase {
	private final DriveTrain driveTrain;
	private final double pwrLeft, pwrRight;

	/**
	 * Drive the drive Train until interrupted.
	 * @param powerLeft power to left side of drive train, -1.0 to +1.0 (+ = forward)
	 * @param powerRight power to right side of drive train, -1.0 to +1.0 (+ = forward)
	 * @param driveTrain The drive subsystem to use.
	 */
	public Drive(double powerLeft, double powerRight, DriveTrain driveTrain) {
		this.driveTrain = driveTrain;
		pwrLeft = powerLeft;
		pwrRight = powerRight;
		addRequirements(driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	public void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		driveTrain.tankDrive( pwrLeft, pwrRight );
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true or command is interrupted
	@Override
	public void end(boolean interrupted) {
	}

}
