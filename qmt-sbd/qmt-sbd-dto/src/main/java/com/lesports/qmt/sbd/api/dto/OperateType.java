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

public enum OperateType implements org.apache.thrift.TEnum {
  AUTO(1),
  MANUAL(2);

  private final int value;

  private OperateType(int value) {
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
  public static OperateType findByValue(int value) { 
    switch (value) {
      case 1:
        return AUTO;
      case 2:
        return MANUAL;
      default:
        return null;
    }
  }
}