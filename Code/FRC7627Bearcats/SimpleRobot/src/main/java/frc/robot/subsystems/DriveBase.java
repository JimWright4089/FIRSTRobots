package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;

public class DriveBase extends SubsystemBase {
  private final TalonFX mLeftMotor = new TalonFX(1);
  private final TalonFX mRightMotor = new TalonFX(2);

  /** Creates a new DriveSubsystem. */
  public DriveBase() {
    mRightMotor.setInverted(true);
  }
  
  public void tankDrive(double left, double right) {
    mLeftMotor.set(TalonFXControlMode.PercentOutput, left);
    mRightMotor.set(TalonFXControlMode.PercentOutput, right);
  }
}
