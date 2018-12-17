/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.config.api.dto;


import java.util.Map;
import java.util.HashMap;
import org.apache.thrift.TEnum;

public enum ActivityResourceType implements org.apache.thrift.TEnum {
  EPISODE(0),
  WEB_URL(1),
  POST(2),
  CAMP(4),
  FLOATER(5);

  private final int value;

  private ActivityResourceType(int value) {
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
  public static ActivityResourceType findByValue(int value) { 
    switch (value) {
      case 0:
        return EPISODE;
      case 1:
        return WEB_URL;
      case 2:
        return POST;
      case 4:
        return CAMP;
      case 5:
        return FLOATER;
      default:
        return null;
    }
  }
}
