
package frc.robot.commands;

import frc.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetAutoFinished extends Command {
  
	private boolean autoFinished;
	
	public SetAutoFinished() {
		autoFinished = true;
	}
	
    public SetAutoFinished(boolean value) {
        autoFinished = value;
    }

    protected void initialize() {
    }

    protected void execute() {
    	RobotMap.isAutoFinished = autoFinished;
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    	
    }

    protected void interrupted() {
      end();
    }
}
