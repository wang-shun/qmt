/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbc.api.common;


public enum ResourceDataType implements org.apache.thrift.TEnum {
  COMMON_COMPETITION(0),
  COMMON_CONTENT(1),
  COMMON_CONTENT_LIST(2),
  DATA_LIST(3),
  DATA_RANK(4),
  COMPETITION_MODULE(5),
  PLAYER_MODULE(6),
  TV_DESKTOP_RECOMMAND_THEME(7);

  private final int value;

  private ResourceDataType(int value) {
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
  public static ResourceDataType findByValue(int value) { 
    switch (value) {
      case 0:
        return COMMON_COMPETITION;
      case 1:
        return COMMON_CONTENT;
      case 2:
        return COMMON_CONTENT_LIST;
      case 3:
        return DATA_LIST;
      case 4:
        return DATA_RANK;
      case 5:
        return COMPETITION_MODULE;
      case 6:
        return PLAYER_MODULE;
      case 7:
        return TV_DESKTOP_RECOMMAND_THEME;
      default:
        return null;
    }
  }
}
