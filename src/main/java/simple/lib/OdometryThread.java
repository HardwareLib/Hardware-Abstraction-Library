// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.lib;

import java.util.Queue;
import java.util.function.DoubleSupplier;

/** Add your docs here. */
public interface OdometryThread {
    public abstract Queue<Double> registerSignal(DoubleSupplier signal);
    public abstract Queue<Double> makeTimestampQueue();
}
