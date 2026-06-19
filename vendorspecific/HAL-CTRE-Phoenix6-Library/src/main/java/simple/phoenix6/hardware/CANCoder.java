// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.phoenix6.hardware;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.CANcoder;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;
import simple.lib.encoder.util.EncoderConfig;
import simple.lib.encoder.util.EncoderInterface;
import simple.lib.logging.data.EncoderData;
import simple.phoenix6.utility.Phoenix6ConfigUtility;
import simple.phoenix6.utility.PhoenixUtil;

/** Add your docs here. */
public class CANCoder implements EncoderInterface {

    private CANcoder encoder;

    private StatusSignal<Angle> position;
    private StatusSignal<AngularVelocity> velocity;

    public CANCoder(int id, EncoderConfig config) {
        encoder = new CANcoder(id,PhoenixUtil.getCAN(config.canbus));
        PhoenixUtil.tryUntilOkay(() -> encoder.getConfigurator().apply(Phoenix6ConfigUtility.getCaNcoderConfiguration(config),0.25), 5);
        position = encoder.getPosition();
        velocity = encoder.getVelocity();
    }

    @Override
    public void configure(EncoderConfig config) {
        PhoenixUtil.tryUntilOkay(() -> encoder.getConfigurator().apply(Phoenix6ConfigUtility.getCaNcoderConfiguration(config),0.25), 5);
    }

    @Override
    public void getData(EncoderData data) {
        BaseStatusSignal.refreshAll(position,velocity);
        data.connected.update(encoder.isConnected());
        data.alive.update(BaseStatusSignal.isAllGood(position,velocity));
        data.position.update(getPosition());
        data.velocity.update(getVelocity());
    }

    @Override
    public Angle getPosition() {
        return position.getValue();
    }

    @Override
    public AngularVelocity getVelocity() {
        return velocity.getValue();
    }
}
