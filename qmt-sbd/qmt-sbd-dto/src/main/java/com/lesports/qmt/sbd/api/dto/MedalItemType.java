/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbd.api.dto;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum MedalItemType implements org.apache.thrift.TEnum {
  TEAM(1),
  DICT(2);

  private final int value;

  private MedalItemType(int value) {
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
  public static MedalItemType findByValue(int value) { 
    switch (value) {
      case 1:
        return TEAM;
      case 2:
        return DICT;
      default:
        return null;
    }
  }
}
