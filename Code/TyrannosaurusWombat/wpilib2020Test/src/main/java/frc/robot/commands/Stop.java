
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DriveTrain;

/**
 * A command that will stop the robot driveTrain.
 */
public class Stop extends CommandBase {
	private final DriveTrain driveTrain;

	/**
	 * Stops the drive Train.
	 * @param driveTrain The drive subsystem to use.
	 */
	public Stop(DriveTrain driveTrain) {
		this.driveTrain = driveTrain;
		addRequirements(driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	public void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		driveTrain.tankDrive(0,0);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	public boolean isFinished() {
		return true;
	}
	
	// Called once after isFinished returns true or command is interrupted
	@Override
	public void end(boolean interrupted) {
	}

}
