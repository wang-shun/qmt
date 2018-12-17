/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbc.api.service;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * 各种直播状态的请求
 * 
 */
public enum LiveShowStatusParam implements org.apache.thrift.TEnum {
  LIVE_NOT_START(0),
  LIVING(1),
  LIVE_END(2),
  LIVE_NOT_END(3);

  private final int value;

  private LiveShowStatusParam(int value) {
    this.value = value;
  }

  /**
   * Get the integer value of this enum value, as defined in the Thrift IDL.
   */
  public int getValue() {
    return value;
  }

  /**
   * Find a the enum type by its integer value, as defined in the Thrift IDL.
   * @return null if the value is not found.
   */
  public static LiveShowStatusParam findByValue(int value) { 
    switch (value) {
      case 0:
        return LIVE_NOT_START;
      case 1:
        return LIVING;
      case 2:
        return LIVE_END;
      case 3:
        return LIVE_NOT_END;
      default:
        return null;
    }
  }
}