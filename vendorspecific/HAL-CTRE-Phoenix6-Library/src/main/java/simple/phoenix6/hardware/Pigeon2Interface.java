// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.phoenix6.hardware;

import static edu.wpi.first.units.Units.RadiansPerSecond;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import simple.lib.gyro.util.GyroConfig;
import simple.lib.gyro.util.GyroInterface;
import simple.lib.logging.data.GyroData;
import simple.phoenix6.utility.Phoenix6ConfigUtility;
import simple.phoenix6.utility.PhoenixUtil;

/** Add your docs here. */
public class Pigeon2Interface implements GyroInterface {
    private Pigeon2 pigeon;

    private StatusSignal<Angle> yaw;
    private StatusSignal<Angle> pitch;
    private StatusSignal<Angle> roll;

    private StatusSignal<AngularVelocity> yawVelocity;
    private StatusSignal<AngularVelocity> pitchVelocity;
    private StatusSignal<AngularVelocity> rollVelocity;

    public Pigeon2Interface(int id, GyroConfig config) {
        pigeon = new Pigeon2(id, PhoenixUtil.getCAN(config.canbus));
        yaw = pigeon.getYaw();
        pitch = pigeon.getPitch();
        roll = pigeon.getRoll();

        yawVelocity = pigeon.getAngularVelocityZWorld();
        rollVelocity = pigeon.getAngularVelocityXWorld();
        pitchVelocity = pigeon.getAngularVelocityYWorld();
    }

    @Override
    public void configure(GyroConfig config) {
        PhoenixUtil.tryUntilOkay(() -> pigeon.getConfigurator().apply(Phoenix6ConfigUtility.getPigeon2Configuration(config),0.25), 5);
    }

    @Override
    public Rotation2d getHeading() {
        return new Rotation2d(yaw.getValue());
    }

    @Override
    public Rotation3d getOrientation() {
        return new Rotation3d(roll.getValue(), pitch.getValue(), yaw.getValue());
    }

    @Override
    public void getData(GyroData data) {
        BaseStatusSignal.refreshAll(yaw,pitch,roll,yawVelocity,pitchVelocity,rollVelocity);
        data.connected.update(pigeon.isConnected());
        data.alive.update(BaseStatusSignal.isAllGood(pitch,yaw,roll,pitchVelocity,yawVelocity,rollVelocity));
        data.heading.update(getHeading());
        data.headingChange.update(new Rotation2d(yawVelocity.getValue().in(RadiansPerSecond)));
        data.orientation.update(getOrientation());
        data.angularVelocity.update(new Rotation3d(rollVelocity.getValue().in(RadiansPerSecond), pitchVelocity.getValue().in(RadiansPerSecond), yawVelocity.getValue().in(RadiansPerSecond)));
    }
}
