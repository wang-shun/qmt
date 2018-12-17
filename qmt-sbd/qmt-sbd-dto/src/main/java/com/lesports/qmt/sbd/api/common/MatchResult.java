/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbd.api.common;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum MatchResult implements org.apache.thrift.TEnum {
  WIN(0),
  LOSE(1),
  FLAT(2);

  private final int value;

  private MatchResult(int value) {
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
  public static MatchResult findByValue(int value) { 
    switch (value) {
      case 0:
        return WIN;
      case 1:
        return LOSE;
      case 2:
        return FLAT;
      default:
        return null;
    }
  }
}
