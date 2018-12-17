/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbd.api.dto;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2016-12-21")
public class TMatchTotalList implements org.apache.thrift.TBase<TMatchTotalList, TMatchTotalList._Fields>, java.io.Serializable, Cloneable, Comparable<TMatchTotalList> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TMatchTotalList");

  private static final org.apache.thrift.protocol.TField GOLD_MEDAL_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("goldMedalCount", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("date", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField OPENING_CEREMONY_FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("openingCeremonyFlag", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField CLOSING_CEREMONY_FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("closingCeremonyFlag", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField MATCH_DAY_FLAG_FIELD_DESC = new org.apache.thrift.protocol.TField("matchDayFlag", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TMatchTotalListStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TMatchTotalListTupleSchemeFactory());
  }

  private int goldMedalCount; // optional
  private String date; // optional
  private int openingCeremonyFlag; // optional
  private int closingCeremonyFlag; // optional
  private int matchDayFlag; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GOLD_MEDAL_COUNT((short)1, "goldMedalCount"),
    DATE((short)2, "date"),
    OPENING_CEREMONY_FLAG((short)3, "openingCeremonyFlag"),
    CLOSING_CEREMONY_FLAG((short)4, "closingCeremonyFlag"),
    MATCH_DAY_FLAG((short)5, "matchDayFlag");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // GOLD_MEDAL_COUNT
          return GOLD_MEDAL_COUNT;
        case 2: // DATE
          return DATE;
        case 3: // OPENING_CEREMONY_FLAG
          return OPENING_CEREMONY_FLAG;
        case 4: // CLOSING_CEREMONY_FLAG
          return CLOSING_CEREMONY_FLAG;
        case 5: // MATCH_DAY_FLAG
          return MATCH_DAY_FLAG;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __GOLDMEDALCOUNT_ISSET_ID = 0;
  private static final int __OPENINGCEREMONYFLAG_ISSET_ID = 1;
  private static final int __CLOSINGCEREMONYFLAG_ISSET_ID = 2;
  private static final int __MATCHDAYFLAG_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.GOLD_MEDAL_COUNT,_Fields.DATE,_Fields.OPENING_CEREMONY_FLAG,_Fields.CLOSING_CEREMONY_FLAG,_Fields.MATCH_DAY_FLAG};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GOLD_MEDAL_COUNT, new org.apache.thrift.meta_data.FieldMetaData("goldMedalCount", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DATE, new org.apache.thrift.meta_data.FieldMetaData("date", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OPENING_CEREMONY_FLAG, new org.apache.thrift.meta_data.FieldMetaData("openingCeremonyFlag", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.CLOSING_CEREMONY_FLAG, new org.apache.thrift.meta_data.FieldMetaData("closingCeremonyFlag", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.MATCH_DAY_FLAG, new org.apache.thrift.meta_data.FieldMetaData("matchDayFlag", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TMatchTotalList.class, metaDataMap);
  }

  public TMatchTotalList() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TMatchTotalList(TMatchTotalList other) {
    __isset_bitfield = other.__isset_bitfield;
    this.goldMedalCount = other.goldMedalCount;
    if (other.isSetDate()) {
      this.date = other.date;
    }
    this.openingCeremonyFlag = other.openingCeremonyFlag;
    this.closingCeremonyFlag = other.closingCeremonyFlag;
    this.matchDayFlag = other.matchDayFlag;
  }

  public TMatchTotalList deepCopy() {
    return new TMatchTotalList(this);
  }

  @Override
  public void clear() {
    setGoldMedalCountIsSet(false);
    this.goldMedalCount = 0;
    this.date = null;
    setOpeningCeremonyFlagIsSet(false);
    this.openingCeremonyFlag = 0;
    setClosingCeremonyFlagIsSet(false);
    this.closingCeremonyFlag = 0;
    setMatchDayFlagIsSet(false);
    this.matchDayFlag = 0;
  }

  public int getGoldMedalCount() {
    return this.goldMedalCount;
  }

  public void setGoldMedalCount(int goldMedalCount) {
    this.goldMedalCount = goldMedalCount;
    setGoldMedalCountIsSet(true);
  }

  public void unsetGoldMedalCount() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __GOLDMEDALCOUNT_ISSET_ID);
  }

  /** Returns true if field goldMedalCount is set (has been assigned a value) and false otherwise */
  public boolean isSetGoldMedalCount() {
    return EncodingUtils.testBit(__isset_bitfield, __GOLDMEDALCOUNT_ISSET_ID);
  }

  public void setGoldMedalCountIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __GOLDMEDALCOUNT_ISSET_ID, value);
  }

  public String getDate() {
    return this.date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public void unsetDate() {
    this.date = null;
  }

  /** Returns true if field date is set (has been assigned a value) and false otherwise */
  public boolean isSetDate() {
    return this.date != null;
  }

  public void setDateIsSet(boolean value) {
    if (!value) {
      this.date = null;
    }
  }

  public int getOpeningCeremonyFlag() {
    return this.openingCeremonyFlag;
  }

  public void setOpeningCeremonyFlag(int openingCeremonyFlag) {
    this.openingCeremonyFlag = openingCeremonyFlag;
    setOpeningCeremonyFlagIsSet(true);
  }

  public void unsetOpeningCeremonyFlag() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __OPENINGCEREMONYFLAG_ISSET_ID);
  }

  /** Returns true if field openingCeremonyFlag is set (has been assigned a value) and false otherwise */
  public boolean isSetOpeningCeremonyFlag() {
    return EncodingUtils.testBit(__isset_bitfield, __OPENINGCEREMONYFLAG_ISSET_ID);
  }

  public void setOpeningCeremonyFlagIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __OPENINGCEREMONYFLAG_ISSET_ID, value);
  }

  public int getClosingCeremonyFlag() {
    return this.closingCeremonyFlag;
  }

  public void setClosingCeremonyFlag(int closingCeremonyFlag) {
    this.closingCeremonyFlag = closingCeremonyFlag;
    setClosingCeremonyFlagIsSet(true);
  }

  public void unsetClosingCeremonyFlag() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CLOSINGCEREMONYFLAG_ISSET_ID);
  }

  /** Returns true if field closingCeremonyFlag is set (has been assigned a value) and false otherwise */
  public boolean isSetClosingCeremonyFlag() {
    return EncodingUtils.testBit(__isset_bitfield, __CLOSINGCEREMONYFLAG_ISSET_ID);
  }

  public void setClosingCeremonyFlagIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CLOSINGCEREMONYFLAG_ISSET_ID, value);
  }

  public int getMatchDayFlag() {
    return this.matchDayFlag;
  }

  public void setMatchDayFlag(int matchDayFlag) {
    this.matchDayFlag = matchDayFlag;
    setMatchDayFlagIsSet(true);
  }

  public void unsetMatchDayFlag() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __MATCHDAYFLAG_ISSET_ID);
  }

  /** Returns true if field matchDayFlag is set (has been assigned a value) and false otherwise */
  public boolean isSetMatchDayFlag() {
    return EncodingUtils.testBit(__isset_bitfield, __MATCHDAYFLAG_ISSET_ID);
  }

  public void setMatchDayFlagIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __MATCHDAYFLAG_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GOLD_MEDAL_COUNT:
      if (value == null) {
        unsetGoldMedalCount();
      } else {
        setGoldMedalCount((Integer)value);
      }
      break;

    case DATE:
      if (value == null) {
        unsetDate();
      } else {
        setDate((String)value);
      }
      break;

    case OPENING_CEREMONY_FLAG:
      if (value == null) {
        unsetOpeningCeremonyFlag();
      } else {
        setOpeningCeremonyFlag((Integer)value);
      }
      break;

    case CLOSING_CEREMONY_FLAG:
      if (value == null) {
        unsetClosingCeremonyFlag();
      } else {
        setClosingCeremonyFlag((Integer)value);
      }
      break;

    case MATCH_DAY_FLAG:
      if (value == null) {
        unsetMatchDayFlag();
      } else {
        setMatchDayFlag((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GOLD_MEDAL_COUNT:
      return Integer.valueOf(getGoldMedalCount());

    case DATE:
      return getDate();

    case OPENING_CEREMONY_FLAG:
      return Integer.valueOf(getOpeningCeremonyFlag());

    case CLOSING_CEREMONY_FLAG:
      return Integer.valueOf(getClosingCeremonyFlag());

    case MATCH_DAY_FLAG:
      return Integer.valueOf(getMatchDayFlag());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GOLD_MEDAL_COUNT:
      return isSetGoldMedalCount();
    case DATE:
      return isSetDate();
    case OPENING_CEREMONY_FLAG:
      return isSetOpeningCeremonyFlag();
    case CLOSING_CEREMONY_FLAG:
      return isSetClosingCeremonyFlag();
    case MATCH_DAY_FLAG:
      return isSetMatchDayFlag();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TMatchTotalList)
      return this.equals((TMatchTotalList)that);
    return false;
  }

  public boolean equals(TMatchTotalList that) {
    if (that == null)
      return false;

    boolean this_present_goldMedalCount = true && this.isSetGoldMedalCount();
    boolean that_present_goldMedalCount = true && that.isSetGoldMedalCount();
    if (this_present_goldMedalCount || that_present_goldMedalCount) {
      if (!(this_present_goldMedalCount && that_present_goldMedalCount))
        return false;
      if (this.goldMedalCount != that.goldMedalCount)
        return false;
    }

    boolean this_present_date = true && this.isSetDate();
    boolean that_present_date = true && that.isSetDate();
    if (this_present_date || that_present_date) {
      if (!(this_present_date && that_present_date))
        return false;
      if (!this.date.equals(that.date))
        return false;
    }

    boolean this_present_openingCeremonyFlag = true && this.isSetOpeningCeremonyFlag();
    boolean that_present_openingCeremonyFlag = true && that.isSetOpeningCeremonyFlag();
    if (this_present_openingCeremonyFlag || that_present_openingCeremonyFlag) {
      if (!(this_present_openingCeremonyFlag && that_present_openingCeremonyFlag))
        return false;
      if (this.openingCeremonyFlag != that.openingCeremonyFlag)
        return false;
    }

    boolean this_present_closingCeremonyFlag = true && this.isSetClosingCeremonyFlag();
    boolean that_present_closingCeremonyFlag = true && that.isSetClosingCeremonyFlag();
    if (this_present_closingCeremonyFlag || that_present_closingCeremonyFlag) {
      if (!(this_present_closingCeremonyFlag && that_present_closingCeremonyFlag))
        return false;
      if (this.closingCeremonyFlag != that.closingCeremonyFlag)
        return false;
    }

    boolean this_present_matchDayFlag = true && this.isSetMatchDayFlag();
    boolean that_present_matchDayFlag = true && that.isSetMatchDayFlag();
    if (this_present_matchDayFlag || that_present_matchDayFlag) {
      if (!(this_present_matchDayFlag && that_present_matchDayFlag))
        return false;
      if (this.matchDayFlag != that.matchDayFlag)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_goldMedalCount = true && (isSetGoldMedalCount());
    list.add(present_goldMedalCount);
    if (present_goldMedalCount)
      list.add(goldMedalCount);

    boolean present_date = true && (isSetDate());
    list.add(present_date);
    if (present_date)
      list.add(date);

    boolean present_openingCeremonyFlag = true && (isSetOpeningCeremonyFlag());
    list.add(present_openingCeremonyFlag);
    if (present_openingCeremonyFlag)
      list.add(openingCeremonyFlag);

    boolean present_closingCeremonyFlag = true && (isSetClosingCeremonyFlag());
    list.add(present_closingCeremonyFlag);
    if (present_closingCeremonyFlag)
      list.add(closingCeremonyFlag);

    boolean present_matchDayFlag = true && (isSetMatchDayFlag());
    list.add(present_matchDayFlag);
    if (present_matchDayFlag)
      list.add(matchDayFlag);

    return list.hashCode();
  }

  @Override
  public int compareTo(TMatchTotalList other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGoldMedalCount()).compareTo(other.isSetGoldMedalCount());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGoldMedalCount()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.goldMedalCount, other.goldMedalCount);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDate()).compareTo(other.isSetDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.date, other.date);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOpeningCeremonyFlag()).compareTo(other.isSetOpeningCeremonyFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOpeningCeremonyFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.openingCeremonyFlag, other.openingCeremonyFlag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetClosingCeremonyFlag()).compareTo(other.isSetClosingCeremonyFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetClosingCeremonyFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.closingCeremonyFlag, other.closingCeremonyFlag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMatchDayFlag()).compareTo(other.isSetMatchDayFlag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMatchDayFlag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.matchDayFlag, other.matchDayFlag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TMatchTotalList(");
    boolean first = true;

    if (isSetGoldMedalCount()) {
      sb.append("goldMedalCount:");
      sb.append(this.goldMedalCount);
      first = false;
    }
    if (isSetDate()) {
      if (!first) sb.append(", ");
      sb.append("date:");
      if (this.date == null) {
        sb.append("null");
      } else {
        sb.append(this.date);
      }
      first = false;
    }
    if (isSetOpeningCeremonyFlag()) {
      if (!first) sb.append(", ");
      sb.append("openingCeremonyFlag:");
      sb.append(this.openingCeremonyFlag);
      first = false;
    }
    if (isSetClosingCeremonyFlag()) {
      if (!first) sb.append(", ");
      sb.append("closingCeremonyFlag:");
      sb.append(this.closingCeremonyFlag);
      first = false;
    }
    if (isSetMatchDayFlag()) {
      if (!first) sb.append(", ");
      sb.append("matchDayFlag:");
      sb.append(this.matchDayFlag);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TMatchTotalListStandardSchemeFactory implements SchemeFactory {
    public TMatchTotalListStandardScheme getScheme() {
      return new TMatchTotalListStandardScheme();
    }
  }

  private static class TMatchTotalListStandardScheme extends StandardScheme<TMatchTotalList> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TMatchTotalList struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // GOLD_MEDAL_COUNT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.goldMedalCount = iprot.readI32();
              struct.setGoldMedalCountIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.date = iprot.readString();
              struct.setDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // OPENING_CEREMONY_FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.openingCeremonyFlag = iprot.readI32();
              struct.setOpeningCeremonyFlagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // CLOSING_CEREMONY_FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.closingCeremonyFlag = iprot.readI32();
              struct.setClosingCeremonyFlagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // MATCH_DAY_FLAG
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.matchDayFlag = iprot.readI32();
              struct.setMatchDayFlagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TMatchTotalList struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetGoldMedalCount()) {
        oprot.writeFieldBegin(GOLD_MEDAL_COUNT_FIELD_DESC);
        oprot.writeI32(struct.goldMedalCount);
        oprot.writeFieldEnd();
      }
      if (struct.date != null) {
        if (struct.isSetDate()) {
          oprot.writeFieldBegin(DATE_FIELD_DESC);
          oprot.writeString(struct.date);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetOpeningCeremonyFlag()) {
        oprot.writeFieldBegin(OPENING_CEREMONY_FLAG_FIELD_DESC);
        oprot.writeI32(struct.openingCeremonyFlag);
        oprot.writeFieldEnd();
      }
      if (struct.isSetClosingCeremonyFlag()) {
        oprot.writeFieldBegin(CLOSING_CEREMONY_FLAG_FIELD_DESC);
        oprot.writeI32(struct.closingCeremonyFlag);
        oprot.writeFieldEnd();
      }
      if (struct.isSetMatchDayFlag()) {
        oprot.writeFieldBegin(MATCH_DAY_FLAG_FIELD_DESC);
        oprot.writeI32(struct.matchDayFlag);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TMatchTotalListTupleSchemeFactory implements SchemeFactory {
    public TMatchTotalListTupleScheme getScheme() {
      return new TMatchTotalListTupleScheme();
    }
  }

  private static class TMatchTotalListTupleScheme extends TupleScheme<TMatchTotalList> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TMatchTotalList struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetGoldMedalCount()) {
        optionals.set(0);
      }
      if (struct.isSetDate()) {
        optionals.set(1);
      }
      if (struct.isSetOpeningCeremonyFlag()) {
        optionals.set(2);
      }
      if (struct.isSetClosingCeremonyFlag()) {
        optionals.set(3);
      }
      if (struct.isSetMatchDayFlag()) {
        optionals.set(4);
      }
      oprot.writeBitSet(optionals, 5);
      if (struct.isSetGoldMedalCount()) {
        oprot.writeI32(struct.goldMedalCount);
      }
      if (struct.isSetDate()) {
        oprot.writeString(struct.date);
      }
      if (struct.isSetOpeningCeremonyFlag()) {
        oprot.writeI32(struct.openingCeremonyFlag);
      }
      if (struct.isSetClosingCeremonyFlag()) {
        oprot.writeI32(struct.closingCeremonyFlag);
      }
      if (struct.isSetMatchDayFlag()) {
        oprot.writeI32(struct.matchDayFlag);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TMatchTotalList struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(5);
      if (incoming.get(0)) {
        struct.goldMedalCount = iprot.readI32();
        struct.setGoldMedalCountIsSet(true);
      }
      if (incoming.get(1)) {
        struct.date = iprot.readString();
        struct.setDateIsSet(true);
      }
      if (incoming.get(2)) {
        struct.openingCeremonyFlag = iprot.readI32();
        struct.setOpeningCeremonyFlagIsSet(true);
      }
      if (incoming.get(3)) {
        struct.closingCeremonyFlag = iprot.readI32();
        struct.setClosingCeremonyFlagIsSet(true);
      }
      if (incoming.get(4)) {
        struct.matchDayFlag = iprot.readI32();
        struct.setMatchDayFlagIsSet(true);
      }
    }
  }

}

