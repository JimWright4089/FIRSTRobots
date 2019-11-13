/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.DriverStation;

public class AutonomousRunner extends CommandGroup {
  /**
   * Add your docs here.
   */
  public AutonomousRunner() {
    
    String gameData = DriverStation.getInstance().getGameSpecificMessage();
    int counter = 0;
    while ((gameData == "" || gameData == null || gameData.length() != 1) && counter < 250) 
    {
      gameData = DriverStation.getInstance().getGameSpecificMessage();
      counter ++;
    }

    if(gameData.length()>0)
    {
      System.out.println("GameData:" + gameData);
      switch(gameData.charAt(0))
      {
        case('1'):
          addSequential(new FollowPath("BlueToDepotFront"));
          break;  
        case('2'):
          addSequential(new FollowPath("BlueToRocket2"));
          break;  
          case('3'):
          addSequential(new FollowPath("BlueToDepot2"));
          break;  
        case('T'):
          addSequential(new FollowPath("TenFeet")); 
          break;  
        default:
          addSequential(new FollowPath("BlueOffLevel2"));
          break;  
      }
    }
    else
    {
      System.out.println("No GameData!");
      addSequential(new FollowPath("BlueOffLevel2"));
    }

    // Add Commands here:
    // e.g. addSequential(new Command1());
    // addSequential(new Command2());
    // these will run in order.

    // To run multiple commands at the same time,
    // use addParallel()
    // e.g. addParallel(new Command1());
    // addSequential(new Command2());
    // Command1 and Command2 will run in parallel.

    // A command group will require all of the subsystems that each member
    // would require.
    // e.g. if Command1 requires chassis, and Command2 requires arm,
    // a CommandGroup containing them would require both the chassis and the
    // arm.
  }
}
