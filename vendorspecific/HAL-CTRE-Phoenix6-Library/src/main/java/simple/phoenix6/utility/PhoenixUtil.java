// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package simple.phoenix6.utility;

import java.util.function.Supplier;

import com.ctre.phoenix6.StatusCode;

/** Add your docs here. */
public class PhoenixUtil {
    public static void tryUntilOkay(Supplier<StatusCode> function, int maxAttempts) {
        int attempts = 0;
        StatusCode currentStatus = StatusCode.StatusCodeNotInitialized;
        while (currentStatus != StatusCode.OK && attempts <= maxAttempts) {
            currentStatus = function.get();
            attempts++;
        }
    }
}
