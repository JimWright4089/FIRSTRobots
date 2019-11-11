package frc.robot.commands;

import frc.robot.*;
import frc.robot.paths.*;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class MoveForward extends CommandGroup {
    public MoveForward() {
      
    }

    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
		RobotMap.sPigeonIMU.setFusedHeading(0, 30);

		  addSequential(new SetAutoFinished(false));
	    
	    //move forward
	    addSequential(new DrivePathAction(new Move10Path60InPerSec()));
	    //addSequential(new DrivePathAction(new Red54Path60InPerSec()));
	    addSequential(new SetAutoFinished());
    }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
	  return RobotMap.isAutoFinished;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
	  end();
  }
}
