/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbd.api.service;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

/**
 * 分组类型
 * 
 */
public enum GroupTypeParam implements org.apache.thrift.TEnum {
  NATIONAL(1),
  DISCIPLINE(2);

  private final int value;

  private GroupTypeParam(int value) {
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
  public static GroupTypeParam findByValue(int value) { 
    switch (value) {
      case 1:
        return NATIONAL;
      case 2:
        return DISCIPLINE;
      default:
        return null;
    }
  }
}
