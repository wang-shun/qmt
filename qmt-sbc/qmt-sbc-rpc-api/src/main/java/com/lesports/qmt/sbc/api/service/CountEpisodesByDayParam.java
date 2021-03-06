/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.lesports.qmt.sbc.api.service;

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
/**
 * 取得若干天每天的节目数量
 * 
 */
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2017-1-11")
public class CountEpisodesByDayParam implements org.apache.thrift.TBase<CountEpisodesByDayParam, CountEpisodesByDayParam._Fields>, java.io.Serializable, Cloneable, Comparable<CountEpisodesByDayParam> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("CountEpisodesByDayParam");

  private static final org.apache.thrift.protocol.TField START_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("startDate", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DAYS_FIELD_DESC = new org.apache.thrift.protocol.TField("days", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField GAME_TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("gameType", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField LIVE_TYPE_PARAM_FIELD_DESC = new org.apache.thrift.protocol.TField("liveTypeParam", org.apache.thrift.protocol.TType.I32, (short)4);
  private static final org.apache.thrift.protocol.TField EPISODE_TYPE_PARAM_FIELD_DESC = new org.apache.thrift.protocol.TField("episodeTypeParam", org.apache.thrift.protocol.TType.I32, (short)5);
  private static final org.apache.thrift.protocol.TField LIVE_SHOW_STATUS_PARAM_FIELD_DESC = new org.apache.thrift.protocol.TField("liveShowStatusParam", org.apache.thrift.protocol.TType.I32, (short)6);
  private static final org.apache.thrift.protocol.TField TIMEZONE_FIELD_DESC = new org.apache.thrift.protocol.TField("timezone", org.apache.thrift.protocol.TType.I32, (short)7);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new CountEpisodesByDayParamStandardSchemeFactory());
    schemes.put(TupleScheme.class, new CountEpisodesByDayParamTupleSchemeFactory());
  }

  private String startDate; // optional
  private int days; // optional
  private long gameType; // optional
  private LiveTypeParam liveTypeParam; // optional
  private EpisodeTypeParam episodeTypeParam; // optional
  private LiveShowStatusParam liveShowStatusParam; // optional
  private int timezone; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    START_DATE((short)1, "startDate"),
    DAYS((short)2, "days"),
    GAME_TYPE((short)3, "gameType"),
    /**
     * 
     * @see LiveTypeParam
     */
    LIVE_TYPE_PARAM((short)4, "liveTypeParam"),
    /**
     * 
     * @see EpisodeTypeParam
     */
    EPISODE_TYPE_PARAM((short)5, "episodeTypeParam"),
    /**
     * 
     * @see LiveShowStatusParam
     */
    LIVE_SHOW_STATUS_PARAM((short)6, "liveShowStatusParam"),
    TIMEZONE((short)7, "timezone");

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
        case 1: // START_DATE
          return START_DATE;
        case 2: // DAYS
          return DAYS;
        case 3: // GAME_TYPE
          return GAME_TYPE;
        case 4: // LIVE_TYPE_PARAM
          return LIVE_TYPE_PARAM;
        case 5: // EPISODE_TYPE_PARAM
          return EPISODE_TYPE_PARAM;
        case 6: // LIVE_SHOW_STATUS_PARAM
          return LIVE_SHOW_STATUS_PARAM;
        case 7: // TIMEZONE
          return TIMEZONE;
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
  private static final int __DAYS_ISSET_ID = 0;
  private static final int __GAMETYPE_ISSET_ID = 1;
  private static final int __TIMEZONE_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.START_DATE,_Fields.DAYS,_Fields.GAME_TYPE,_Fields.LIVE_TYPE_PARAM,_Fields.EPISODE_TYPE_PARAM,_Fields.LIVE_SHOW_STATUS_PARAM,_Fields.TIMEZONE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.START_DATE, new org.apache.thrift.meta_data.FieldMetaData("startDate", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DAYS, new org.apache.thrift.meta_data.FieldMetaData("days", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.GAME_TYPE, new org.apache.thrift.meta_data.FieldMetaData("gameType", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.LIVE_TYPE_PARAM, new org.apache.thrift.meta_data.FieldMetaData("liveTypeParam", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, LiveTypeParam.class)));
    tmpMap.put(_Fields.EPISODE_TYPE_PARAM, new org.apache.thrift.meta_data.FieldMetaData("episodeTypeParam", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, EpisodeTypeParam.class)));
    tmpMap.put(_Fields.LIVE_SHOW_STATUS_PARAM, new org.apache.thrift.meta_data.FieldMetaData("liveShowStatusParam", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, LiveShowStatusParam.class)));
    tmpMap.put(_Fields.TIMEZONE, new org.apache.thrift.meta_data.FieldMetaData("timezone", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(CountEpisodesByDayParam.class, metaDataMap);
  }

  public CountEpisodesByDayParam() {
    this.timezone = 8;

  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public CountEpisodesByDayParam(CountEpisodesByDayParam other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetStartDate()) {
      this.startDate = other.startDate;
    }
    this.days = other.days;
    this.gameType = other.gameType;
    if (other.isSetLiveTypeParam()) {
      this.liveTypeParam = other.liveTypeParam;
    }
    if (other.isSetEpisodeTypeParam()) {
      this.episodeTypeParam = other.episodeTypeParam;
    }
    if (other.isSetLiveShowStatusParam()) {
      this.liveShowStatusParam = other.liveShowStatusParam;
    }
    this.timezone = other.timezone;
  }

  public CountEpisodesByDayParam deepCopy() {
    return new CountEpisodesByDayParam(this);
  }

  @Override
  public void clear() {
    this.startDate = null;
    setDaysIsSet(false);
    this.days = 0;
    setGameTypeIsSet(false);
    this.gameType = 0;
    this.liveTypeParam = null;
    this.episodeTypeParam = null;
    this.liveShowStatusParam = null;
    this.timezone = 8;

  }

  public String getStartDate() {
    return this.startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public void unsetStartDate() {
    this.startDate = null;
  }

  /** Returns true if field startDate is set (has been assigned a value) and false otherwise */
  public boolean isSetStartDate() {
    return this.startDate != null;
  }

  public void setStartDateIsSet(boolean value) {
    if (!value) {
      this.startDate = null;
    }
  }

  public int getDays() {
    return this.days;
  }

  public void setDays(int days) {
    this.days = days;
    setDaysIsSet(true);
  }

  public void unsetDays() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DAYS_ISSET_ID);
  }

  /** Returns true if field days is set (has been assigned a value) and false otherwise */
  public boolean isSetDays() {
    return EncodingUtils.testBit(__isset_bitfield, __DAYS_ISSET_ID);
  }

  public void setDaysIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DAYS_ISSET_ID, value);
  }

  public long getGameType() {
    return this.gameType;
  }

  public void setGameType(long gameType) {
    this.gameType = gameType;
    setGameTypeIsSet(true);
  }

  public void unsetGameType() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __GAMETYPE_ISSET_ID);
  }

  /** Returns true if field gameType is set (has been assigned a value) and false otherwise */
  public boolean isSetGameType() {
    return EncodingUtils.testBit(__isset_bitfield, __GAMETYPE_ISSET_ID);
  }

  public void setGameTypeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __GAMETYPE_ISSET_ID, value);
  }

  /**
   * 
   * @see LiveTypeParam
   */
  public LiveTypeParam getLiveTypeParam() {
    return this.liveTypeParam;
  }

  /**
   * 
   * @see LiveTypeParam
   */
  public void setLiveTypeParam(LiveTypeParam liveTypeParam) {
    this.liveTypeParam = liveTypeParam;
  }

  public void unsetLiveTypeParam() {
    this.liveTypeParam = null;
  }

  /** Returns true if field liveTypeParam is set (has been assigned a value) and false otherwise */
  public boolean isSetLiveTypeParam() {
    return this.liveTypeParam != null;
  }

  public void setLiveTypeParamIsSet(boolean value) {
    if (!value) {
      this.liveTypeParam = null;
    }
  }

  /**
   * 
   * @see EpisodeTypeParam
   */
  public EpisodeTypeParam getEpisodeTypeParam() {
    return this.episodeTypeParam;
  }

  /**
   * 
   * @see EpisodeTypeParam
   */
  public void setEpisodeTypeParam(EpisodeTypeParam episodeTypeParam) {
    this.episodeTypeParam = episodeTypeParam;
  }

  public void unsetEpisodeTypeParam() {
    this.episodeTypeParam = null;
  }

  /** Returns true if field episodeTypeParam is set (has been assigned a value) and false otherwise */
  public boolean isSetEpisodeTypeParam() {
    return this.episodeTypeParam != null;
  }

  public void setEpisodeTypeParamIsSet(boolean value) {
    if (!value) {
      this.episodeTypeParam = null;
    }
  }

  /**
   * 
   * @see LiveShowStatusParam
   */
  public LiveShowStatusParam getLiveShowStatusParam() {
    return this.liveShowStatusParam;
  }

  /**
   * 
   * @see LiveShowStatusParam
   */
  public void setLiveShowStatusParam(LiveShowStatusParam liveShowStatusParam) {
    this.liveShowStatusParam = liveShowStatusParam;
  }

  public void unsetLiveShowStatusParam() {
    this.liveShowStatusParam = null;
  }

  /** Returns true if field liveShowStatusParam is set (has been assigned a value) and false otherwise */
  public boolean isSetLiveShowStatusParam() {
    return this.liveShowStatusParam != null;
  }

  public void setLiveShowStatusParamIsSet(boolean value) {
    if (!value) {
      this.liveShowStatusParam = null;
    }
  }

  public int getTimezone() {
    return this.timezone;
  }

  public void setTimezone(int timezone) {
    this.timezone = timezone;
    setTimezoneIsSet(true);
  }

  public void unsetTimezone() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TIMEZONE_ISSET_ID);
  }

  /** Returns true if field timezone is set (has been assigned a value) and false otherwise */
  public boolean isSetTimezone() {
    return EncodingUtils.testBit(__isset_bitfield, __TIMEZONE_ISSET_ID);
  }

  public void setTimezoneIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TIMEZONE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case START_DATE:
      if (value == null) {
        unsetStartDate();
      } else {
        setStartDate((String)value);
      }
      break;

    case DAYS:
      if (value == null) {
        unsetDays();
      } else {
        setDays((Integer)value);
      }
      break;

    case GAME_TYPE:
      if (value == null) {
        unsetGameType();
      } else {
        setGameType((Long)value);
      }
      break;

    case LIVE_TYPE_PARAM:
      if (value == null) {
        unsetLiveTypeParam();
      } else {
        setLiveTypeParam((LiveTypeParam)value);
      }
      break;

    case EPISODE_TYPE_PARAM:
      if (value == null) {
        unsetEpisodeTypeParam();
      } else {
        setEpisodeTypeParam((EpisodeTypeParam)value);
      }
      break;

    case LIVE_SHOW_STATUS_PARAM:
      if (value == null) {
        unsetLiveShowStatusParam();
      } else {
        setLiveShowStatusParam((LiveShowStatusParam)value);
      }
      break;

    case TIMEZONE:
      if (value == null) {
        unsetTimezone();
      } else {
        setTimezone((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case START_DATE:
      return getStartDate();

    case DAYS:
      return Integer.valueOf(getDays());

    case GAME_TYPE:
      return Long.valueOf(getGameType());

    case LIVE_TYPE_PARAM:
      return getLiveTypeParam();

    case EPISODE_TYPE_PARAM:
      return getEpisodeTypeParam();

    case LIVE_SHOW_STATUS_PARAM:
      return getLiveShowStatusParam();

    case TIMEZONE:
      return Integer.valueOf(getTimezone());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case START_DATE:
      return isSetStartDate();
    case DAYS:
      return isSetDays();
    case GAME_TYPE:
      return isSetGameType();
    case LIVE_TYPE_PARAM:
      return isSetLiveTypeParam();
    case EPISODE_TYPE_PARAM:
      return isSetEpisodeTypeParam();
    case LIVE_SHOW_STATUS_PARAM:
      return isSetLiveShowStatusParam();
    case TIMEZONE:
      return isSetTimezone();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof CountEpisodesByDayParam)
      return this.equals((CountEpisodesByDayParam)that);
    return false;
  }

  public boolean equals(CountEpisodesByDayParam that) {
    if (that == null)
      return false;

    boolean this_present_startDate = true && this.isSetStartDate();
    boolean that_present_startDate = true && that.isSetStartDate();
    if (this_present_startDate || that_present_startDate) {
      if (!(this_present_startDate && that_present_startDate))
        return false;
      if (!this.startDate.equals(that.startDate))
        return false;
    }

    boolean this_present_days = true && this.isSetDays();
    boolean that_present_days = true && that.isSetDays();
    if (this_present_days || that_present_days) {
      if (!(this_present_days && that_present_days))
        return false;
      if (this.days != that.days)
        return false;
    }

    boolean this_present_gameType = true && this.isSetGameType();
    boolean that_present_gameType = true && that.isSetGameType();
    if (this_present_gameType || that_present_gameType) {
      if (!(this_present_gameType && that_present_gameType))
        return false;
      if (this.gameType != that.gameType)
        return false;
    }

    boolean this_present_liveTypeParam = true && this.isSetLiveTypeParam();
    boolean that_present_liveTypeParam = true && that.isSetLiveTypeParam();
    if (this_present_liveTypeParam || that_present_liveTypeParam) {
      if (!(this_present_liveTypeParam && that_present_liveTypeParam))
        return false;
      if (!this.liveTypeParam.equals(that.liveTypeParam))
        return false;
    }

    boolean this_present_episodeTypeParam = true && this.isSetEpisodeTypeParam();
    boolean that_present_episodeTypeParam = true && that.isSetEpisodeTypeParam();
    if (this_present_episodeTypeParam || that_present_episodeTypeParam) {
      if (!(this_present_episodeTypeParam && that_present_episodeTypeParam))
        return false;
      if (!this.episodeTypeParam.equals(that.episodeTypeParam))
        return false;
    }

    boolean this_present_liveShowStatusParam = true && this.isSetLiveShowStatusParam();
    boolean that_present_liveShowStatusParam = true && that.isSetLiveShowStatusParam();
    if (this_present_liveShowStatusParam || that_present_liveShowStatusParam) {
      if (!(this_present_liveShowStatusParam && that_present_liveShowStatusParam))
        return false;
      if (!this.liveShowStatusParam.equals(that.liveShowStatusParam))
        return false;
    }

    boolean this_present_timezone = true && this.isSetTimezone();
    boolean that_present_timezone = true && that.isSetTimezone();
    if (this_present_timezone || that_present_timezone) {
      if (!(this_present_timezone && that_present_timezone))
        return false;
      if (this.timezone != that.timezone)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_startDate = true && (isSetStartDate());
    list.add(present_startDate);
    if (present_startDate)
      list.add(startDate);

    boolean present_days = true && (isSetDays());
    list.add(present_days);
    if (present_days)
      list.add(days);

    boolean present_gameType = true && (isSetGameType());
    list.add(present_gameType);
    if (present_gameType)
      list.add(gameType);

    boolean present_liveTypeParam = true && (isSetLiveTypeParam());
    list.add(present_liveTypeParam);
    if (present_liveTypeParam)
      list.add(liveTypeParam.getValue());

    boolean present_episodeTypeParam = true && (isSetEpisodeTypeParam());
    list.add(present_episodeTypeParam);
    if (present_episodeTypeParam)
      list.add(episodeTypeParam.getValue());

    boolean present_liveShowStatusParam = true && (isSetLiveShowStatusParam());
    list.add(present_liveShowStatusParam);
    if (present_liveShowStatusParam)
      list.add(liveShowStatusParam.getValue());

    boolean present_timezone = true && (isSetTimezone());
    list.add(present_timezone);
    if (present_timezone)
      list.add(timezone);

    return list.hashCode();
  }

  @Override
  public int compareTo(CountEpisodesByDayParam other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetStartDate()).compareTo(other.isSetStartDate());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStartDate()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.startDate, other.startDate);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDays()).compareTo(other.isSetDays());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDays()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.days, other.days);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGameType()).compareTo(other.isSetGameType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGameType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.gameType, other.gameType);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLiveTypeParam()).compareTo(other.isSetLiveTypeParam());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLiveTypeParam()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.liveTypeParam, other.liveTypeParam);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetEpisodeTypeParam()).compareTo(other.isSetEpisodeTypeParam());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEpisodeTypeParam()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.episodeTypeParam, other.episodeTypeParam);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetLiveShowStatusParam()).compareTo(other.isSetLiveShowStatusParam());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetLiveShowStatusParam()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.liveShowStatusParam, other.liveShowStatusParam);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTimezone()).compareTo(other.isSetTimezone());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTimezone()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.timezone, other.timezone);
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
    StringBuilder sb = new StringBuilder("CountEpisodesByDayParam(");
    boolean first = true;

    if (isSetStartDate()) {
      sb.append("startDate:");
      if (this.startDate == null) {
        sb.append("null");
      } else {
        sb.append(this.startDate);
      }
      first = false;
    }
    if (isSetDays()) {
      if (!first) sb.append(", ");
      sb.append("days:");
      sb.append(this.days);
      first = false;
    }
    if (isSetGameType()) {
      if (!first) sb.append(", ");
      sb.append("gameType:");
      sb.append(this.gameType);
      first = false;
    }
    if (isSetLiveTypeParam()) {
      if (!first) sb.append(", ");
      sb.append("liveTypeParam:");
      if (this.liveTypeParam == null) {
        sb.append("null");
      } else {
        sb.append(this.liveTypeParam);
      }
      first = false;
    }
    if (isSetEpisodeTypeParam()) {
      if (!first) sb.append(", ");
      sb.append("episodeTypeParam:");
      if (this.episodeTypeParam == null) {
        sb.append("null");
      } else {
        sb.append(this.episodeTypeParam);
      }
      first = false;
    }
    if (isSetLiveShowStatusParam()) {
      if (!first) sb.append(", ");
      sb.append("liveShowStatusParam:");
      if (this.liveShowStatusParam == null) {
        sb.append("null");
      } else {
        sb.append(this.liveShowStatusParam);
      }
      first = false;
    }
    if (isSetTimezone()) {
      if (!first) sb.append(", ");
      sb.append("timezone:");
      sb.append(this.timezone);
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

  private static class CountEpisodesByDayParamStandardSchemeFactory implements SchemeFactory {
    public CountEpisodesByDayParamStandardScheme getScheme() {
      return new CountEpisodesByDayParamStandardScheme();
    }
  }

  private static class CountEpisodesByDayParamStandardScheme extends StandardScheme<CountEpisodesByDayParam> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, CountEpisodesByDayParam struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // START_DATE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.startDate = iprot.readString();
              struct.setStartDateIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DAYS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.days = iprot.readI32();
              struct.setDaysIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // GAME_TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.gameType = iprot.readI64();
              struct.setGameTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // LIVE_TYPE_PARAM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.liveTypeParam = com.lesports.qmt.sbc.api.service.LiveTypeParam.findByValue(iprot.readI32());
              struct.setLiveTypeParamIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // EPISODE_TYPE_PARAM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.episodeTypeParam = com.lesports.qmt.sbc.api.service.EpisodeTypeParam.findByValue(iprot.readI32());
              struct.setEpisodeTypeParamIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // LIVE_SHOW_STATUS_PARAM
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.liveShowStatusParam = com.lesports.qmt.sbc.api.service.LiveShowStatusParam.findByValue(iprot.readI32());
              struct.setLiveShowStatusParamIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // TIMEZONE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.timezone = iprot.readI32();
              struct.setTimezoneIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, CountEpisodesByDayParam struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.startDate != null) {
        if (struct.isSetStartDate()) {
          oprot.writeFieldBegin(START_DATE_FIELD_DESC);
          oprot.writeString(struct.startDate);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetDays()) {
        oprot.writeFieldBegin(DAYS_FIELD_DESC);
        oprot.writeI32(struct.days);
        oprot.writeFieldEnd();
      }
      if (struct.isSetGameType()) {
        oprot.writeFieldBegin(GAME_TYPE_FIELD_DESC);
        oprot.writeI64(struct.gameType);
        oprot.writeFieldEnd();
      }
      if (struct.liveTypeParam != null) {
        if (struct.isSetLiveTypeParam()) {
          oprot.writeFieldBegin(LIVE_TYPE_PARAM_FIELD_DESC);
          oprot.writeI32(struct.liveTypeParam.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.episodeTypeParam != null) {
        if (struct.isSetEpisodeTypeParam()) {
          oprot.writeFieldBegin(EPISODE_TYPE_PARAM_FIELD_DESC);
          oprot.writeI32(struct.episodeTypeParam.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.liveShowStatusParam != null) {
        if (struct.isSetLiveShowStatusParam()) {
          oprot.writeFieldBegin(LIVE_SHOW_STATUS_PARAM_FIELD_DESC);
          oprot.writeI32(struct.liveShowStatusParam.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetTimezone()) {
        oprot.writeFieldBegin(TIMEZONE_FIELD_DESC);
        oprot.writeI32(struct.timezone);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class CountEpisodesByDayParamTupleSchemeFactory implements SchemeFactory {
    public CountEpisodesByDayParamTupleScheme getScheme() {
      return new CountEpisodesByDayParamTupleScheme();
    }
  }

  private static class CountEpisodesByDayParamTupleScheme extends TupleScheme<CountEpisodesByDayParam> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, CountEpisodesByDayParam struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetStartDate()) {
        optionals.set(0);
      }
      if (struct.isSetDays()) {
        optionals.set(1);
      }
      if (struct.isSetGameType()) {
        optionals.set(2);
      }
      if (struct.isSetLiveTypeParam()) {
        optionals.set(3);
      }
      if (struct.isSetEpisodeTypeParam()) {
        optionals.set(4);
      }
      if (struct.isSetLiveShowStatusParam()) {
        optionals.set(5);
      }
      if (struct.isSetTimezone()) {
        optionals.set(6);
      }
      oprot.writeBitSet(optionals, 7);
      if (struct.isSetStartDate()) {
        oprot.writeString(struct.startDate);
      }
      if (struct.isSetDays()) {
        oprot.writeI32(struct.days);
      }
      if (struct.isSetGameType()) {
        oprot.writeI64(struct.gameType);
      }
      if (struct.isSetLiveTypeParam()) {
        oprot.writeI32(struct.liveTypeParam.getValue());
      }
      if (struct.isSetEpisodeTypeParam()) {
        oprot.writeI32(struct.episodeTypeParam.getValue());
      }
      if (struct.isSetLiveShowStatusParam()) {
        oprot.writeI32(struct.liveShowStatusParam.getValue());
      }
      if (struct.isSetTimezone()) {
        oprot.writeI32(struct.timezone);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, CountEpisodesByDayParam struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(7);
      if (incoming.get(0)) {
        struct.startDate = iprot.readString();
        struct.setStartDateIsSet(true);
      }
      if (incoming.get(1)) {
        struct.days = iprot.readI32();
        struct.setDaysIsSet(true);
      }
      if (incoming.get(2)) {
        struct.gameType = iprot.readI64();
        struct.setGameTypeIsSet(true);
      }
      if (incoming.get(3)) {
        struct.liveTypeParam = com.lesports.qmt.sbc.api.service.LiveTypeParam.findByValue(iprot.readI32());
        struct.setLiveTypeParamIsSet(true);
      }
      if (incoming.get(4)) {
        struct.episodeTypeParam = com.lesports.qmt.sbc.api.service.EpisodeTypeParam.findByValue(iprot.readI32());
        struct.setEpisodeTypeParamIsSet(true);
      }
      if (incoming.get(5)) {
        struct.liveShowStatusParam = com.lesports.qmt.sbc.api.service.LiveShowStatusParam.findByValue(iprot.readI32());
        struct.setLiveShowStatusParamIsSet(true);
      }
      if (incoming.get(6)) {
        struct.timezone = iprot.readI32();
        struct.setTimezoneIsSet(true);
      }
    }
  }

}

