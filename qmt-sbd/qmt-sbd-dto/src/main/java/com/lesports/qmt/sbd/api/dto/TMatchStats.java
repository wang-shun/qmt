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
public class TMatchStats implements org.apache.thrift.TBase<TMatchStats, TMatchStats._Fields>, java.io.Serializable, Cloneable, Comparable<TMatchStats> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TMatchStats");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField COMPETITOR_STATS_FIELD_DESC = new org.apache.thrift.protocol.TField("competitorStats", org.apache.thrift.protocol.TType.LIST, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TMatchStatsStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TMatchStatsTupleSchemeFactory());
  }

  private long id; // required
  private List<TCompetitorStat> competitorStats; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    COMPETITOR_STATS((short)2, "competitorStats");

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
        case 1: // ID
          return ID;
        case 2: // COMPETITOR_STATS
          return COMPETITOR_STATS;
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
  private static final int __ID_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.COMPETITOR_STATS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.COMPETITOR_STATS, new org.apache.thrift.meta_data.FieldMetaData("competitorStats", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TCompetitorStat.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TMatchStats.class, metaDataMap);
  }

  public TMatchStats() {
  }

  public TMatchStats(
    long id)
  {
    this();
    this.id = id;
    setIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TMatchStats(TMatchStats other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetCompetitorStats()) {
      List<TCompetitorStat> __this__competitorStats = new ArrayList<TCompetitorStat>(other.competitorStats.size());
      for (TCompetitorStat other_element : other.competitorStats) {
        __this__competitorStats.add(new TCompetitorStat(other_element));
      }
      this.competitorStats = __this__competitorStats;
    }
  }

  public TMatchStats deepCopy() {
    return new TMatchStats(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.competitorStats = null;
  }

  public long getId() {
    return this.id;
  }

  public void setId(long id) {
    this.id = id;
    setIdIsSet(true);
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public int getCompetitorStatsSize() {
    return (this.competitorStats == null) ? 0 : this.competitorStats.size();
  }

  public java.util.Iterator<TCompetitorStat> getCompetitorStatsIterator() {
    return (this.competitorStats == null) ? null : this.competitorStats.iterator();
  }

  public void addToCompetitorStats(TCompetitorStat elem) {
    if (this.competitorStats == null) {
      this.competitorStats = new ArrayList<TCompetitorStat>();
    }
    this.competitorStats.add(elem);
  }

  public List<TCompetitorStat> getCompetitorStats() {
    return this.competitorStats;
  }

  public void setCompetitorStats(List<TCompetitorStat> competitorStats) {
    this.competitorStats = competitorStats;
  }

  public void unsetCompetitorStats() {
    this.competitorStats = null;
  }

  /** Returns true if field competitorStats is set (has been assigned a value) and false otherwise */
  public boolean isSetCompetitorStats() {
    return this.competitorStats != null;
  }

  public void setCompetitorStatsIsSet(boolean value) {
    if (!value) {
      this.competitorStats = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Long)value);
      }
      break;

    case COMPETITOR_STATS:
      if (value == null) {
        unsetCompetitorStats();
      } else {
        setCompetitorStats((List<TCompetitorStat>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Long.valueOf(getId());

    case COMPETITOR_STATS:
      return getCompetitorStats();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case COMPETITOR_STATS:
      return isSetCompetitorStats();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TMatchStats)
      return this.equals((TMatchStats)that);
    return false;
  }

  public boolean equals(TMatchStats that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_competitorStats = true && this.isSetCompetitorStats();
    boolean that_present_competitorStats = true && that.isSetCompetitorStats();
    if (this_present_competitorStats || that_present_competitorStats) {
      if (!(this_present_competitorStats && that_present_competitorStats))
        return false;
      if (!this.competitorStats.equals(that.competitorStats))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true;
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_competitorStats = true && (isSetCompetitorStats());
    list.add(present_competitorStats);
    if (present_competitorStats)
      list.add(competitorStats);

    return list.hashCode();
  }

  @Override
  public int compareTo(TMatchStats other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCompetitorStats()).compareTo(other.isSetCompetitorStats());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCompetitorStats()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.competitorStats, other.competitorStats);
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
    StringBuilder sb = new StringBuilder("TMatchStats(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (isSetCompetitorStats()) {
      if (!first) sb.append(", ");
      sb.append("competitorStats:");
      if (this.competitorStats == null) {
        sb.append("null");
      } else {
        sb.append(this.competitorStats);
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

  private static class TMatchStatsStandardSchemeFactory implements SchemeFactory {
    public TMatchStatsStandardScheme getScheme() {
      return new TMatchStatsStandardScheme();
    }
  }

  private static class TMatchStatsStandardScheme extends StandardScheme<TMatchStats> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TMatchStats struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.id = iprot.readI64();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // COMPETITOR_STATS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list346 = iprot.readListBegin();
                struct.competitorStats = new ArrayList<TCompetitorStat>(_list346.size);
                TCompetitorStat _elem347;
                for (int _i348 = 0; _i348 < _list346.size; ++_i348)
                {
                  _elem347 = new TCompetitorStat();
                  _elem347.read(iprot);
                  struct.competitorStats.add(_elem347);
                }
                iprot.readListEnd();
              }
              struct.setCompetitorStatsIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TMatchStats struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI64(struct.id);
      oprot.writeFieldEnd();
      if (struct.competitorStats != null) {
        if (struct.isSetCompetitorStats()) {
          oprot.writeFieldBegin(COMPETITOR_STATS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.competitorStats.size()));
            for (TCompetitorStat _iter349 : struct.competitorStats)
            {
              _iter349.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TMatchStatsTupleSchemeFactory implements SchemeFactory {
    public TMatchStatsTupleScheme getScheme() {
      return new TMatchStatsTupleScheme();
    }
  }

  private static class TMatchStatsTupleScheme extends TupleScheme<TMatchStats> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TMatchStats struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetId()) {
        optionals.set(0);
      }
      if (struct.isSetCompetitorStats()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetId()) {
        oprot.writeI64(struct.id);
      }
      if (struct.isSetCompetitorStats()) {
        {
          oprot.writeI32(struct.competitorStats.size());
          for (TCompetitorStat _iter350 : struct.competitorStats)
          {
            _iter350.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TMatchStats struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.id = iprot.readI64();
        struct.setIdIsSet(true);
      }
      if (incoming.get(1)) {
        {
          org.apache.thrift.protocol.TList _list351 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.competitorStats = new ArrayList<TCompetitorStat>(_list351.size);
          TCompetitorStat _elem352;
          for (int _i353 = 0; _i353 < _list351.size; ++_i353)
          {
            _elem352 = new TCompetitorStat();
            _elem352.read(iprot);
            struct.competitorStats.add(_elem352);
          }
        }
        struct.setCompetitorStatsIsSet(true);
      }
    }
  }

}

