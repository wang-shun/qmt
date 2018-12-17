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
public class TMatchInfo implements org.apache.thrift.TBase<TMatchInfo, TMatchInfo._Fields>, java.io.Serializable, Cloneable, Comparable<TMatchInfo> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TMatchInfo");

  private static final org.apache.thrift.protocol.TField COMPETITOR_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("competitorId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField GROUND_FIELD_DESC = new org.apache.thrift.protocol.TField("ground", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField NEAR_MATCHES_FIELD_DESC = new org.apache.thrift.protocol.TField("nearMatches", org.apache.thrift.protocol.TType.LIST, (short)4);
  private static final org.apache.thrift.protocol.TField AFTER_MATCHES_FIELD_DESC = new org.apache.thrift.protocol.TField("afterMatches", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField STATS_FIELD_DESC = new org.apache.thrift.protocol.TField("stats", org.apache.thrift.protocol.TType.MAP, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TMatchInfoStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TMatchInfoTupleSchemeFactory());
  }

  private long competitorId; // required
  private com.lesports.qmt.sbd.api.common.GroundType ground; // optional
  private com.lesports.qmt.sbd.api.common.CompetitorType type; // optional
  private List<THistoryMatch> nearMatches; // optional
  private List<THistoryMatch> afterMatches; // optional
  private Map<String,String> stats; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    COMPETITOR_ID((short)1, "competitorId"),
    /**
     * 
     * @see com.lesports.qmt.sbd.api.common.GroundType
     */
    GROUND((short)2, "ground"),
    /**
     * 
     * @see com.lesports.qmt.sbd.api.common.CompetitorType
     */
    TYPE((short)3, "type"),
    NEAR_MATCHES((short)4, "nearMatches"),
    AFTER_MATCHES((short)5, "afterMatches"),
    STATS((short)6, "stats");

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
        case 1: // COMPETITOR_ID
          return COMPETITOR_ID;
        case 2: // GROUND
          return GROUND;
        case 3: // TYPE
          return TYPE;
        case 4: // NEAR_MATCHES
          return NEAR_MATCHES;
        case 5: // AFTER_MATCHES
          return AFTER_MATCHES;
        case 6: // STATS
          return STATS;
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
  private static final int __COMPETITORID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.GROUND,_Fields.TYPE,_Fields.NEAR_MATCHES,_Fields.AFTER_MATCHES,_Fields.STATS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.COMPETITOR_ID, new org.apache.thrift.meta_data.FieldMetaData("competitorId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.GROUND, new org.apache.thrift.meta_data.FieldMetaData("ground", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.lesports.qmt.sbd.api.common.GroundType.class)));
    tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.EnumMetaData(org.apache.thrift.protocol.TType.ENUM, com.lesports.qmt.sbd.api.common.CompetitorType.class)));
    tmpMap.put(_Fields.NEAR_MATCHES, new org.apache.thrift.meta_data.FieldMetaData("nearMatches", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, THistoryMatch.class))));
    tmpMap.put(_Fields.AFTER_MATCHES, new org.apache.thrift.meta_data.FieldMetaData("afterMatches", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, THistoryMatch.class))));
    tmpMap.put(_Fields.STATS, new org.apache.thrift.meta_data.FieldMetaData("stats", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING), 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TMatchInfo.class, metaDataMap);
  }

  public TMatchInfo() {
  }

  public TMatchInfo(
    long competitorId)
  {
    this();
    this.competitorId = competitorId;
    setCompetitorIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TMatchInfo(TMatchInfo other) {
    __isset_bitfield = other.__isset_bitfield;
    this.competitorId = other.competitorId;
    if (other.isSetGround()) {
      this.ground = other.ground;
    }
    if (other.isSetType()) {
      this.type = other.type;
    }
    if (other.isSetNearMatches()) {
      List<THistoryMatch> __this__nearMatches = new ArrayList<THistoryMatch>(other.nearMatches.size());
      for (THistoryMatch other_element : other.nearMatches) {
        __this__nearMatches.add(new THistoryMatch(other_element));
      }
      this.nearMatches = __this__nearMatches;
    }
    if (other.isSetAfterMatches()) {
      List<THistoryMatch> __this__afterMatches = new ArrayList<THistoryMatch>(other.afterMatches.size());
      for (THistoryMatch other_element : other.afterMatches) {
        __this__afterMatches.add(new THistoryMatch(other_element));
      }
      this.afterMatches = __this__afterMatches;
    }
    if (other.isSetStats()) {
      Map<String,String> __this__stats = new HashMap<String,String>(other.stats);
      this.stats = __this__stats;
    }
  }

  public TMatchInfo deepCopy() {
    return new TMatchInfo(this);
  }

  @Override
  public void clear() {
    setCompetitorIdIsSet(false);
    this.competitorId = 0;
    this.ground = null;
    this.type = null;
    this.nearMatches = null;
    this.afterMatches = null;
    this.stats = null;
  }

  public long getCompetitorId() {
    return this.competitorId;
  }

  public void setCompetitorId(long competitorId) {
    this.competitorId = competitorId;
    setCompetitorIdIsSet(true);
  }

  public void unsetCompetitorId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __COMPETITORID_ISSET_ID);
  }

  /** Returns true if field competitorId is set (has been assigned a value) and false otherwise */
  public boolean isSetCompetitorId() {
    return EncodingUtils.testBit(__isset_bitfield, __COMPETITORID_ISSET_ID);
  }

  public void setCompetitorIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __COMPETITORID_ISSET_ID, value);
  }

  /**
   * 
   * @see com.lesports.qmt.sbd.api.common.GroundType
   */
  public com.lesports.qmt.sbd.api.common.GroundType getGround() {
    return this.ground;
  }

  /**
   * 
   * @see com.lesports.qmt.sbd.api.common.GroundType
   */
  public void setGround(com.lesports.qmt.sbd.api.common.GroundType ground) {
    this.ground = ground;
  }

  public void unsetGround() {
    this.ground = null;
  }

  /** Returns true if field ground is set (has been assigned a value) and false otherwise */
  public boolean isSetGround() {
    return this.ground != null;
  }

  public void setGroundIsSet(boolean value) {
    if (!value) {
      this.ground = null;
    }
  }

  /**
   * 
   * @see com.lesports.qmt.sbd.api.common.CompetitorType
   */
  public com.lesports.qmt.sbd.api.common.CompetitorType getType() {
    return this.type;
  }

  /**
   * 
   * @see com.lesports.qmt.sbd.api.common.CompetitorType
   */
  public void setType(com.lesports.qmt.sbd.api.common.CompetitorType type) {
    this.type = type;
  }

  public void unsetType() {
    this.type = null;
  }

  /** Returns true if field type is set (has been assigned a value) and false otherwise */
  public boolean isSetType() {
    return this.type != null;
  }

  public void setTypeIsSet(boolean value) {
    if (!value) {
      this.type = null;
    }
  }

  public int getNearMatchesSize() {
    return (this.nearMatches == null) ? 0 : this.nearMatches.size();
  }

  public java.util.Iterator<THistoryMatch> getNearMatchesIterator() {
    return (this.nearMatches == null) ? null : this.nearMatches.iterator();
  }

  public void addToNearMatches(THistoryMatch elem) {
    if (this.nearMatches == null) {
      this.nearMatches = new ArrayList<THistoryMatch>();
    }
    this.nearMatches.add(elem);
  }

  public List<THistoryMatch> getNearMatches() {
    return this.nearMatches;
  }

  public void setNearMatches(List<THistoryMatch> nearMatches) {
    this.nearMatches = nearMatches;
  }

  public void unsetNearMatches() {
    this.nearMatches = null;
  }

  /** Returns true if field nearMatches is set (has been assigned a value) and false otherwise */
  public boolean isSetNearMatches() {
    return this.nearMatches != null;
  }

  public void setNearMatchesIsSet(boolean value) {
    if (!value) {
      this.nearMatches = null;
    }
  }

  public int getAfterMatchesSize() {
    return (this.afterMatches == null) ? 0 : this.afterMatches.size();
  }

  public java.util.Iterator<THistoryMatch> getAfterMatchesIterator() {
    return (this.afterMatches == null) ? null : this.afterMatches.iterator();
  }

  public void addToAfterMatches(THistoryMatch elem) {
    if (this.afterMatches == null) {
      this.afterMatches = new ArrayList<THistoryMatch>();
    }
    this.afterMatches.add(elem);
  }

  public List<THistoryMatch> getAfterMatches() {
    return this.afterMatches;
  }

  public void setAfterMatches(List<THistoryMatch> afterMatches) {
    this.afterMatches = afterMatches;
  }

  public void unsetAfterMatches() {
    this.afterMatches = null;
  }

  /** Returns true if field afterMatches is set (has been assigned a value) and false otherwise */
  public boolean isSetAfterMatches() {
    return this.afterMatches != null;
  }

  public void setAfterMatchesIsSet(boolean value) {
    if (!value) {
      this.afterMatches = null;
    }
  }

  public int getStatsSize() {
    return (this.stats == null) ? 0 : this.stats.size();
  }

  public void putToStats(String key, String val) {
    if (this.stats == null) {
      this.stats = new HashMap<String,String>();
    }
    this.stats.put(key, val);
  }

  public Map<String,String> getStats() {
    return this.stats;
  }

  public void setStats(Map<String,String> stats) {
    this.stats = stats;
  }

  public void unsetStats() {
    this.stats = null;
  }

  /** Returns true if field stats is set (has been assigned a value) and false otherwise */
  public boolean isSetStats() {
    return this.stats != null;
  }

  public void setStatsIsSet(boolean value) {
    if (!value) {
      this.stats = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case COMPETITOR_ID:
      if (value == null) {
        unsetCompetitorId();
      } else {
        setCompetitorId((Long)value);
      }
      break;

    case GROUND:
      if (value == null) {
        unsetGround();
      } else {
        setGround((com.lesports.qmt.sbd.api.common.GroundType)value);
      }
      break;

    case TYPE:
      if (value == null) {
        unsetType();
      } else {
        setType((com.lesports.qmt.sbd.api.common.CompetitorType)value);
      }
      break;

    case NEAR_MATCHES:
      if (value == null) {
        unsetNearMatches();
      } else {
        setNearMatches((List<THistoryMatch>)value);
      }
      break;

    case AFTER_MATCHES:
      if (value == null) {
        unsetAfterMatches();
      } else {
        setAfterMatches((List<THistoryMatch>)value);
      }
      break;

    case STATS:
      if (value == null) {
        unsetStats();
      } else {
        setStats((Map<String,String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case COMPETITOR_ID:
      return Long.valueOf(getCompetitorId());

    case GROUND:
      return getGround();

    case TYPE:
      return getType();

    case NEAR_MATCHES:
      return getNearMatches();

    case AFTER_MATCHES:
      return getAfterMatches();

    case STATS:
      return getStats();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case COMPETITOR_ID:
      return isSetCompetitorId();
    case GROUND:
      return isSetGround();
    case TYPE:
      return isSetType();
    case NEAR_MATCHES:
      return isSetNearMatches();
    case AFTER_MATCHES:
      return isSetAfterMatches();
    case STATS:
      return isSetStats();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TMatchInfo)
      return this.equals((TMatchInfo)that);
    return false;
  }

  public boolean equals(TMatchInfo that) {
    if (that == null)
      return false;

    boolean this_present_competitorId = true;
    boolean that_present_competitorId = true;
    if (this_present_competitorId || that_present_competitorId) {
      if (!(this_present_competitorId && that_present_competitorId))
        return false;
      if (this.competitorId != that.competitorId)
        return false;
    }

    boolean this_present_ground = true && this.isSetGround();
    boolean that_present_ground = true && that.isSetGround();
    if (this_present_ground || that_present_ground) {
      if (!(this_present_ground && that_present_ground))
        return false;
      if (!this.ground.equals(that.ground))
        return false;
    }

    boolean this_present_type = true && this.isSetType();
    boolean that_present_type = true && that.isSetType();
    if (this_present_type || that_present_type) {
      if (!(this_present_type && that_present_type))
        return false;
      if (!this.type.equals(that.type))
        return false;
    }

    boolean this_present_nearMatches = true && this.isSetNearMatches();
    boolean that_present_nearMatches = true && that.isSetNearMatches();
    if (this_present_nearMatches || that_present_nearMatches) {
      if (!(this_present_nearMatches && that_present_nearMatches))
        return false;
      if (!this.nearMatches.equals(that.nearMatches))
        return false;
    }

    boolean this_present_afterMatches = true && this.isSetAfterMatches();
    boolean that_present_afterMatches = true && that.isSetAfterMatches();
    if (this_present_afterMatches || that_present_afterMatches) {
      if (!(this_present_afterMatches && that_present_afterMatches))
        return false;
      if (!this.afterMatches.equals(that.afterMatches))
        return false;
    }

    boolean this_present_stats = true && this.isSetStats();
    boolean that_present_stats = true && that.isSetStats();
    if (this_present_stats || that_present_stats) {
      if (!(this_present_stats && that_present_stats))
        return false;
      if (!this.stats.equals(that.stats))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_competitorId = true;
    list.add(present_competitorId);
    if (present_competitorId)
      list.add(competitorId);

    boolean present_ground = true && (isSetGround());
    list.add(present_ground);
    if (present_ground)
      list.add(ground.getValue());

    boolean present_type = true && (isSetType());
    list.add(present_type);
    if (present_type)
      list.add(type.getValue());

    boolean present_nearMatches = true && (isSetNearMatches());
    list.add(present_nearMatches);
    if (present_nearMatches)
      list.add(nearMatches);

    boolean present_afterMatches = true && (isSetAfterMatches());
    list.add(present_afterMatches);
    if (present_afterMatches)
      list.add(afterMatches);

    boolean present_stats = true && (isSetStats());
    list.add(present_stats);
    if (present_stats)
      list.add(stats);

    return list.hashCode();
  }

  @Override
  public int compareTo(TMatchInfo other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetCompetitorId()).compareTo(other.isSetCompetitorId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompetitorId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.competitorId, other.competitorId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetGround()).compareTo(other.isSetGround());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGround()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ground, other.ground);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetType()).compareTo(other.isSetType());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetType()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, other.type);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetNearMatches()).compareTo(other.isSetNearMatches());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetNearMatches()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.nearMatches, other.nearMatches);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAfterMatches()).compareTo(other.isSetAfterMatches());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAfterMatches()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.afterMatches, other.afterMatches);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStats()).compareTo(other.isSetStats());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStats()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.stats, other.stats);
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
    StringBuilder sb = new StringBuilder("TMatchInfo(");
    boolean first = true;

    sb.append("competitorId:");
    sb.append(this.competitorId);
    first = false;
    if (isSetGround()) {
      if (!first) sb.append(", ");
      sb.append("ground:");
      if (this.ground == null) {
        sb.append("null");
      } else {
        sb.append(this.ground);
      }
      first = false;
    }
    if (isSetType()) {
      if (!first) sb.append(", ");
      sb.append("type:");
      if (this.type == null) {
        sb.append("null");
      } else {
        sb.append(this.type);
      }
      first = false;
    }
    if (isSetNearMatches()) {
      if (!first) sb.append(", ");
      sb.append("nearMatches:");
      if (this.nearMatches == null) {
        sb.append("null");
      } else {
        sb.append(this.nearMatches);
      }
      first = false;
    }
    if (isSetAfterMatches()) {
      if (!first) sb.append(", ");
      sb.append("afterMatches:");
      if (this.afterMatches == null) {
        sb.append("null");
      } else {
        sb.append(this.afterMatches);
      }
      first = false;
    }
    if (isSetStats()) {
      if (!first) sb.append(", ");
      sb.append("stats:");
      if (this.stats == null) {
        sb.append("null");
      } else {
        sb.append(this.stats);
      }
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

  private static class TMatchInfoStandardSchemeFactory implements SchemeFactory {
    public TMatchInfoStandardScheme getScheme() {
      return new TMatchInfoStandardScheme();
    }
  }

  private static class TMatchInfoStandardScheme extends StandardScheme<TMatchInfo> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TMatchInfo struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // COMPETITOR_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.competitorId = iprot.readI64();
              struct.setCompetitorIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // GROUND
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.ground = com.lesports.qmt.sbd.api.common.GroundType.findByValue(iprot.readI32());
              struct.setGroundIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TYPE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.type = com.lesports.qmt.sbd.api.common.CompetitorType.findByValue(iprot.readI32());
              struct.setTypeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // NEAR_MATCHES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list40 = iprot.readListBegin();
                struct.nearMatches = new ArrayList<THistoryMatch>(_list40.size);
                THistoryMatch _elem41;
                for (int _i42 = 0; _i42 < _list40.size; ++_i42)
                {
                  _elem41 = new THistoryMatch();
                  _elem41.read(iprot);
                  struct.nearMatches.add(_elem41);
                }
                iprot.readListEnd();
              }
              struct.setNearMatchesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // AFTER_MATCHES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list43 = iprot.readListBegin();
                struct.afterMatches = new ArrayList<THistoryMatch>(_list43.size);
                THistoryMatch _elem44;
                for (int _i45 = 0; _i45 < _list43.size; ++_i45)
                {
                  _elem44 = new THistoryMatch();
                  _elem44.read(iprot);
                  struct.afterMatches.add(_elem44);
                }
                iprot.readListEnd();
              }
              struct.setAfterMatchesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // STATS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map46 = iprot.readMapBegin();
                struct.stats = new HashMap<String,String>(2*_map46.size);
                String _key47;
                String _val48;
                for (int _i49 = 0; _i49 < _map46.size; ++_i49)
                {
                  _key47 = iprot.readString();
                  _val48 = iprot.readString();
                  struct.stats.put(_key47, _val48);
                }
                iprot.readMapEnd();
              }
              struct.setStatsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TMatchInfo struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(COMPETITOR_ID_FIELD_DESC);
      oprot.writeI64(struct.competitorId);
      oprot.writeFieldEnd();
      if (struct.ground != null) {
        if (struct.isSetGround()) {
          oprot.writeFieldBegin(GROUND_FIELD_DESC);
          oprot.writeI32(struct.ground.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.type != null) {
        if (struct.isSetType()) {
          oprot.writeFieldBegin(TYPE_FIELD_DESC);
          oprot.writeI32(struct.type.getValue());
          oprot.writeFieldEnd();
        }
      }
      if (struct.nearMatches != null) {
        if (struct.isSetNearMatches()) {
          oprot.writeFieldBegin(NEAR_MATCHES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.nearMatches.size()));
            for (THistoryMatch _iter50 : struct.nearMatches)
            {
              _iter50.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.afterMatches != null) {
        if (struct.isSetAfterMatches()) {
          oprot.writeFieldBegin(AFTER_MATCHES_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.afterMatches.size()));
            for (THistoryMatch _iter51 : struct.afterMatches)
            {
              _iter51.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.stats != null) {
        if (struct.isSetStats()) {
          oprot.writeFieldBegin(STATS_FIELD_DESC);
          {
            oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, struct.stats.size()));
            for (Map.Entry<String, String> _iter52 : struct.stats.entrySet())
            {
              oprot.writeString(_iter52.getKey());
              oprot.writeString(_iter52.getValue());
            }
            oprot.writeMapEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TMatchInfoTupleSchemeFactory implements SchemeFactory {
    public TMatchInfoTupleScheme getScheme() {
      return new TMatchInfoTupleScheme();
    }
  }

  private static class TMatchInfoTupleScheme extends TupleScheme<TMatchInfo> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TMatchInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetCompetitorId()) {
        optionals.set(0);
      }
      if (struct.isSetGround()) {
        optionals.set(1);
      }
      if (struct.isSetType()) {
        optionals.set(2);
      }
      if (struct.isSetNearMatches()) {
        optionals.set(3);
      }
      if (struct.isSetAfterMatches()) {
        optionals.set(4);
      }
      if (struct.isSetStats()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetCompetitorId()) {
        oprot.writeI64(struct.competitorId);
      }
      if (struct.isSetGround()) {
        oprot.writeI32(struct.ground.getValue());
      }
      if (struct.isSetType()) {
        oprot.writeI32(struct.type.getValue());
      }
      if (struct.isSetNearMatches()) {
        {
          oprot.writeI32(struct.nearMatches.size());
          for (THistoryMatch _iter53 : struct.nearMatches)
          {
            _iter53.write(oprot);
          }
        }
      }
      if (struct.isSetAfterMatches()) {
        {
          oprot.writeI32(struct.afterMatches.size());
          for (THistoryMatch _iter54 : struct.afterMatches)
          {
            _iter54.write(oprot);
          }
        }
      }
      if (struct.isSetStats()) {
        {
          oprot.writeI32(struct.stats.size());
          for (Map.Entry<String, String> _iter55 : struct.stats.entrySet())
          {
            oprot.writeString(_iter55.getKey());
            oprot.writeString(_iter55.getValue());
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TMatchInfo struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.competitorId = iprot.readI64();
        struct.setCompetitorIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.ground = com.lesports.qmt.sbd.api.common.GroundType.findByValue(iprot.readI32());
        struct.setGroundIsSet(true);
      }
      if (incoming.get(2)) {
        struct.type = com.lesports.qmt.sbd.api.common.CompetitorType.findByValue(iprot.readI32());
        struct.setTypeIsSet(true);
      }
      if (incoming.get(3)) {
        {
          org.apache.thrift.protocol.TList _list56 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.nearMatches = new ArrayList<THistoryMatch>(_list56.size);
          THistoryMatch _elem57;
          for (int _i58 = 0; _i58 < _list56.size; ++_i58)
          {
            _elem57 = new THistoryMatch();
            _elem57.read(iprot);
            struct.nearMatches.add(_elem57);
          }
        }
        struct.setNearMatchesIsSet(true);
      }
      if (incoming.get(4)) {
        {
          org.apache.thrift.protocol.TList _list59 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.afterMatches = new ArrayList<THistoryMatch>(_list59.size);
          THistoryMatch _elem60;
          for (int _i61 = 0; _i61 < _list59.size; ++_i61)
          {
            _elem60 = new THistoryMatch();
            _elem60.read(iprot);
            struct.afterMatches.add(_elem60);
          }
        }
        struct.setAfterMatchesIsSet(true);
      }
      if (incoming.get(5)) {
        {
          org.apache.thrift.protocol.TMap _map62 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.STRING, org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.stats = new HashMap<String,String>(2*_map62.size);
          String _key63;
          String _val64;
          for (int _i65 = 0; _i65 < _map62.size; ++_i65)
          {
            _key63 = iprot.readString();
            _val64 = iprot.readString();
            struct.stats.put(_key63, _val64);
          }
        }
        struct.setStatsIsSet(true);
      }
    }
  }

}
