/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbc.api.common;


public enum VideoContentType implements org.apache.thrift.TEnum {
  RECORD(0),
  HIGHLIGHTS(1),
  MATCH_REPORT(2),
  FEATURE(3),
  MATCH_PIECE(4),
  FEATURE_PIECE(5),
  FEATURE_TIDBITS(6),
  PLAN(7),
  OTHER(20);

  private final int value;

  private VideoContentType(int value) {
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
  public static VideoContentType findByValue(int value) { 
    switch (value) {
      case 0:
        return RECORD;
      case 1:
        return HIGHLIGHTS;
      case 2:
        return MATCH_REPORT;
      case 3:
        return FEATURE;
      case 4:
        return MATCH_PIECE;
      case 5:
        return FEATURE_PIECE;
      case 6:
        return FEATURE_TIDBITS;
      case 7:
        return PLAN;
      case 20:
        return OTHER;
      default:
        return null;
    }
  }
}
