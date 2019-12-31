
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import frc.robot.subsystems.DriveTrain;

public class DriveWithController extends CommandBase {

	private double leftPct, rghtPct;
	private XboxController driveController;
	private DriveTrain driveTrain;

	/**
	 * Drives the robot using an Xbox controller.  Left joystick Y-axis = left motors, Right joystick Y-axis = right motors.
	 * @param driveController xBox controller to use
	 * @param driveTrain drive subsystem to use
	 */
	public DriveWithController(XboxController driveController, DriveTrain driveTrain) {
		this.driveController = driveController;
		this.driveTrain = driveTrain;
		addRequirements(driveTrain);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	public void execute() {
		/* get Xbox stick values */
		leftPct = -1 * driveController.getY(Hand.kLeft);  /* positive is forward */
		rghtPct = -1 * driveController.getY(Hand.kRight); /* positive is right */

		/* deadband gamepad 10% */
		if (Math.abs(leftPct) < 0.10) {
			leftPct = 0;
		}
		if (Math.abs(rghtPct) < 0.10) {
			rghtPct = 0;
		}

		/* drive robot */
		driveTrain.tankDrive(leftPct, rghtPct, true);

		/* print debug info to console if either bumper is held down */
		boolean btnLeftBumper = driveController.getBumper(Hand.kLeft);
		boolean btnRightBumper = driveController.getBumper(Hand.kRight);

		if (btnLeftBumper || btnRightBumper) {
			System.out.println(" leftPct:" + leftPct + " rightPct:" + rghtPct);
			driveTrain.print();
		}
	}

}
